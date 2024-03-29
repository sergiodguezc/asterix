package ast;

import asem.SymbolMap;

import org.json.simple.JSONObject;
import utils.Entero;

public class Arg implements ASTNode {

    // Tipo del argumento
    private T tipo;

    // Nombre del argumento
    private String id;

    // Si se pasa por referencia o no
    private boolean ref;

    // Delta de los args dentro
    private int delta;

    public Arg (T tipo, String id, boolean ref){
        this.tipo = tipo;
        this.id = id;
        this.ref = ref;
    }

    // AST
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

    // VINCULACION
    public void bind(SymbolMap ts) {
        ts.insertId(id,this);
        tipo.bind(ts);
    }

    // TIPADO
    public T type() {
        tipo.type();
        return tipo;
    }

    // GENERACION DE CODIGO
    public void setDelta(Entero size, Entero localsize) {
        if (ref) {
            delta = localsize.get(); // size del puntero
            size.add(4);
            localsize.add(4);
        } else {
            delta = localsize.get(); // size del tipo que copiamos

            // Aumentamos el size y el localsize
            tipo.setSizeT();
            size.add(tipo.getSizeT());
            localsize.add(tipo.getSizeT());
        }

    }

    // GETTERS AND SETTERS
    public String getId() {
        return id;
    }


    // Devuelve el tipo del argumento
    public T getType() {
        return tipo;
    }

    public boolean isRef() {
        return ref;
    }


    public int getDelta() {
        return delta;
    }
}

