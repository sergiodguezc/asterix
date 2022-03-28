package ast;

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

    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION E/S");
        obj.put("E/S", label);
        obj.put("valor", valor.getJSON());
        return obj;
    }

    public String toString() {
        return label+"("+valor.toString()+")";
    } 
}
