/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package obelix;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import alex.AnalizadorLexicoAsterix;
import asem.AnalizadorSemanticoAsterix;
import asint.AnalizadorSintacticoAsterix;
import ast.P;
import code.CodeGenerator;
import errors.GestionErroresAsterix;

public class App {
    public static void main(String[] args) throws Exception {
        Reader input = new InputStreamReader(new FileInputStream("struct_test.atx"));
        AnalizadorLexicoAsterix alex = new AnalizadorLexicoAsterix(input);
        AnalizadorSintacticoAsterix asint = new AnalizadorSintacticoAsterix(alex);
        P programa = (P) asint.parse().value;
        AnalizadorSemanticoAsterix asem = new AnalizadorSemanticoAsterix(programa);
        asem.analizaSemantica();
        CodeGenerator cg = new CodeGenerator(programa,"struct_test");

        if (GestionErroresAsterix.numErroresSemanticos + GestionErroresAsterix.numErroresSintacticos == 0 )
            cg.generateCode();

        if (args.length>1) {
            if (GestionErroresAsterix.numErroresSemanticos + GestionErroresAsterix.numErroresSintacticos == 0 ) {
                try (FileWriter file = new FileWriter(args[1])) {
                    try {
                        file.write(programa.toString());
                        file.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("*** Errores en el código, no se genera salida.");
            }
        }
    }
}
