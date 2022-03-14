package asint;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexicoAsterix;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 AnalizadorLexicoAsterix alex = new AnalizadorLexicoAsterix(input);
	 AnalizadorSintacticoAsterix asint = new AnalizadorSintacticoAsterix(alex);
	 asint.parse();
 }
}   
   
