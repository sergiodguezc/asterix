package ast;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;
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

}
