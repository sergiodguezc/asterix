package ast;

public class IDecVar extends I{
    private T type;
    private E id;
    private boolean ini; // Booleano que indica si tambien se inicializa
    private E valor;

    public IDecVar (T type, E id) {
        this.type = type;
        this.id = id;
        ini = false;
    }
    public IDecVar(T type, E id, E valor) {
        this.type = type;
        this.id = id;
        this.valor = valor;
        ini = true;
    }
    public KindI kind() {return KindI.DEC;}
    public String toString() {
        if (ini)
            return "Dec(" + type.toString() + "," + id.toString() + "," + valor.toString() + ")";
        else
            return "Dec(" + type.toString() + "," + id.toString() + ")";
    }
}
