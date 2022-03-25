package ast;

import java.util.List;

public class ICall extends I {
    private List<E> params;
    private E id;

    public ICall(List<E> params, E id, E nuevo) {
        this.params = params;
        this.id = id;
        params.add(nuevo);
    }

    public KindI kind() {
        return KindI.CALL;
    }

    public String toString() {
        return "call("+id.toString()+","+params.toString()+")";
    }

}
