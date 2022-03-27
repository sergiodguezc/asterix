package ast;

import java.util.List;

public class S implements ASTNode, DefSub {
    private List<I> cuerpo;
    private List<Arg> args;
    private boolean isFunction;
    private boolean isMain;
    private String id;
    private T tRet;
    private E vRet;

    public S(List<I> cuerpo, List<Arg> args, String id, T tRet, E vRet) {
        this.cuerpo = cuerpo;
        this.args = args;
        this.id = id;
        this.tRet = tRet;
        this.vRet = vRet;
        isFunction = true;
        isMain = false;
    }

    public S(List<I> cuerpo, List<Arg> args, String id) {
        this.cuerpo = cuerpo;
        this.id = id;
        this.args = args;
        isMain = false;
        isFunction = false;
    }

    public S(List<I> cuerpo, E vRet) {
        this.cuerpo = cuerpo;
        this.vRet = vRet;
        isMain = true;
    }

	public NodeKind nodeKind() {
		return NodeKind.SUBPROGRAMA;
	}

    public String toString() {
        if (isMain) {
            return "main("+cuerpo.toString()+"," + vRet.toString() + ")";
        }
        if (isFunction) {
            return "fun("+id+","+args.toString()+","+cuerpo.toString()+","+tRet.toString()+","+vRet.toString()+")";
        }
        return "proc("+id+","+args.toString()+","+cuerpo.toString()+")";
    }
    
}
