package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public abstract class E implements ASTNode {
    public abstract KindE kind();
    public abstract JSONObject getJSON();
    public void bind (SymbolMap ts) {};
    public E opnd1() {throw new UnsupportedOperationException("opnd1");} 
    public E opnd2() {throw new UnsupportedOperationException("opnd2");} 
    public String num() {throw new UnsupportedOperationException("num");}
    public NodeKind nodeKind() {return NodeKind.EXPRESION;}
    public String toString() {return "";}
    public abstract T type();
}