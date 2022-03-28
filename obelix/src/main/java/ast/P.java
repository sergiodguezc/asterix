package ast;

import org.json.simple.JSONArray;
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
        JSONObject obj = new JSONObject();
        obj.put("node", "PROGRAMA");
        if (!defsubs.isEmpty()) {
            JSONArray defsubsjson = new JSONArray();
            for (DefSub df : defsubs) {
                defsubsjson.add(df.getJSON());
            }
            obj.put("DefSub", defsubs);
        }
		return obj;
	}
}
