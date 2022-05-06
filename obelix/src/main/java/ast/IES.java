package ast;

import asem.SymbolMap;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONObject;
import utils.Entero;

public class IES extends I {
    private E valor;
    private String label;

    public IES(E valor, String label) {
        this.valor = valor;
        this.label = label;
    }

	public KindI kind() {
		return KindI.ES;
	}

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION ES");
        obj.put("ES", label);
        obj.put("valor", valor.getJSON());
        return obj;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

	public T type() {
        T tipoValor = valor.type();

        // Solamente permitimos escribir o leer enteros, reales o booleanos
        if (tipoValor.getKindT() != KindT.BOOLIX
                && tipoValor.getKindT() != KindT.INTIX
                && tipoValor.getKindT() != KindT.FLOATIX)
            return new T(KindT.ERROR);

        // En caso contrario, esta bien tipado
		return new T(KindT.INS);
	}

    public void bind(SymbolMap ts) {
        valor.bind(ts);
    }

	@Override
	public void generateCodeI(PrintWriter pw) {
        // Mostrar valor por consola
        if (label.equals("stilus")) {
            valor.generateCodeE(pw); // Calculamos el valor a escribir y lo ponemos en la pila
            if (valor.getType().getKindT() == KindT.INTIX)
                pw.println("call $printi");
            else if (valor.getType().getKindT() == KindT.FLOATIX)
                pw.println("call $printf");
            else if (valor.getType().getKindT() == KindT.BOOLIX)
                pw.println("call $printi");
        // Leer valor de la consola
        } else {
            // Calculamos la posición de memoria de la variable donde queremos escribir la lectura
            valor.generateCodeD(pw);

            if (valor.getType().getKindT() == KindT.INTIX)
                pw.println("call $readi");
            else if (valor.getType().getKindT() == KindT.FLOATIX)
                pw.println("call $readf");
            else if (valor.getType().getKindT() == KindT.BOOLIX)
                pw.println("call $readi");

            valor.getType().generateCode(pw); // Guardamos en memoria el valor leido
            pw.println(".store");
        }
	}

	public void setDelta(Entero size, Entero localSize) {
        // Las funciones de entrada salida no declaran variables
		
	}
}
