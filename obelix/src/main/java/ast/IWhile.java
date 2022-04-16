package ast;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

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

    public String toString() {
        return getJSON().toJSONString();
    }

    @SuppressWarnings("unchecked")
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

    public T type() {
        T tipocond = cond.type();
        boolean error = false;

        if (tipocond.getKindT() != KindT.BOOLIX) {
            error = true;
            GestionErroresAsterix.errorSemantico("Error de tipo en la condicion del while. Debe ser tipo boolix.");
        }

        for (I ins : cuerpoWhile) {
            ins.type();
        }


        return error ? new T(KindT.ERROR) : new T(KindT.BOOLIX);
    }

    public void bind(SymbolMap ts) {
        cond.bind(ts);
        ts.openBlock();
        for (I ins : cuerpoWhile) {
            ins.bind(ts);
        }
        ts.closeBlock();
    }

}
