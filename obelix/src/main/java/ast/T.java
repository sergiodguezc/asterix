package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public class T implements ASTNode {
    private String kindT;
    private String N; // Longitud del vector
    private T tipo; // Tipo del vector o del alias (significado doble)
    private Boolean isVector;

    public T(String kindT) {
        this.kindT = kindT;
        isVector = false;
    }

    public T(T tipo, String N) {
        isVector = true;
        this.tipo = tipo;
        this.N = N;
    }

    public void bind(SymbolMap ts) {
        IAlias def = (IAlias) ts.searchId(kindT);
        tipo = def.type();
    };

    public NodeKind nodeKind() {
        return NodeKind.TIPO;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "TIPO");
        if (isVector) {
            obj.put("kindT", "vectix");
            obj.put("tipo", tipo.getJSON());
            obj.put("longitud", N);
        } else {
            obj.put("kindT", kindT);
        }
        return obj;
    }

    public KindT getKindT() {
        return null;
    }

    public T type() {
        return this;
    }
}
