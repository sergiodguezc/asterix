package ast;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

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

    public T type() {
        if ((op.equals("not") && opnd().type().getKindT() != KindT.BOOLIX) ||
                (op.equals("menos") && opnd().type().getKindT() != KindT.INTIX) ||
                (op.equals("menos") && opnd().type().getKindT() != KindT.FLOATIX)) {
            GestionErroresAsterix.errorSemantico("Expresion unaria mal tipada.");
            return new T(KindT.ERROR);
        } else
            return opnd().type();
    }

    public void bind(SymbolMap ts) {
        opnd.bind(ts);
    }
}
