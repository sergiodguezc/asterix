package ast;

import asem.ASemUtils;
import asem.SymbolMap;
import com.rits.cloning.Cloner;
import errors.GestionErroresAsterix;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONObject;

public class IAsig extends I {

    private E id;
    private E valor;

    public IAsig(E id, E valor) {
        this.id = id;
        this.valor = valor;
    }

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

    public void bind(SymbolMap ts) {
        id.bind(ts);
        valor.bind(ts);
    }

	public T type() {
        // Tenemos que comprobar que esta bien tipado
        T tipoId = id.type();
        T tipoValor = valor.type();

        if (ASemUtils.checkEqualTypes(tipoId, tipoValor))
            return new T(KindT.INS);

        GestionErroresAsterix.errorSemantico("Error de tipado en la asignacion");
		return new T(KindT.ERROR);
	}

	@Override
	public void generateCodeI(PrintWriter pw) {
        // Tipos son básicos
        if (id.getType().getKindT() != KindT.VECTIX && id.getType().getKindT() != KindT.POT) {
            // Necesitamos es calcular la posicion de memoria de la al que queremos
            // asignar el valor de la expresión. En este caso, necesitamos la
            // posicion de id.
            id.generateCodeD(pw); // en el caso en que sea una variable calcula su
            // posición con el localStart ya sumado.

            // Generamos el código que calcula el valor de la expresión
            valor.generateCodeE(pw);

            // Generamos el código que calcula el tipo que devuelve y lo
            // yuxtaponemos a '.store'.
            id.getType().generateCode(pw);
            pw.println(".store");
        }
        // Tipo es un vector. En este caso, el valor siempre será una lista de elementos del mismo tipo
        else if (id.getType().getKindT() == KindT.VECTIX) {
            List<E> lista = ((ELista) valor).getLista();
            int offset = 0;
            for (E elem : lista) {
                // Calculamos la posición con el local start ya sumado del inicio del vector
                id.generateCodeD(pw);
                // sumamos el offset de cada elemento del vector
                pw.println("i32.const " + offset);
                pw.println("i32.add");

                // Generamos el codigo de evaluación de cada elemento
                elem.generateCodeE(pw);

                // Hacemos el store del valor en la posición previamente calculada
                elem.getType().generateCode(pw);
                pw.println(".store");

                // Calculamos el tamaño del tipo que hemos calculado y lo sumamos al offset
                elem.getType().setSizeT();
                offset += elem.getType().getSizeT();
            }
        }
	}

    public void setDelta(AtomicInteger size, AtomicInteger localSize) {
        // Las asignaciones no crean variables. No hacemos nada.
	}
        // Caso Recursivo : Es un vector y valor es una ELista

}
