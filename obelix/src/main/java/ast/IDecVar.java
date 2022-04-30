package ast;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONObject;

public class IDecVar extends IDec {
    private T type;
    private String id;
    private E valor;

    // Variable no inicializada
    public IDecVar(T type, String id) {
        super(id);
        this.type = type;
        setIni(false);
    }

    // Variable inicializada
    public IDecVar(T type, String id, E valor) {
        super(id);
        this.type = type;
        this.valor = valor;
        setIni(true);
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

    @Override
    public KindD kindD() {
        return KindD.VAR;
    }

    public void bind(SymbolMap ts) {
        type.bind(ts);
        if(isIni())
            valor.bind(ts);
        ts.insertId(getId(), this);
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    public T type() {
        type.type();
        if(isIni()) {
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
        if (isIni())
            generateCodeRecursively(pw, getDelta(), valor);
	}

	public void setDelta(AtomicInteger size, AtomicInteger localSize) {
        // Igualamos el delta a la posicion local sobre la que lo calculamos.
        setDelta(localSize.intValue());
        // Calculamos primero el tamaño del tipo y luego sumamos al size y localSize
        // el tamaño del tipo.
        type.setSizeT();
        localSize.set(localSize.intValue() + type.getSizeT());
        size.set(size.intValue() + type.getSizeT());

	}

    // Generar código para las variables globales
	public void generateCode(PrintWriter pw) {

	}

    private void generateCodeRecursively(PrintWriter pw, int offset, E valor) {
        // Caso Base: Es un tipo basico
        if(valor.getType().getKindT() != KindT.VECTIX) {
            // Obtenemos la posicion donde almacenamos elem
            pw.println("get_local $localStart");
            pw.println("i32.const " + offset);
            pw.println("i32.add");

            // Evaluamos elem
            valor.generateCodeE(pw);

            // Almacenamos elem en esa posicion
            type.generateCode(pw);
            pw.println(".store");
        }

        // Caso Recursivo : Es un vector y valor es una ELista
        else if (valor.kind() == KindE.LISTA) {
           List<E> lista = ((ELista) valor).getLista();

           for(E elem : lista) {
               generateCodeRecursively(pw, offset, elem);
               elem.getType().setSizeT();
               offset += elem.getType().getSizeT();
           }
        }

        // Caso Base : Caso de que sea una llamada a una funcion o un vector ya definido
        else {
            // Obtenemos la direccion de memoria del vector ya definido
            valor.generateCodeD(pw);

            // Obtenemos la direccion de memoria del nuevo vector
            pw.println("get_local $localStart");
            pw.println("i32.const " + getDelta() + " ;; delta del vector");
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
}