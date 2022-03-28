package ast;

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

	public NodeKind nodeKind() {
		return NodeKind.ARG;
	}

    public String toString () {
        if (ref)
            return "arg(" + tipo.toString() + ",ref," + id +")";
        return "arg(" + tipo.toString() + "," + id +")";
    }

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

