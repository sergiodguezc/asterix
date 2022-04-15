package ast;

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

        // Si son vectores, tienen que coincidir los tipos internos
        if (checkTIAsig(tipoId, tipoValor))
            return new T(KindT.INS);

        GestionErroresAsterix.errorSemantico("Error de tipado en la asignacion");
		return new T(KindT.ERROR);
	}

    public boolean checkTIAsig(T tipoId, T tipoValor) {
        return  // Si ambos son vectores, los tipos internos han de coincidir
                tipoId.getKindT() == KindT.VECTIX && tipoValor.getKindT() == KindT.VECTIX
                && tipoId.type().getKindT() == tipoValor.getKindT()
                // En caso contrario, unicamente se necesita que coincidan los tipos
                || tipoId.getKindT() == tipoValor.getKindT();
    }
}