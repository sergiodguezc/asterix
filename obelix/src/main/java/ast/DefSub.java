package ast;

import java.io.PrintWriter;

public interface DefSub extends ASTNode {

    // Interfaz para subprogramas, declaraciones y alias
    // Un programa esta formado por una lista de DefSub
    //
    // Empleamos esta función para generar las variables globales en las IDec
    // y para llamar a generateCodeI en los subprogramas.
	public void generateCode(PrintWriter pw);

}

