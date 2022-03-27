package ast;

public class IAlias extends I implements DefSub {
    private String id, ids;
    private T tipo;
    private boolean isId; // Sirve para diferenciar los casos
                          // pot par {
                          //    intix der;
                          //    intix izq;
                          // }
                          // datix parix par;
                          // y el caso 
                          // datix numero intix;

    public IAlias(String id, T tipo) {
        this.id = id;
        this.tipo = tipo;
        isId = false;
    }
    public IAlias(String id, String ids) {
        this.id = id;
        this.ids = ids;
        isId = false;
    }

	public KindI kind() {
		return KindI.ALIAS;
	}

    public String toString() {
        if (isId) {
            return "alias(" + id + "," + ids + ")";
        }
        return "alias(" + id + "," + tipo.toString() + ")";
    }
    
}
