package ast;

import org.json.simple.JSONObject;

public abstract class I implements ASTNode{
    public abstract KindI kind();
    public abstract JSONObject getJSON();
    public NodeKind nodeKind() {return NodeKind.INSTRUCCION;}
    public String toString() {return "";}
}
