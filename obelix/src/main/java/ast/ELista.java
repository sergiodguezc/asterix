package ast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class ELista extends E {
    private List<E> V;

    public ELista(List<E> V) {
        this.V = V;
    }

    public KindE kind() {
        return KindE.LISTA;
    }

    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "EXPRESION LISTA");
        JSONArray arr = new JSONArray();
        for(E v: V)
            arr.add(v.getJSON());
        obj.put("lista", arr);
        return obj;
    }

    public String toString() {
        return "vector("+V.toString() + ")";
    }

}
