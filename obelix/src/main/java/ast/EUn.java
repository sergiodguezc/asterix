package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public class EUn extends E {

    private E opnd;
    private String op;

    public EUn(E opnd, String op) {
        this.opnd = opnd;
        this.op = op;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    public KindE kind() {
        return KindE.EUn;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "EXPRESION UNARIA");
        obj.put("operacion", op);
        obj.put("operando", opnd.getJSON());
        return obj;
    }

    public E opnd() {
        return opnd;
    }

    // TODO: Comprobar que sean tipos admitidos
    public T type() {
        return opnd.type();
    }

    public void bind(SymbolMap ts) {
        opnd.bind(ts);
    }
}
