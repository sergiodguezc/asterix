package ast;

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
        return (tipoECall = tRet);
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
        generateCodeD(pw);
        pw.println("i32.load");
    }

    public void generateCodeD(PrintWriter pw){
        List<Arg> argumentos = potion.getArguments();
        int offset = 8;

        for (int i = 0; i < argumentos.size(); i++) {
            if(argumentos.get(i).isRef()) {
                // Copiamos la direccion de los parametros por referencia en el lugar que le corresponde
                pw.println("get_global $SP");
                args.get(i).generateCodeD(pw);
                pw.println("i32.store offset=" + offset);
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
    }

    private void generateCodeRecursively(E valor, int offset, PrintWriter pw) {
        T tipo = valor.getType();

        // Caso Base: Es un tipo basico
        if (tipo.getKindT() != KindT.VECTIX) {
            pw.println("get_global $SP");
            valor.generateCodeE(pw);
            tipo.generateCode(pw); pw.println(".store offset=" + offset);
        }

        // Caso Recursivo : Es un vector y valor es una ELista
        else if (valor.kind() == KindE.LISTA) {
            List<E> lista = ((ELista) valor).getLista();

            for (E elem : lista) {
                elem.getType().setSizeT();
                generateCodeRecursively(elem, offset + elem.getType().getSizeT(), pw);
            }
        }

        // Caso Base : Caso de que sea una llamada a una funcion o un vector ya definido
        else {
            // Obtenemos la direccion de memoria del vector ya definido
            valor.generateCodeD(pw);

            // Ponemos la direccion de memoria donde queremos copiarlo
            pw.println("get_global $SP");
            pw.println("i32.const " + offset);
            pw.println("i32.add");

            // Obtenemos el numero de bytes a copiar
            tipo.setSizeT();
            pw.println("i32.const " + tipo.getSizeT());

            // Llamamos a la funcion copyn
            P.copyn = true;
            pw.println("call $copyn");
        }
    }



}
