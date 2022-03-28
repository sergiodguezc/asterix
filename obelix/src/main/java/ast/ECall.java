package ast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class ECall extends E{
    private List<E> args;
    private String id;
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

    public KindE kind() {return KindE.CALL;}

    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "EXPRESION LLAMADA");
        obj.put("id", id);
        if(noParams)
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
}
