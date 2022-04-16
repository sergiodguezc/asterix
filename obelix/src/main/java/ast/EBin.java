package ast;

import java.util.List;

import org.json.simple.JSONObject;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

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

        if ((op.equals("or") || op.equals("and")) && checkTEBool(tipoOp1, tipoOp2))
            return new T(KindT.BOOLIX);
        else if ((op.equals("igual") || op.equals("mayor") || op.equals("menor") || op.equals("geq")
                || op.equals("leq") || op.equals("dis")) && checkTEComp(tipoOp1, tipoOp2))
            return new T(KindT.BOOLIX);
        else if (checkTEInt(tipoOp1, tipoOp2))
            return new T(KindT.INTIX);
        else if (checkTEFloat(tipoOp1, tipoOp2))
            return new T(KindT.FLOATIX);
        else if (op.equals("accA") && checkTAccA(tipoOp1, tipoOp2))
            return tipoOp1.type();
        else if (op.equals("accS") && checkTAccS(tipoOp1)) {
            // Obtenemos la lista de declaraciones del struct
            List<IDec> declarations = tipoOp1.getDec().getDeclarations();

            // Reccorremos la lista de declaraciones hasta que veamos la que coincide con el opnd2
            for (IDec dec : declarations)
                if(opnd2.getVal().equals(dec.getId()))
                    return dec.type();

            // En caso contrario no devuelve nada y sale de esta condicion
            // Devolviendo KindT.ERROR.
        }
        GestionErroresAsterix.errorSemantico("Error de tipado en la expresion binaria");
        return new T(KindT.ERROR);
    }

    private boolean checkTAccS(T tipoOp1) {
        return tipoOp1.getKindT() == KindT.POT;
    }

    private boolean checkTAccA(T tipoOp1, T tipoOp2) {
        return tipoOp1.getKindT() == KindT.VECTIX && tipoOp2.getKindT() == KindT.INTIX;
    }

    // TODO: Comparaciones y casteos varios
    private boolean checkTEFloat(T tipoOp1, T tipoOp2) {
        return (tipoOp1.getKindT() == KindT.INTIX || tipoOp1.getKindT() == KindT.FLOATIX)
                && (tipoOp2.getKindT() == KindT.INTIX || tipoOp2.getKindT() == KindT.FLOATIX);
    }

    private boolean checkTEInt(T tipoOp1, T tipoOp2) {
        return tipoOp1.getKindT() == KindT.INTIX && tipoOp2.getKindT() == KindT.INTIX;
    }

    // TODO: Comparaciones y casteos varios
    private boolean checkTEComp(T opnd1, T opnd2) {
        return (opnd1.getKindT() == KindT.INTIX || opnd1.getKindT() == KindT.FLOATIX)
                && (opnd2.getKindT() == KindT.INTIX || opnd2.getKindT() == KindT.FLOATIX)
                || (opnd1.getKindT() == KindT.BOOLIX && opnd2.getKindT() == KindT.BOOLIX);
    }

    private boolean checkTEBool(T opnd1, T opnd2) {
        return opnd1.getKindT() == KindT.BOOLIX && opnd2.getKindT() == KindT.BOOLIX;
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

    public void bind(SymbolMap ts) {
        opnd1.bind(ts);
        opnd2.bind(ts);

        // Si es un struct guardamos las declaraciones
        // IDecStruct struct = (IDecStruct) ts.searchId(opnd1.getVal());
    }
}
