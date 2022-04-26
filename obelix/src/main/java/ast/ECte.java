package ast;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

import org.json.simple.JSONObject;

import java.util.List;

public class ECte extends E {
    private KindT kindT;
    private String v;
    private ASTNode dec;
    private T tipoECte;

    public ECte(String v) {
        this.v = v;
    }

    public ECte(String v, KindT kindT) {
        this.v = v;
        this.kindT = kindT;
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

    @Override
    public T getType() {
        return tipoECte;
    }

    public T type() {
        if (dec != null) {
            return dec.getType();
        }
        else if (kindT == KindT.INTIX || kindT == KindT.FLOATIX || kindT == KindT.BOOLIX)
            return (tipoECte = new T(kindT));

        GestionErroresAsterix.errorSemantico("Variable '" + v + "' sin declarar");
        return (tipoECte = new T(KindT.ERROR));
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    // Si es una variable dec hace referencia a la declaracion de esa variable
    // En caso contrario, dec es null.
    public void bind(SymbolMap ts) {
        dec = ts.searchId(v);
    }
}
