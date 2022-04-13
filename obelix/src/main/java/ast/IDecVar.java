package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public class IDecVar extends IDec {
    private T type;
    private String id;
    private boolean ini; // Booleano que indica si tambien se inicializa
    private E valor;

    public IDecVar(T type, String id) {
        this.type = type;
        this.id = id;
        ini = false;
    }

    public IDecVar(T type, String id, E valor) {
        this.type = type;
        this.id = id;
        this.valor = valor;
        ini = true;
    }

    public KindI kind() {
        return KindI.DEC;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION DECLARACION");
        obj.put("id", id);
        obj.put("tipo", type.getJSON());
        if (!ini)
            return obj;
        obj.put("valor", valor.getJSON());
        return obj;
    }

    public void bind(SymbolMap ts) {
        ts.insertId(id, this);
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    @Override
    public T type() {
        return null;
    }
}
