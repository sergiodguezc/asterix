package ast;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;

import java.io.PrintWriter;
import java.util.List;

import org.json.simple.JSONObject;
import utils.CodeUtils;

public class IAsig extends I {

    // Expresión de la izquierda
    private E id;
    // Expresión de la derecha que vamos a asignar a la
    // de la izquierda.
    private E valor;

    private CodeUtils utils;

    // CONSTRUCTOR
    public IAsig(E id, E valor, CodeUtils utils) {
        this.id = id;
        this.valor = valor;
        this.utils = utils;
    }

    // AST
    public KindI kind() {
        return KindI.ASIG;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION ASIGNACION");
        obj.put("variable", id.getJSON());
        obj.put("valor", valor.getJSON());
        return obj;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    // VINCULACION
    public void bind(SymbolMap ts) {
        id.bind(ts);
        valor.bind(ts);
    }

    // TIPADO
	public T type() {
        // Tenemos que comprobar que esta bien tipado
        T tipoId = id.type();
        T tipoValor = valor.type();

        if (ASemUtils.checkEqualTypes(tipoId, tipoValor))
            return new T(KindT.INS);

        GestionErroresAsterix.errorSemantico("Error de tipado en la asignacion");
		return new T(KindT.ERROR);
	}


    // GENERACIÓN DE CÓDIGO
	public void generateCodeI(PrintWriter pw) {
        pw.println(";; instruccion asignacion");
        // Tipos son básicos
        if (id.getType().getKindT() != KindT.VECTIX && id.getType().getKindT() != KindT.POT) {
            // Necesitamos es calcular la posicion de memoria de la al que queremos
            // asignar el valor de la expresión. En este caso, necesitamos la
            // posicion de id.
            pw.println(";; codigo del designador");
            id.generateCodeD(pw); // en el caso en que sea una variable calcula su
            // posición con el localStart ya sumado.

            // Generamos el código que calcula el valor de la expresión
            pw.println(";; calculamos el valor a guardar");
            valor.generateCodeE(pw);

            // Generamos el código que calcula el tipo que devuelve y lo
            // yuxtaponemos a '.store'.
            pw.println(";; guardamos el valor");
            id.getType().generateCode(pw);
            pw.println(".store");
        }
        // Tipo es un vector. En este caso, el valor siempre será una lista de elementos del mismo tipo
        else {
            if (valor.kind() == KindE.LISTA) {
                pw.println(";; asignacion a una lista");
                List<E> lista = ((ELista) valor).getLista();
                int offset = 0;
                for (E elem : lista) {
                    // Calculamos la posición con el local start ya sumado del inicio del vector
                    pw.println(";; designador del elemento del vector");
                    id.generateCodeD(pw);
                    // sumamos el offset de cada elemento del vector
                    pw.println("i32.const " + offset);
                    pw.println("i32.add");

                    // Generamos el codigo de evaluación de cada elemento
                    pw.println(";; valor del elemento de la lista a guardar");
                    elem.generateCodeE(pw);

                    // Hacemos el store del valor en la posición previamente calculada
                    pw.println(";; guardamos el valor");
                    elem.getType().generateCode(pw);
                    pw.println(".store");

                    // Calculamos el tamaño del tipo que hemos calculado y lo sumamos al offset
                    elem.getType().setSizeT();
                    offset += elem.getType().getSizeT();
                }
            }
            else {
                pw.println(";; asignacion a una vector o un struct");
                // Obtenemos la direccion de origen
                pw.println(";; calculamos el designador ($src)");
                valor.generateCodeD(pw);

                // Obtenemos la direccion de destino
                pw.println(";; calculamos el designador ($dest)");
                id.generateCodeD(pw);

                // Obtenemos el numero de bytes a copiar
                pw.println(";; calculamos el numero de bytes a copiar");
                id.getType().setSizeT();
                pw.println("i32.const " + id.getType().getSizeT());

                // Llamamos a la función copyn
                pw.println(";; llamamos a copyn");
                utils.showCopyn();
                pw.println("call $copyn");
            }
        }
	}
}
