package asem;

import ast.KindT;
import ast.T;

public class ASemUtils {

    public static boolean checkEqualTypes(T t1, T t2) {
        // Caso base 1: Tipos distintos, se devuelve falso
        if (t1.getKindT() != t2.getKindT() || t1.getKindT() == KindT.ERROR) {
            return false;
        // Caso base 2: Tipos iguales no son vectix, se devuelve true
        } else if (t1.getKindT() != KindT.VECTIX) {
            return true;
        // Caso base 3: Tipos iguales vectix y elemento derecha es una lista vacía
        } else if (t2.type() == null) {
            return true;
        // Caso base 4: Tipos iguales vectix y tamaños distintos
        } else if (t1.getVSize() != t2.getVSize()) {
            return false;
        }
        // Caso recursivo: Los dos tipos son vectix, y se compara recursivamente los tipos internos
        else {
            return checkEqualTypes(t1.type(), t2.type());
        }

    }
}
