package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public interface ASTNode {
    // public ?? type() // for the future
    public void bind(SymbolMap ts);
    // public ?? generateCode() // for the future
    public NodeKind nodeKind();
    public String toString();
    public JSONObject getJSON();
}
