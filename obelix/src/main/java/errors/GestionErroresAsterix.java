package errors;

import alexop.UnidadLexica;

public class GestionErroresAsterix {
    public static int numErroresSintacticos;
    public static int numErroresSemanticos;

    public GestionErroresAsterix() {
        numErroresSintacticos = 0;
        numErroresSemanticos = 0;
    }

    public void errorLexico(int fila, int columna, String lexema) {
        System.err.println(
                "***ERROR lexico. Fila: " + fila + ", Columna: " + columna + " Elemento inesperado \"" + lexema + "\"");
        System.exit(1);
    }

	public void errorSintactico(UnidadLexica unidadLexica) {
        if (unidadLexica.lexema() != null) {
            System.err.println("***ERROR sintactico. Fila: " + unidadLexica.fila() + ", Columna: "
                    + unidadLexica.columna() + " Elemento inesperado \"" + unidadLexica.lexema() + "\"");
        } else {
            System.err.println("***ERROR sintactico. Fila: " + unidadLexica.fila() + ", Columna: "
                    + unidadLexica.columna() + " Elemento inesperado " + unidadLexica.lexema());
        }
    }
}
