package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public class Arg implements ASTNode {
    private T tipo;
    private String id;
    private boolean ref;

    public Arg (T tipo, String id, boolean ref){
        this.tipo = tipo;
        this.id = id;
        this.ref = ref;
    }

    public T type() {
        tipo.type();
        return tipo;
    }

    public void bind(SymbolMap ts) {
        // TODO: Usar los nodekind en los casteos en otro lado
        IDec dec = new IDecVar(tipo, id);
        ts.insertId(id,dec);
        tipo.bind(ts);
    }

    public NodeKind nodeKind() {
		return NodeKind.ARG;
	}

    public String toString () {
        return getJSON().toJSONString();
    }


    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("nodo", "ARGUMENTO");
        obj.put("tipo",tipo.getJSON());
        if(ref)
            obj.put("referencia", "SI");
        else
            obj.put("referencia", "NO");
        obj.put("id", id);
        return obj;
    }
}

