package ast;

public class IDecVar extends IDec {
    private T type;
    private String id;
    private boolean ini; // Booleano que indica si tambien se inicializa
    private E valor;

    public IDecVar (T type, String id) {
        this.type = type;
        this.id = id;
        ini = false;
    }
    public IDecVar(T type, String id, E valor) {
        this.type = type;
        this.id = id;
        this.valor = valor;
        ini = true;
    }
    public KindI kind() {return KindI.DEC;}
    public String toString() {
        if (ini)
            return "Dec(" + type.toString() + "," + id + "," + valor.toString() + ")";
        return "Dec(" + type.toString() + "," + id + ")";
    }
}
