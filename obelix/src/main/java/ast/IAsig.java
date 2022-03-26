package ast;

public class IAsig extends I {

    private E id;
    private E valor;

    public IAsig(E id, E valor) {
        this.id = id;
        this.valor = valor;
    }

    public KindI kind() {return KindI.ASIG;}
    public String toString() {
        return "asig(" + id.toString() + "," + valor.toString() + ")";
    }
}
