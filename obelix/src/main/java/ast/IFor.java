package ast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class IFor extends I {
    private T tipo; 
    private String id;
    private List<I> cuerpoFor;

    public IFor(T tipo, String id, List<I> cuerpoFor) {
        this.tipo = tipo;
        this.id = id;
        this.cuerpoFor = cuerpoFor;
    }

	public KindI kind() {
		return KindI.FOR;
	}

    @Override
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION FOR");
        obj.put("tipo", tipo.getJSON());
        obj.put("id", id);
        if(cuerpoFor.isEmpty())
            return obj;
        JSONArray arr = new JSONArray();
        for(I i: cuerpoFor)
            arr.add(i.getJSON());
        obj.put("cuerpo", arr);
        return obj;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    
}
