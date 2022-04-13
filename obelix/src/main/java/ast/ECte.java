package ast;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

import org.json.simple.JSONObject;

public class ECte extends E {
    private String v;
    private IDec dec;

    public ECte(String v) {
        this.v = v;
    }

    public String num() {
        return v;
    }

    public KindE kind() {
        return KindE.CTE;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "EXPRESION CONSTANTE");
        obj.put("valor", v);
        return obj;
    }

    public T type() {
        if (dec != null)
            return dec.type();
        else {
            GestionErroresAsterix.errorSemantico("Variable '" + v + "' sin declarar");
            return new T(KindT.ERROR);
        }
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    public void bind(SymbolMap ts) {
        dec = (IDec) ts.searchId(v);
    }
}
