    package asem;

import ast.P;

public class AnalizadorSemanticoAsterix {
    private SymbolMap tabla;
    private P programa;

    public AnalizadorSemanticoAsterix(P programa) {
        tabla = new SymbolMap();
        this.programa = programa;
    }

    public void analizaSemantica() {
        // Primero realizamos la vinculación y luego el analisis de tipo
        programa.bind(tabla);
        programa.type();
    }

}
