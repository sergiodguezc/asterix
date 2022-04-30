package ast;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import asem.SymbolMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class IIf extends I {
    private E cond;
    private List<I> cuerpoIf;
    private List<I> cuerpoElse;
    private boolean ifelse;

    public IIf(E cond, List<I> cuerpoIf) {
        this.cond = cond;
        this.cuerpoIf = cuerpoIf;
        ifelse = false;
    }

    public IIf(E cond, List<I> cuerpoIf, List<I> cuerpoElse) {
        this.cond = cond;
        this.cuerpoIf = cuerpoIf;
        this.cuerpoElse = cuerpoElse;
        ifelse = true;
    }

    public KindI kind() {
        return KindI.IF;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION IF");
        obj.put("cond", cond.getJSON());
        if (!cuerpoIf.isEmpty()) {
            JSONArray cuerpojson = new JSONArray();
            for (I i : cuerpoIf) {
                cuerpojson.add(i.getJSON());
            }
            obj.put("cuerpoIf", cuerpojson);
        }
        if (ifelse) {
            if (!cuerpoElse.isEmpty()) {
                JSONArray cuerpojson = new JSONArray();
                for (I i : cuerpoIf) {
                    cuerpojson.add(i.getJSON());
                }
                obj.put("cuerpoElse", cuerpojson);
            }
        }
        return obj;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    public T type() {
        T resultado = new T(KindT.INS);

        // Solo hay que comprobar que la condicion del if es un booleano
        if(cond.type().getKindT() != KindT.BOOLIX)
            resultado = new T(KindT.ERROR);

        // Llamadas recursivas a type() para seguir comprobando si hay errores semanticos
        for(I ins : cuerpoIf)
            ins.type();

        if(ifelse)
            for (I ins : cuerpoElse)
                ins.type();

        return resultado;
    }

    public void bind(SymbolMap ts) {
        cond.bind(ts);
        // Ambito del cuerpo del if
        ts.openBlock();
        for(I ins : cuerpoIf)
            ins.bind(ts);
        ts.closeBlock();
        // Ambito del cuerpo del else
        if(ifelse) {
            ts.openBlock();
            for(I ins : cuerpoElse)
                ins.bind(ts);
            ts.closeBlock();
        }
    }

	@Override
	public void generateCodeI(PrintWriter pw) {
        // Primero creamos el código de la condición
        cond.generateCodeE(pw);
        pw.println("if");
        for (I ins : cuerpoIf)
            ins.generateCodeI(pw);
        if (ifelse) {
            pw.println("else");
            for (I ins : cuerpoElse)
                ins.generateCodeI(pw);
        }
        pw.println("end");
	}

    public void setDelta(AtomicInteger size, AtomicInteger localSize) {
        for (I ins : cuerpoIf)
            ins.setDelta(size, localSize);
        if (ifelse)
            for (I ins : cuerpoElse)
                ins.setDelta(size, localSize);
		
	}
}
