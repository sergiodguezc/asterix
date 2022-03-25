package ast;

import java.util.List;

public class P implements ASTNode{
    private List<IDec> declaraciones;

    public P(List<IDec> declaraciones) {
        this.declaraciones = declaraciones;
    }
	public NodeKind nodeKind() {
		return NodeKind.PROGRAMA;
	}

	public String toString() {
		return "P("+")";
	}
}
