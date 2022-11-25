package ast;

import java.io.PrintWriter;
import java.util.List;

import asem.SymbolMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Entero;

public class IIf extends I {
    private E cond; // expresión que indica la condición
    private List<I> cuerpoIf; // instrucciones internas del if
    private List<I> cuerpoElse; // instrucciones internas del else
    private boolean ifelse; // booleano que indica si hay else o es solo if

    // CONSTRUCTOR
    public IIf(E cond, List<I> cuerpoIf) {
        this.cond = cond;
        this.cuerpoIf = cuerpoIf;
        ifelse = false;
    }

    // CONSTRUCTOR
    public IIf(E cond, List<I> cuerpoIf, List<I> cuerpoElse) {
        this.cond = cond;
        this.cuerpoIf = cuerpoIf;
        this.cuerpoElse = cuerpoElse;
        ifelse = true;
    }

    // AST
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

    // VINCULACIÓN
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

    // TIPADO
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


    // GENERACIÓN DE CÓDIGO
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

    public void setDelta(Entero size, Entero localSize) {
        Entero newLocalSizeIf = new Entero(localSize.get());
        Entero newSizeIf = new Entero(0);
        Entero newLocalSizeElse = new Entero(localSize.get());
        Entero newSizeElse = new Entero(0);

        for (I ins : cuerpoIf) {
            ins.setDelta(newSizeIf, newLocalSizeIf);
        }
        if (ifelse)
            for (I ins : cuerpoElse)
                ins.setDelta(newSizeElse, newLocalSizeElse);

        if (localSize.get() + newSizeIf.get() > size.get()) {
            size.set(localSize.get() + newSizeIf.get());
        }

        if (localSize.get() + newSizeElse.get() > size.get()) {
            size.set(localSize.get() + newSizeElse.get());
        }
	}
}
