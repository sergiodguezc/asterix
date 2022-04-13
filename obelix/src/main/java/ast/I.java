package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public abstract class I implements ASTNode{
    public abstract KindI kind();
    public abstract JSONObject getJSON();
    public NodeKind nodeKind() {return NodeKind.INSTRUCCION;}
    public String toString() {return "";}
    public void bind(SymbolMap ts) {};
    public abstract KindT type();
}
