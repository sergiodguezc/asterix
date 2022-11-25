package ast;


import org.json.simple.JSONObject;
import utils.Entero;

import java.io.PrintWriter;

public abstract class I implements ASTNode{
    public abstract KindI kind();
    public abstract JSONObject getJSON();
    public NodeKind nodeKind() {return NodeKind.INSTRUCCION;}
    public String toString() {return "";}
    
    // Función recursiva dentro del árbol que va calculando la posicion relativa
    // de las variables que declaramos dentro de memoria. La variable size
    // representa la posicion de la memoria desde el inicio hasta la última
    // variable que añadamos, en cambio, localSize representa la posicion 
    // relativa frente a la cual se calcula el delta.
    public void setDelta(Entero size, Entero localSize){}

    // Para generar el codigo de instrucciones
    public void generateCodeI(PrintWriter pw){}
}
