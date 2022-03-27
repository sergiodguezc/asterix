package ast;

import java.util.List;

public class ECall extends E{
    private List<E> args;
    private String id;
    boolean noParams;

    public ECall(List<E> args, String id) {
        this.args = args;
        this.id = id;
        noParams = false;
    }
    public ECall(String id) {
        this.id = id;
        noParams = true;
    }

    public KindE kind() {return KindE.CALL;}
    public String toString() {
        if(noParams)
            return "call("+id+")";
        return "call("+id+","+args.toString()+")";
    }
}
