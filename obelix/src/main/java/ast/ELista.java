package ast;

import java.util.List;

public class ELista extends E {
    private E N; // Indica el tamaño inicial del vector
    private List<E> V;
    public ELista(E N, E valor, List<E> V) {
        this.N = N;
        this.V = V;
        V.add(valor);
    }

    public KindE kind() { return KindE.LISTA;}
    public String toString() { return V.toString();}

}
