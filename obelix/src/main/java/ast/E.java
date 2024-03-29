package ast;

import java.io.PrintWriter;

import org.json.simple.JSONObject;

public abstract class E implements ASTNode {
    public abstract KindE kind();
    public abstract JSONObject getJSON();
    public E opnd1() {throw new UnsupportedOperationException("opnd1");} 
    public E opnd2() {throw new UnsupportedOperationException("opnd2");} 
    public String getVal() {throw new UnsupportedOperationException("val");}
    public NodeKind nodeKind() {return NodeKind.EXPRESION;}
    public String toString() {return "";}

    public void generateCodeE(PrintWriter pw){}

    public void generateCodeD(PrintWriter pw){}

    // Código designador para los accesos a struct
    public void generateCodeAccS(PrintWriter pw, E opnd2, boolean designador){}

}
