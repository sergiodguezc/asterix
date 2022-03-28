package ast;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class IIf extends I {
    private E cond; 
    private List<I> cuerpoIf;
    private List<I> cuerpoElse;
    private boolean ifelse;

    public IIf(E cond, List<I> cuerpoIf) {
        this.cond = cond;
        this.cuerpoIf = cuerpoIf;
        ifelse = false;
    }

    public IIf(E cond, List<I> cuerpoIf, List<I> cuerpoElse) {
        this.cond = cond;
        this.cuerpoIf = cuerpoIf;
        this.cuerpoElse = cuerpoElse;
        ifelse = true;
    }

	public KindI kind() {
		return KindI.IF;
	}

	@Override
	public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION IF");
        obj.put("cond", cond.getJSON());
        if (!cuerpoIf.isEmpty()) {
            JSONArray cuerpojson = new JSONArray();
            for (I i : cuerpoIf) {
                cuerpojson.add(i.getJSON());
            }
            obj.put("cuerpoIf", cuerpojson);
        }
        if (ifelse) {
            if (!cuerpoElse.isEmpty()) {
                JSONArray cuerpojson = new JSONArray();
                for (I i : cuerpoIf) {
                    cuerpojson.add(i.getJSON());
                }
                obj.put("cuerpoElse", cuerpojson);
            }
        }
		return obj;
	}
    
   public String toString () {
        return getJSON().toJSONString();
   }
}
