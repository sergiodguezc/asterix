package ast;

import java.util.List;

public class ELista extends E {
    private List<E> V;

    public ELista(List<E> V) {
        this.V = V;
    }

    public KindE kind() {
        return KindE.LISTA;
    }

    public String toString() {
        return "vector("+V.toString() + ")";
    }

}
