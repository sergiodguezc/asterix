package ast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ICall extends I {
    private List<E> params;
    private String id;
    private Boolean noParams;
    private S potion;

    public ICall(List<E> params, String id) {
        this.params = params;
        this.id = id;
        noParams = false;
    }

    public ICall(String id) {
        this.id = id;
        noParams = true;
    }

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

    @Override
    public void generateCodeI(PrintWriter pw) {
        // TODO Auto-generated method stub

    }

    public void setDelta(AtomicInteger size, AtomicInteger localSize) {
        // Esta instrucción no puede tener variables declaradas, dejamos este
    }

}
