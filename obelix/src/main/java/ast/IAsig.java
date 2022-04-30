package ast;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;

import java.io.PrintWriter;
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

    public void setDelta(AtomicInteger size, AtomicInteger localSize) {
        // Las asignaciones no crean variables. No hacemos nada.
	}

}
