package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public class T implements ASTNode {
    private KindT kindT;
    private String aliasId;
    private int N; // Longitud del vector
    private T tipo; // Tipo del vector o del alias (significado doble)

    public T(KindT kindT) {
        this.kindT = kindT;
    }

    public T(String aliasId) {
        this.aliasId = aliasId;
        kindT = KindT.ALIAS;
    }

    public T(T tipo, int N) {
        this.kindT = KindT.VECTIX;
        this.tipo = tipo;
        this.N = N;
    }

    public KindT getKindT() {
        return kindT;
    }

    public void bind(SymbolMap ts) {
        if (kindT == KindT.ALIAS) {
            // Conseguir el tipo de un alias
            IAlias def = (IAlias) ts.searchId(aliasId);
            tipo = def.type();
        } else if (kindT == KindT.VECTIX) {
            tipo.bind(ts);
        }

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
        if (kindT == KindT.VECTIX) {
            obj.put("kindT", "vectix");
            obj.put("tipo", tipo.getJSON());
            obj.put("longitud", N);
        } else {
            obj.put("kindT", kindT);
        }
        return obj;
    }

    public T type() {
        return tipo;
    }
}
