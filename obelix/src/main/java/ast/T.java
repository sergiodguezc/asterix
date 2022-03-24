package ast;

public class T implements ASTNode {
    private String kindT;

    public T(String kindT){this.kindT = kindT;}
    public NodeKind nodeKind() {return NodeKind.TIPO;}
    public String toString() {return kindT;}
}
