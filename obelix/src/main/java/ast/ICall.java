package ast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;
import utils.CodeUtils;

import java.io.PrintWriter;
import java.util.List;

public class ICall extends I {
    private List<E> params; // lista de parámtros de entrada
    private String id; // identificador de la función
    private Boolean noParams; // booleano que indica si no tiene parámetros
    private S potion; // función a la que hace referencia
    private CodeUtils utils;

    // CONSTRUCTOR
    public ICall(List<E> params, String id, CodeUtils utils) {
        this.params = params;
        this.id = id;
        noParams = false;
        this.utils = utils;
    }

    // CONSTRUCTOR
    public ICall(String id, CodeUtils utils) {
        this.id = id;
        noParams = true;
        this.utils = utils;
    }

    // AST
    public KindI kind() {
        return KindI.CALL;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION LLAMADA");
        obj.put("funcion", id);
        if (noParams)
            return obj;
        JSONArray arr = new JSONArray();
        for (E arg : params)
            arr.add(arg.getJSON());
        obj.put("args", arr);
        return obj;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    // VINCULACION
    public void bind(SymbolMap ts) {
        for (E e : params)
            e.bind(ts);
        ASTNode potion = ts.searchId(id);
        if (potion == null || potion.nodeKind() != NodeKind.SUBPROGRAMA) {
            GestionErroresAsterix.errorSemantico("ERROR: Llamada a procedimiento no declarado.");
        } else {
            this.potion = (S) potion;
        }
    }

    // TIPADO
    public T type() {
        if (potion == null) {
            return new T(KindT.ERROR);
        }

        // Obtenemos la lista de argumentos del subprograma al que se hace referencia.
        List<Arg> argsS = potion.getArguments();
        if (argsS.size() != params.size()) {
            GestionErroresAsterix.errorSemantico("ERROR: Llamada a subprograma. Numero de parametros incorrecto.");
            return new T(KindT.ERROR);
        }

        boolean error = false;
        for (int i = 0; i < params.size(); i++) {
            if (!ASemUtils.checkEqualTypes(params.get(i).type(), argsS.get(i).type())) {
                GestionErroresAsterix.errorSemantico("ERROR: Tipos de los argumentos incorrectos.");
                error = true;
            }
        }
        if (error)
            return new T(KindT.ERROR);
        return new T(KindT.INS);
    }


    // GENERACIÓN DE CÓDIGO
    public void generateCodeI(PrintWriter pw) {
        List<Arg> argumentos = potion.getArguments();
        int offset = 8;

        for (int i = 0; i < argumentos.size(); i++) {
            if(argumentos.get(i).isRef()) {
                // Copiamos la direccion de los parametros por referencia en el lugar que le corresponde
                pw.println("get_global $SP");
                params.get(i).generateCodeD(pw);
                pw.println("i32.store offset=" + offset);
                offset += 4;
            }
            else {
                // Evaluamos las expresiones de los parametros por valor y copiamos el resultado en el lugar que le corresponde
                generateCodeRecursively(params.get(i), offset, pw);
                params.get(i).getType().setSizeT();
                offset += params.get(i).getType().getSizeT();
            }
        }

        pw.println("call $" + id);
        if(potion.isFunction()) {
            pw.println("drop");
        }
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
            utils.showCopyn();
            pw.println("call $copyn");
        }
    }
}
