package ast;

import asem.SymbolMap;

import java.io.PrintWriter;

import org.json.simple.JSONObject;

public class Arg implements ASTNode {

    // Tipo del argumento
    private T tipo;

    // Nombre del argumento
    private String id;

    // Si se pasa por referencia o no
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
        ts.insertId(id,this);
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

    // Devuelve el tipo del argumento
    public T getType() {
        return tipo;
    }

    // TODO: Guardar en memoria los valores de las variables.
    // Ahora mismo solo se permiten pasos por valor (no por referencia).
	public void generateCode(PrintWriter pw) {
	    pw.print("(param $" + id );
        tipo.generateCode(pw);
        pw.print(")");
	}
}

