package ast;

import java.util.List;

public class Sub implements ASTNode, DefSub {
    private List<I> cuerpo;
    private List<Arg> args;
    private boolean isFunction;
    private boolean isMain;
    private E id;
    private T tRet;
    private E vRet;

    public Sub(List<I> cuerpo, List<Arg> args,E id, T tRet, E vRet) {
        this.cuerpo = cuerpo;
        this.args = args;
        this.id = id;
        this.tRet = tRet;
        this.vRet = vRet;
        isFunction = true;
        isMain = false;
    }

    public Sub(List<I> cuerpo, List<Arg> args, E id) {
        this.cuerpo = cuerpo;
        this.id = id;
        this.args = args;
        isMain = false;
        isFunction = false;
    }

    public Sub(List<I> cuerpo) {
        this.cuerpo = cuerpo;
        isMain = true;
    }

	public NodeKind nodeKind() {
		return NodeKind.SUBPROGRAMA;
	}

    public String toString() {
        if (isMain) {
            return "main("+cuerpo.toString()+")";
        }
        if (isFunction) {
            return "fun("+id.toString()+","+args.toString()+","+cuerpo.toString()+","+tRet.toString()+","+vRet.toString()+")";
        }
        return "proc("+id.toString()+","+args.toString()+","+cuerpo.toString()+")";
    }
    
}
