package ast;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class IWhile extends I {
    private E cond; 
    private List<I> cuerpoWhile;

    public IWhile(E cond, List<I> cuerpoWhile) {
        this.cond = cond;
        this.cuerpoWhile = cuerpoWhile;
    }

	public KindI kind() {
		return KindI.WHILE;
	}

   public String toString () {
        return getJSON().toJSONString();
   }

	@Override
	public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION WHILE");
        obj.put("cond", cond.getJSON());
        if (!cuerpoWhile.isEmpty()) {
            JSONArray cuerpojson = new JSONArray();
            for (I i : cuerpoWhile) {
                cuerpojson.add(i.getJSON());
            }
            obj.put("cuerpo", cuerpojson);
        }
		return obj;
	}

    
}
