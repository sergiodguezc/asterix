package ast;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

public class IWhile extends I {
    private E cond;
    private T tipo;
    private List<I> cuerpoWhile;

    public IWhile(E cond, List<I> cuerpoWhile) {
        this.cond = cond;
        this.cuerpoWhile = cuerpoWhile;
    }

    public KindI kind() {
        return KindI.WHILE;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION WHILE");
        obj.put("cond", cond.getJSON());
        if (!cuerpoWhile.isEmpty()) {
            JSONArray cuerpojson = new JSONArray();
            for (I i : cuerpoWhile) {
                cuerpojson.add(i.getJSON());
            }
            obj.put("cuerpo", cuerpojson);
        }
        return obj;
    }

    public T type() {
        T tipocond = cond.type();
        boolean error = false;

        if (tipocond.getKindT() != KindT.BOOLIX) {
            error = true;
            GestionErroresAsterix.errorSemantico("Error de tipo en la condicion del while. Debe ser tipo boolix.");
        }

        for (I ins : cuerpoWhile) {
            ins.type();
        }

        tipo = error ? new T(KindT.ERROR) : new T(KindT.BOOLIX);

        return tipo;
    }

    public void bind(SymbolMap ts) {
        cond.bind(ts);
        ts.openBlock();
        for (I ins : cuerpoWhile) {
            ins.bind(ts);
        }
        ts.closeBlock();
    }

	public void generateCodeI(PrintWriter pw) {
        // Abrimos un bloque nuevo para el bucle
        pw.println("(block");

        // Creamos ahora el loop
        pw.println("(loop");

        // TODO: Arreglar condición, creo que está al revés en EBin o algo
        // así porque siempre sale del bucle sin hacer nada. También Asig
        // creo que está mal, no actualiza el valor en memoria.
        // Generamos el código de la condicion y la cargamos desde memoria
        // a la pila
        cond.generateCodeE(pw);
        pw.println("i32.eqz");
        pw.println("br_if 1");

        // Recorremos ahora el cuerpo del bucle generando el código
        for (I ins : cuerpoWhile)
            ins.generateCodeI(pw);

        // Salto incondicional al inicio del bucle, etiqueta 0 representa este
        // punto.
        pw.println("br 0");
        // Cerramos el loop y el bloque
        pw.println(")");
        pw.println(")");
	}

	public T getType() {
        return tipo;
	}

    public void setDelta(AtomicInteger size, AtomicInteger localSize) {
        for (I ins : cuerpoWhile)
            ins.setDelta(size, localSize);
		
	}

}
