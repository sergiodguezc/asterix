package ast;

public class IAlias extends I implements DefSub {
    private String id, ids;
    private T tipo;

    public IAlias(String id, T tipo) {
        this.id = id;
        this.tipo = tipo;
    }

	public KindI kind() {
		return KindI.ALIAS;
	}

    public String toString() {
        return "alias(" + id + "," + tipo.toString() + ")";
    }
    
}
