package ast;

import asem.ASemUtils;
import asem.SymbolMap;
import com.rits.cloning.Cloner;
import errors.GestionErroresAsterix;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONObject;

public class IDec extends I implements DefSub {
    private String id; // Identificador de la variable
    private int delta; // Posición relativa de la variable dentro del bloque
    private Boolean ini;
    private T type;

    private E valor;
    private List<IDec> declarations; // En caso de que sea un struct

    // Variable no inicializada
    public IDec(T type, String id) {
        Cloner c = new Cloner();
        this.type = c.deepClone(type);
        this.id = id;
        ini = false;
    }

    // Variable inicializada
    public IDec(T type, String id, E valor) {
        Cloner c = new Cloner();
        this.type = c.deepClone(type);
        this.id = id;
        this.valor = valor;
        ini = true;
    }

    public KindI kind() {
        return KindI.DEC;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION DECLARACION");
        obj.put("id", getId());
        obj.put("tipo", type.getJSON());
        if (!isIni())
            return obj;
        obj.put("valor", valor.getJSON());
        return obj;
    }

    public void bind(SymbolMap ts) {
        type.bind(ts);
        if (isIni())
            valor.bind(ts);
        ts.insertId(id, this);
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    public T type() {
        type.type();
        if (isIni()) {
            T tipoValor = valor.type();
            if (!ASemUtils.checkEqualTypes(type, tipoValor)) {
                GestionErroresAsterix.errorSemantico("Error de tipado en la declaracion.");
                return new T(KindT.ERROR);
            }
        }

        return type;
    }

    public T getType() {
        return type;
    }

    // Generamos el código para guardar el valor de las variables inicializadas
    // en memoria
    public void generateCodeI(PrintWriter pw) {
        if (isIni()) {
            pw.println(";; inicialización de variable " + id);
            if (type.getKindT() != KindT.POT)
                generateCodeRecursively(pw, delta, valor);
            else {
                // Colocamos el valor de localStart en la pila para recuperarlo despues
                pw.println("get_local $localStart ;; save localStart, at the end we revert this change");

                // Cambiamos el localStart a localStart + delta
                pw.println("get_local $localStart");
                pw.println("i32.const " + delta + " ;; delta variable " + id);
                pw.println("i32.add");
                pw.println("set_local $localStart");

                // Generamos codigo para las declaraciones internas del struct si estan
                // inicializadas
                for (IDec iDec : declarations) {
                    iDec.generateCodeI(pw);
                }

                // Devolvemos el localStart al inicial. Este valor se encontraba ya en la pila.
                pw.println("set_local $localStart ;; devolvemos el localStart al valor original");
            // Caso en que es un vector de structs, recorremos el vect
            }
        }
    }

    public void setDelta(AtomicInteger size, AtomicInteger localSize) {
        delta = localSize.intValue();

        type.setSizeT();
        localSize.set(localSize.intValue() + type.getSizeT());
        size.set(size.intValue() + type.getSizeT());

        if (type.getKindTBasico() == KindT.POT) {
            // Caso struct simple
            if (type.getKindT() == KindT.POT) {
                declarations = type.getDec();
                for (IDec iDec : declarations)
                    if (iDec.isIni()) ini = true;
            }
            // Caso vector de structs
            if (type.getKindT() == KindT.VECTIX) {
                Cloner c = new Cloner();
                T tBasic = c.deepClone(type);
                while (tBasic.getKindT() != KindT.POT) {
                    tBasic = tBasic.getTInterno();
                }
                declarations = tBasic.getDec();
                for (IDec iDec : declarations)
                    if (iDec.isIni()) ini = true;
            }

        }
    }

    // Generar código para las variables globales
    public void generateCode(PrintWriter pw) {

    }

    private void generateCodeRecursively(PrintWriter pw, int offset, E valor) {
        // Caso Base: Es un tipo basico
        if (valor != null && valor.getType().getKindT() != KindT.VECTIX) {
            // Obtenemos la posicion donde almacenamos elem
            pw.println("get_local $localStart");
            pw.println("i32.const " + offset + " ;; offset");
            pw.println("i32.add");

            // Evaluamos elem
            pw.println(";; valor a guardar");
            valor.generateCodeE(pw);

            // Almacenamos elem en esa posicion
            valor.getType().generateCode(pw);
            pw.println(".store");
        }

        // Caso Recursivo : Es un vector y valor es una ELista
        else if (valor != null && valor.kind() == KindE.LISTA) {
            List<E> lista = ((ELista) valor).getLista();

            for (E elem : lista) {
                generateCodeRecursively(pw, offset, elem);
                elem.getType().setSizeT();
                offset += elem.getType().getSizeT();
            }
        // Caso en que el vector sea de structs
        } else if (valor == null && type.getKindT() == KindT.VECTIX && type.getKindTBasico() == KindT.POT) {
                Cloner c = new Cloner();
                T tBasic = c.deepClone(type);
                int basicElements = tBasic.getVSize();
                while (tBasic.getKindT() == KindT.VECTIX){
                    basicElements *= tBasic.getVSize();
                    tBasic = tBasic.getTInterno();
                }
                for (int i = 0; i < basicElements; i++) {
                    pw.println(";; struct " + i + " del vector " + id);
                    tBasic.setSizeT();
                    int size = tBasic.getSizeT();
                    pw.println("get_local $localStart ;; guardamos el valor para recuperarlo luego");
                    pw.println("get_local $localStart");
                    pw.println("i32.const " + size * i + " ;; size*i: " + size + "*" + i);
                    pw.println("i32.add");

                    pw.println("set_local $localStart");
                    for (IDec idec : declarations) {
                        // sumamos la posición inicial del vector
                        idec.generateCodeI(pw);
                    }
                    pw.println("set_local $localStart ;; recuperamos el valor original");
                }
        }

        // Caso Base : Caso de que sea una llamada a una funcion o un vector ya definido
        else {
            // Obtenemos la direccion de memoria del vector ya definido
            valor.generateCodeD(pw);

            // Obtenemos la direccion de memoria del nuevo vector
            pw.println("get_local $localStart");
            pw.println("i32.const " + delta + " ;; delta del vector");
            pw.println("i32.add");

            // Obtenemos el tamaño del vector
            type.setSizeT();
            pw.println("i32.const " + type.getSizeT() + " ;; tamaño del vector");

            // Llamamos a la funcion copyn
            P.copyni = type.getKindTBasico() == KindT.INTIX;
            P.copynf = type.getKindTBasico() == KindT.FLOATIX;
            pw.println((type.getKindTBasico() == KindT.INTIX) ? "call $copyni" : "call $copynf");
        }
    }

    // Getter de las declaraciones del struct, también nos sirve para diferenciar el
    // caso en que es uno y en los que es una variable normal.
    public List<IDec> getDeclarations() {
        return declarations;
    }

    // Getters y setters para comprobar si la variable está inicializada
    public boolean isIni() { return ini; } // getter ini
    public void setIni(boolean ini) { this.ini = ini; } // setter para el ini
                                                        
    // Getter para el id de la variable
    public String getId() {return id;}
    // Getter para el delta de la variable
    public int getDelta() {return delta;}
    public E getValor() {return valor;}
}
