/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package obelix;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import alex.AnalizadorLexicoAsterix;
import asint.AnalizadorSintacticoAsterix;

public class App {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 AnalizadorLexicoAsterix alex = new AnalizadorLexicoAsterix(input);
	 AnalizadorSintacticoAsterix asint = new AnalizadorSintacticoAsterix(alex);
	 asint.parse();
 }
}   
   