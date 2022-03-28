package ast;

import org.json.simple.JSONObject;

public class IAsig extends I {

    private E id;
    private E valor;

    public IAsig(E id, E valor) {
        this.id = id;
        this.valor = valor;
    }

    public KindI kind() {return KindI.ASIG;}

    @Override
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
}
