package ast;

import org.json.simple.JSONObject;

import java.util.List;

public class P implements ASTNode {
    private List<DefSub> defsubs;

    public P(List<DefSub> defsubs, DefSub nuevo) {
        this.defsubs = defsubs;
        defsubs.add(nuevo);
    }
	public NodeKind nodeKind() {
		return NodeKind.PROGRAMA;
	}

	public String toString() {
		return "P("+defsubs.toString()+")";
	}

	@Override
	public JSONObject getJSON() {
		return null;
	}
}
