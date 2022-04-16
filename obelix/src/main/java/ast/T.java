package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public class T implements ASTNode {
    private KindT kindT;
    private String id; // Identificador que hace referencia a un alias o a un struct
    private int N; // Longitud del vector
    private T tipo; // Tipo del vector o del alias (significado doble)
    private IDecStruct dec; // Declaracion del struct que obtenemos del bind()

    public T(KindT kindT) {
        this.kindT = kindT;
    }

    public T(String id) {
        this.id = id;
    }

    public T(T tipo, int N) {
        this.kindT = KindT.VECTIX;
        this.tipo = tipo;
        this.N = N;
    }

    public int getVSize() {
        return N;
    }

    public KindT getKindT() {
        return kindT;
    }

    public void bind(SymbolMap ts) {
        if (id != null) { // Caso que sea un alias o un struct
            I def = (I) ts.searchId(id);
            if (def.kind() == KindI.ALIAS) {
                tipo = def.type();
                kindT = KindT.ALIAS;
            }
            else {
                kindT = KindT.POT;
                dec = (IDecStruct) def;
            }
        }
        else if (kindT == KindT.VECTIX) {
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

    public IDecStruct getDec() {
        return dec;
    }

    // Si es un ALIAS : Devuelve el tipo al que hace referencia
    // Si es un VECTIX : Devuelve el tipo de los elementos del vector
    // En cc : Devuelve null.
    public T type() {
        return tipo;
    }
}
