package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

import java.io.PrintWriter;

public interface ASTNode {

    // Si es una E : Devuelve el tipo (BOOLIX, INTIX, FLOATIX, VECTIX, POT) si es correcto o ERROR en cc
    // Si es una I : Comprueba si esta bien tipada, en ese caso devuelve INS, en cc devuelve ERROR
    public T type();

    // Vinculacion, utilizando la Tabla de Simbolos
    public void bind(SymbolMap ts);

    // Método para generar el código wasm
    public default void generateCode() {}

    // En la vinculacion, lo utilizamos para distinguir el ASTNode
    public NodeKind nodeKind();

    // Para escribir el AST
    public String toString();
    public JSONObject getJSON();

    // Para no tener bucles en la ejecución de type empleamos esta función para
    // obtener el tipo sin tener que volver a ejecutar type(). Solo llamamos a esta función
    // cuando hacemos una búsqueda en la tabla de símbolos.
    public default T getType() {return null;};

}
