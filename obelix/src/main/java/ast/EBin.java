package ast;

import org.json.simple.JSONObject;

public class EBin extends E {
    private E opnd1;
    private E opnd2;
    private String op;

    public EBin(E opnd1, E opnd2, String op) {
        this.opnd1 = opnd1;
        this.opnd2 = opnd2;
        this.op = op;
    }

    public T type() {
        T tipoOp1 = opnd1.type();
        T tipoOp2 = opnd2.type();
        if (op.equals("or") || op.equals("and") || op.equals("igual")) {
            // if (tipoOp1.check("boolix") && tipoOp2.check("boolix")) {
            // return tipoOp1;
            // }
            // else if (op.equals("igual") && tipoOp1.check("intix") &&
            // tipoOp2.check("intix")) {
            // return tipoOp1;
            // }
            // else {
            // GestionErroresAsterix.numErroresSemanticos++;
            // }
        } else {
            // if(tipoOp1.check("intix") && tipoOp2.check("intix")) {
            // return tipoOp1;
            // }
        }

        return tipoOp1;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "EXPRESION BINARIA");
        obj.put("operacion", op);
        obj.put("operando 1", opnd1.getJSON());
        obj.put("operando 2", opnd2.getJSON());
        return obj;
    }

    public KindE kind() {
        return KindE.EBin;
    }

    public E opnd1() {
        return opnd1;
    }

    public E opnd2() {
        return opnd2;
    }
}
