package asem;

import ast.KindT;
import ast.T;

public class ASemUtils {

    public static boolean checkEqualTypes(T t1, T t2) {
        // Caso base 1: Tipos distintos, se devuelve falso
        if (t1.getKindT() != t2.getKindT()) {
            return false;
        // Caso base 2: Tipos iguales no son vectix, se devuelve true
        } else if (t1.getKindT() != KindT.VECTIX) {
            return true;
        }
        // Caso recursivo: Los dos tipos son vectix, y se compara recursivamente los tipos internos
        else {
            return checkEqualTypes(t1.type(), t2.type());
        }

    }
}
