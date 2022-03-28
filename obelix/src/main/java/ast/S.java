package ast;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

	@Override
	public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "SUBPROGRAMA");
        if (isMain) {
            obj.put("id", "panoramix");
        } else {
            obj.put("id", id);
            if (isFunction) {
                obj.put("tRet", tRet.getJSON());
                obj.put("vRet", vRet.getJSON());
            } 
            if (!args.isEmpty()) {
                JSONArray argsjson = new JSONArray();
                for ( Arg a : args ) {
                    argsjson.add(a.getJSON());
                }
                obj.put("args", argsjson);
            }
        }

        return obj;
	}
    
}
