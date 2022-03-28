package ast;

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
            return tipo.toString()+" : &"+id.toString();

        return tipo.toString()+" : "+id.toString();
    }
}

