package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

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
}
