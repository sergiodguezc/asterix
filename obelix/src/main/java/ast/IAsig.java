package ast;

public class IAsig extends I {

    private String id;
    private E valor;

    public IAsig(String id, E valor) {
        this.id = id;
        this.valor = valor;
    }

    public KindI kind() {return KindI.ASIG;}
    public String toString() {
        return "asig(" + id + "," + valor.toString() + ")";
    }
}
