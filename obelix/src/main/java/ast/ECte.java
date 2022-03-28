package ast;

import org.json.simple.JSONObject;

public class ECte extends E {
    private String v;
    public ECte(String v) {
        this.v = v;
    }
    public String num() {return v;}
    public KindE kind() {return KindE.CTE;}

    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "EXPRESION CONSTANTE");
        obj.put("valor", v);
        return obj;
    }

    public String toString() {return v;}
}
