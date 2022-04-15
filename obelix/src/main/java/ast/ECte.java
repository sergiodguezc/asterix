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

    public String getVal() {
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

    // TODO: Cambiar el cups para mandar un KindT en el constructor
    public T type() {
        if (dec != null)
            return dec.type();
        else if (v.equals("galo") || v.equals("romano"))
            return new T(KindT.BOOLIX);
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
