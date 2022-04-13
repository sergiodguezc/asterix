package ast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

import java.util.List;

public class ECall extends E {
    private List<E> args;
    private String id;
    private S potion;
    boolean noParams;

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
        if (potion == null) {
            GestionErroresAsterix.errorSemantico("ERROR: Llamada a función no existente.");
            return new T(KindT.ERROR);
        } else
            return potion.type();
    }

    public void bind(SymbolMap ts) {
        for (E e : args)
            e.bind(ts);
        potion = (S) ts.searchId(id);
    }
}
