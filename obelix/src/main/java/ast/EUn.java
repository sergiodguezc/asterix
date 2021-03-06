package ast;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

import java.io.PrintWriter;

import org.json.simple.JSONObject;

public class EUn extends E {

    private E opnd;
    private String op;
    private T tipoEUn;

    public EUn(E opnd, String op) {
        this.opnd = opnd;
        this.op = op;
    }

    // AST
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

    // VINCULACION
    public void bind(SymbolMap ts) {
        opnd.bind(ts);
    }

    // TIPADO
    public T type() {
        T tipoOpnd = opnd.type();

        if ((op.equals("not") && tipoOpnd.getKindT() != KindT.BOOLIX) &&
                (op.equals("menos") && tipoOpnd.getKindT() != KindT.INTIX) &&
                (op.equals("menos") && tipoOpnd.getKindT() != KindT.FLOATIX)) {
            GestionErroresAsterix.errorSemantico("Expresion unaria mal tipada.");
            return (tipoEUn = new T(KindT.ERROR));
        } else
            return (tipoEUn = tipoOpnd);
    }

    // GENERACION DE CODIGO
    public void generateCodeE(PrintWriter pw) {
        if (op.equals("not")) {
            // Tenemos que asignarle a la variable el valor opuesto al que tiene
            // si es 1, le asignamos 0 y viceversa.
            opnd().generateCodeE(pw);
            pw.println("i32.const 1");
            pw.println("i32.xor");
        } else {
            pw.println("i32.const 0");
            opnd().generateCodeE(pw);
            pw.println("i32.sub");
        }
    }

    // GETTERS AND SETTERS
    public E opnd() {
        return opnd;
    }

    public T getType() {
        return tipoEUn;
    }
}
