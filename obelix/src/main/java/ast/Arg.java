package ast;

public class Arg implements ASTNode {
    private T tipo;
    private String idA;
    private String id;
    private boolean ref;
    private boolean isAlias;

    public Arg (T tipo, String id, boolean ref){
        this.tipo = tipo;
        this.id = id;
        this.ref = ref;
        isAlias = false;
    }
    public Arg (String idA, String id, boolean ref){
        this.idA = idA;
        this.id = id;
        this.ref = ref;
        isAlias = true;
    }

	public NodeKind nodeKind() {
		return NodeKind.ARG;
	}

    public String toString () {
        if (ref) {
            if (isAlias)
                return idA.toString()+" : &"+id.toString();
            else
                return tipo.toString()+" : &"+id.toString();
        }
        if (isAlias)
            return idA.toString()+" : "+id.toString();
        else
            return tipo.toString()+" : "+id.toString();
    }
}

