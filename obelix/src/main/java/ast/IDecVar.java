package ast;

public class IDecVar extends I{
    private T type;
    private E id;
    private E idA; // Identificador del tipo (alias)
    private boolean ini; // Booleano que indica si tambien se inicializa
    private boolean isAlias;
    private E valor;

    public IDecVar (T type, E id) {
        this.type = type;
        this.id = id;
        ini = false;
        isAlias = false;
    }
    public IDecVar(T type, E id, E valor) {
        this.type = type;
        this.id = id;
        this.valor = valor;
        ini = true;
        isAlias = false;
    }
    public IDecVar(E idA, E id) {
        this.idA = idA;
        this.id = id;
        ini = false;
        isAlias = true;
    }
    public IDecVar(E idA, E id, E valor) {
        this.idA = idA;
        this.id = id;
        this.valor = valor;
        ini = true;
        isAlias = true;
    }
    public KindI kind() {return KindI.DEC;}
    public String toString() {
        if (ini)
            if (!isAlias)
                return "Dec(" + type.toString() + "," + id.toString() + "," + valor.toString() + ")";
            else
                return "Dec(" + idA.toString() + "," + id.toString() + "," + valor.toString() + ")";
        else
            if (!isAlias)
                return "Dec(" + type.toString() + "," + id.toString() + ")";
            else
                return "Dec(" + idA.toString() + "," + id.toString() + ")";
    }
}
