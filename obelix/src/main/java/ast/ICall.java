package ast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class ICall extends I {
    private List<E> params;
    private String id;
    private Boolean noParams;

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

    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION LLAMADA");
        obj.put("funcion", id);
        if(noParams)
            return obj;
        JSONArray arr = new JSONArray();
        for(E arg: params)
            arr.add(arg.getJSON());
        obj.put("args", arr);
        return obj;
    }

    public String toString() {
        if (noParams)
            return "call("+id +")";
        return "call("+id+","+params.toString()+")";
    }

}
