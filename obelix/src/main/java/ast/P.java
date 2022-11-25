package ast;

import asem.SymbolMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.CodeUtils;

import java.io.PrintWriter;
import java.util.List;

public class P implements ASTNode {
    private List<S> subprogramas;
    private List<I> definiciones;
    private CodeUtils utils;

    public P(List<I> definiciones, List<S> subprogramas, S main, CodeUtils utils) {
        this.definiciones = definiciones;
        this.subprogramas = subprogramas;
        subprogramas.add(main);
        this.utils = utils;
    }

    // AST
    public NodeKind nodeKind() {
        return NodeKind.PROGRAMA;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "PROGRAMA");
        if(!definiciones.isEmpty()) {
            JSONArray defjson = new JSONArray();
            for (I df : definiciones) {
                defjson.add(df.getJSON());
            }
            obj.put("Definiciones", defjson);
        }
        if (!subprogramas.isEmpty()) {
            JSONArray subjson = new JSONArray();
            for (S sub : subprogramas) {
                subjson.add(sub.getJSON());
            }
            obj.put("Subprogramas", subjson);
        }
        return obj;
    }

    // VINCULACION
    public void bind(SymbolMap ts) {
        // Creamos el bloque principal con los identificadores
        // de las definiciones
        ts.openBlock();

        // Recursion a las definiciones
        for (I def : definiciones) {
            def.bind(ts);
        }

        // Recursion a los subprogramas
        for (S sub : subprogramas) {
            sub.bind(ts);
        }

        ts.closeBlock();
    }

    // TIPADO
    public T type() {
        for (I def : definiciones)
            def.type();
        for (S sub : subprogramas)
            sub.type();
        return new T(KindT.INS);
    }

    // GENERACION DE CODIGO
    public void generateCode(PrintWriter pw) {
        pw.println("(module ");
        // Cabecera del archivo wat, contiene las paginas de memoria del programa
        // y los punteros a la pila de memoria.
        pw.print(utils.generateCabecera());
        
        // Generamos el código de las funciones y las definiciones
        utils.generateMetaMain(pw, definiciones);
        for(S sub : subprogramas) {
            sub.generateCode(pw);
        }

        // Antes de cerrar el módulo, creamos las funciones auxiliares $reserveStack
        // $freeStack y $copyn.
        pw.print(utils.generateAuxFunc());
        
        // Cerramos el módulo
        pw.print(")");
    }
}
