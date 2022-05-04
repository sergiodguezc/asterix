package ast;

import com.rits.cloning.Cloner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;

import java.io.PrintWriter;
import java.util.List;

public class ECall extends E {
    private List<E> args;
    private String id;
    private S potion;
    boolean noParams;
    private T tipoECall;

    public ECall(List<E> args, String id) {
        this.args = args;
        this.id = id;
        noParams = false;
    }

    public ECall(String id) {
        this.id = id;
        noParams = true;
    }

    public KindE kind() {
        return KindE.CALL;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "EXPRESION LLAMADA");
        obj.put("id", id);
        if (noParams)
            return obj;
        JSONArray arr = new JSONArray();
        for (E arg : args)
            arr.add(arg.getJSON());
        obj.put("args", arr);
        return obj;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    public T type() {
        T tRet;
        // Comprobamos la existencia del subprograma.
        if (potion == null) {
            return (tipoECall = new T(KindT.ERROR));
            // Comprobamos que no sea un procedimiento.
        } else if ((tRet = potion.getType()) == null) {
            GestionErroresAsterix.errorSemantico("ERROR: Llamada a un procedimiento. No devuelve ningun valor.");
            return (tipoECall = new T(KindT.ERROR));
        }

        // Obtenemos la lista de argumentos del subprograma al que se hace referencia.
        List<Arg> argsS = potion.getArguments();
        if (argsS.size() != args.size()) {
            GestionErroresAsterix.errorSemantico("ERROR: Llamada a una funcion. Numero de parametros incorrecto.");
            return new T(KindT.ERROR);
        }

        boolean error = false;
        for (int i = 0; i < args.size(); i++) {
            if (!ASemUtils.checkEqualTypes(args.get(i).type(), argsS.get(i).type())) {
                GestionErroresAsterix.errorSemantico("ERROR: Tipos de los argumentos incorrectos.");
                error = true;
            }
        }
        if (error)
            return new T(KindT.ERROR);

        // Devolvemos el tipo del valor que devuelve la función.
        return tRet;
    }

    public void bind(SymbolMap ts) {
        for (E e : args)
            e.bind(ts);
        ASTNode potion = ts.searchId(id);
        if (potion == null || potion.nodeKind() != NodeKind.SUBPROGRAMA) {
            GestionErroresAsterix.errorSemantico("ERROR: Llamada a función no existente.");
        } else {
            this.potion = (S) potion;
        }
    }

    public T getType() {
        return tipoECall;
    }

    public void generateCodeE(PrintWriter pw){
        List<Arg> argumentos = potion.getArguments();
        int offset = 8;

        for (int i = 0; i < argumentos.size(); i++) {
            if(argumentos.get(i).isRef()) {
                // Copiamos la direccion de los parametros por referencia en el lugar que le corresponde
                pw.println("get_global $SP offset=" + offset);
                args.get(i).generateCodeD(pw);
                pw.println("i32.store");
                offset += 4;
            }
            else {
                // Evaluamos las expresiones de los parametros por valor y copiamos el resultado en el lugar que le corresponde
                generateCodeRecursively(args.get(i), offset, pw);
                args.get(i).getType().setSizeT();
                offset += args.get(i).getType().getSizeT();
            }
        }


        pw.println("call $" + id);
        // TODO : Gestionar la memoria del return
        pw.println("i32.load");
    }

    private void generateCodeRecursively(E valor, int offset, PrintWriter pw) {
        T tipo = valor.getType();

        // Caso Base: Es un tipo basico
        if (tipo.getKindT() != KindT.VECTIX) {
            pw.println("get_global $SP offset=" + offset);
            valor.generateCodeE(pw);
            tipo.generateCode(pw); pw.println(".store");
        }

        // Caso Recursivo : Es un vector y valor es una ELista
        else if (valor.kind() == KindE.LISTA) {
            List<E> lista = ((ELista) valor).getLista();

            for (E elem : lista) {
                elem.getType().setSizeT();
                generateCodeRecursively(elem, offset + elem.getType().getSizeT(), pw);
            }
        }

        // Caso Base : Es un struct o un vector de structs
        else if (tipo.getKindTBasico() == KindT.POT) {
            // Caso vector de structs
            if (tipo.getKindT() == KindT.VECTIX) {
                //Cloner c = new Cloner();
                //T tBasic = c.deepClone(tipo);
                T tBasic = tipo;
                // Calculamos cuántos elementos básicos tiene el vector
                int basicElements = tBasic.getVSize();
                while (tBasic.getKindT() == KindT.VECTIX){
                    basicElements *= tBasic.getVSize();
                    tBasic = tBasic.getTInterno();
                }
                // Por cada elemento básico, copiamos su valor a la pila de la función.
                for (int i = 0; i < basicElements; i++) {
                    pw.println(";; struct " + i + " del vector " + id);

                    tBasic.setSizeT();
                    // Calculamos el tamaño del struct básico
                    int size = tBasic.getSizeT();

                    // obtener el puntero donde comenzamos a copiar el struct
                    pw.println("get_local $localStart ;; guardamos el valor para recuperarlo luego");
                    pw.println("get_local $localStart");
                    pw.println("i32.const " + size * i + " ;; size*i: " + size + "*" + i);
                    pw.println("i32.add");

                    pw.println("set_local $localStart");

                    // Por cada declaración del struct, copiamos sus elementos a esa posición.
                    for (IDec idec : tBasic.getDec()) {
                        idec.generateCodeCall(pw, offset);
                        offset += tipo.getSizeT();
                    }
                    pw.println("set_local $localStart ;; recuperamos el valor original");
                }
            // Caso en que es un struct
            } else {
                // Colocamos el valor de localStart en la pila para recuperarlo despues
                pw.println("get_local $localStart ;; save localStart, at the end we revert this change");
                valor.generateCodeD(pw);
                pw.println("set_local $localStart");

                // Por cada declaración del struct, copiamos sus elementos a esa posición.
                tipo.setSizeT();
                for (IDec iDec : tipo.getDec()) {
                    iDec.generateCodeCall(pw, offset);
                    offset += tipo.getSizeT();
                }

                // Devolvemos el localStart al inicial. Este valor se encontraba ya en la pila.
                pw.println("set_local $localStart ;; devolvemos el localStart al valor original");
            }
        }

        // Caso Base : Caso de que sea una llamada a una funcion o un vector ya definido
        else {
            // Obtenemos la direccion de memoria del vector ya definido
            valor.generateCodeD(pw);

            // Ponemos la direccion de memoria donde queremos copiarlo
            pw.println("get_global $SP offset =" + offset);

            // Llamamos a la funcion copyn
            P.copyni = tipo.getKindTBasico() == KindT.INTIX || tipo.getKindTBasico() == KindT.BOOLIX;
            P.copynf = tipo.getKindTBasico() == KindT.FLOATIX;
            pw.println((tipo.getKindTBasico() == KindT.FLOATIX) ? "call $copynf" : "call $copyni");
        }
    }

    public void generateCodeD(PrintWriter pw){}

}
