package ast;


import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

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
    public abstract void setDelta(AtomicInteger size, AtomicInteger localSize);

    // Para generar el codigo de instrucciones
    public abstract void generateCodeI(PrintWriter pw);
}
