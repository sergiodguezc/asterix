package ast;

import asem.SymbolMap;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONObject;

public class IAlias extends I implements DefSub {
    private String id;
    private T tipo;

    public IAlias(String id, T tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public KindI kind() {
        return KindI.ALIAS;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION ALIAS");
        obj.put("alias", id);
        obj.put("tipo", tipo.getJSON());
        return obj;
    }

    public T getType() {
        return tipo;
    }

    // En este caso, devolvemos el tipo al que ha hecho alias.
    public T type() {
        // Recursion al tipo al que hace referencia
        tipo.type();
        return tipo;
    }

    public void bind(SymbolMap ts) {
        ts.insertId(id, this);
    }

    public String toString() {
        return getJSON().toJSONString();
    }

	public void generateCodeI(PrintWriter pw) {
	}

	public void setDelta(AtomicInteger size, AtomicInteger localSize) {
        // Los alias no declaran variables. No hacemos nada.		
		
	}

    // Esta función es necesaria para crear los códigos de las instrucciones
    // globales. 
	public void generateCode(PrintWriter pw) {
		
	}

}
