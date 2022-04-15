package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public class IAlias extends I implements DefSub {
    private String id;
    private T tipo;

    public IAlias(String id, T tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public KindI kind() {
        return KindI.ALIAS;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION ALIAS");
        obj.put("alias", id);
        obj.put("tipo", tipo.getJSON());
        return obj;
    }

    // En este caso, devolvemos el tipo al que ha hecho alias.
    public T type() {
        // Recursion al tipo al que hace referencia
        tipo.type();
        return tipo;
    }

    public void bind(SymbolMap ts) {
        ts.insertId(id, this);
    }

    public String toString() {
        return getJSON().toJSONString();
    }

}
