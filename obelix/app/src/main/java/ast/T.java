package ast;

public abstract class T implements ASTNode {
    public abstract KindE kind();
    public NodeKind nodeKind() {return NodeKind.TIPO;}
    public String toString() {return "";}
}
