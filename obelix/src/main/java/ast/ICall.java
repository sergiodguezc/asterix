package ast;

import java.util.List;

public class ICall extends I {
    private List<E> params;
    private String id;
    private Boolean noParams;

    public ICall(List<E> params, String id) {
        this.params = params;
        this.id = id;
        noParams = false;
    }
    public ICall(String id) {
        this.id = id;
        noParams = true;
    }

    public KindI kind() {
        return KindI.CALL;
    }

    public String toString() {
        if (noParams)
            return "call("+id +")";
        return "call("+id+","+params.toString()+")";
    }

}
