package ast;

import asem.SymbolMap;
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

    public KindT type() {
        if (v = "galo" || v = "romano")
            return BOOLIX;
        else if (ts.searchid(v)) {
            return tipo declaracion;
        }
        else if to
    }

    public String toString() {return getJSON().toJSONString();}
}
