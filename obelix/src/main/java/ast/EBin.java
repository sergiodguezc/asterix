package ast;

import java.io.PrintWriter;
import java.util.List;

import org.json.simple.JSONObject;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

public class EBin extends E {
    private E opnd1;
    private E opnd2;
    private String op;
    // Tipo de la expresion
    private T tipoExp;

    public EBin(E opnd1, E opnd2, String op) {
        this.opnd1 = opnd1;
        this.opnd2 = opnd2;
        this.op = op;
    }

    public T type() {
        T tipoOp1 = opnd1.type();
        T tipoOp2 = null;
        if (!op.equals("accS")) {
             tipoOp2 = opnd2.type();
        }

        if ((op.equals("or") || op.equals("and")) && checkTEBool(tipoOp1, tipoOp2))
            return (tipoExp = new T(KindT.BOOLIX));
        else if ((op.equals("igual") || op.equals("mayor") || op.equals("menor") || op.equals("geq")
                || op.equals("leq") || op.equals("dis")) && checkTEComp(tipoOp1, tipoOp2))
            return (tipoExp = new T(KindT.BOOLIX));
        else if ((checkTEInt(tipoOp1, tipoOp2) && op.equals("mod")) || checkTEInt(tipoOp1, tipoOp2))
            return (tipoExp = new T(KindT.INTIX));
        else if (checkTEFloat(tipoOp1, tipoOp2))
            return (tipoExp = new T(KindT.FLOATIX));
        else if (op.equals("accA") && checkTAccA(tipoOp1, tipoOp2))
            return (tipoExp = tipoOp1.type());
        else if (op.equals("accS") && checkTAccS(tipoOp1)) {
            // Obtenemos la lista de declaraciones del struct
            List<IDec> declarations = tipoOp1.getDec();

            // Reccorremos la lista de declaraciones hasta que veamos la que coincide con el opnd2
            for (IDec dec : declarations)
                if(opnd2.getVal().equals(dec.getId()))
                    return (tipoExp = dec.getType());

            // En caso contrario no devuelve nada y sale de esta condicion
            // Devolviendo KindT.ERROR.
        }
        GestionErroresAsterix.errorSemantico("Error de tipado en la expresion binaria");
        return (tipoExp = new T(KindT.ERROR));
    }

    private boolean checkTAccS(T tipoOp1) {
        return tipoOp1.getKindT() == KindT.POT;
    }

    private boolean checkTAccA(T tipoOp1, T tipoOp2) {
        return tipoOp1.getKindT() == KindT.VECTIX && tipoOp2.getKindT() == KindT.INTIX;
    }

    // TODO: Comparaciones y casteos varios
    private boolean checkTEFloat(T tipoOp1, T tipoOp2) {
        if (op.equals("pow")) {
            return (tipoOp1.getKindT() == KindT.INTIX || tipoOp1.getKindT() == KindT.FLOATIX)
                    && (tipoOp2.getKindT() == KindT.INTIX);
        }
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

    public T getType() {
        return tipoExp;
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
    }

	public void generateCodeE(PrintWriter pw) {
        if(op.equals("accA")) {
            // Calculo el designador del vector dado por opnd1
            opnd1.generateCodeD(pw);

            // Accedo a la posicion dada por el opnd2
            opnd2.generateCodeE(pw);
            pw.println("i32.add");

            // Pongo el valor en la pila
            pw.println("i32.load");
        }
        else if (op.equals("accS")) {

        }
        else {
            // Como se apilan, primero se calcula el código del operando 1.
            opnd1.generateCodeE(pw);

            // Realizamos primero un posible casteo de este operando para que funcionen
            // las operaciones entre i32 y f32. Siempre el casteo es de i32 -> f32
            if (!op.equals("pow") && opnd2.getType().getKindT() == KindT.FLOATIX &&
                    opnd1.getType().getKindT() == KindT.INTIX) {
                pw.println("f32.convert_s/i32");
            }

            // Segundo, se calcula el código del operando 2.
            opnd2.generateCodeE(pw);

            // Realizamos otro posible casteo de este operando para que funcionen
            // las operaciones entre i32 y f32. Siempre el casteo es de i32 -> f32
            if (!op.equals("pow") && opnd2.getType().getKindT() == KindT.INTIX &&
                    opnd1.getType().getKindT() == KindT.FLOATIX) {
                pw.println("f32.convert_s/i32");
            }

            // Finalmente calculamos el código de la operacion.
            opToWat(pw);
        }
	}

    public void generateCodeD(PrintWriter pw) {
        // TODO: Solamente es necesario para arrays, structs y variables
        // Acceso a array
        if(op.equals("accA")) {
            // Generar codigo D para el opnd1
            pw.println(";; codigo designador accA");
            pw.println(";; codigo designador operando 1");
            opnd1.generateCodeD(pw);

            // Calculamos el code E de opnd2
            pw.println(";; codigo E operando 2");
            opnd2.generateCodeE(pw);

            // Calcular el tamaño del array
            int dim = opnd1.getType().getVSize();
            pw.println("i32.const " + dim + ";; tamaño vector");

            // Accedemos a la posicion de inicio del array
            pw.println("i32.mul");

            // Accedemos a la posicion dada por opnd2
            pw.println("i32.add");
        }

        // Acceso a struct
        else if (op.equals("accS")) {
            // Calculamos el code D del opnd1
            opnd1.generateCodeD(pw);

            // Calculamos la posicion relativa del opnd2
            // pw.println("i32.const " + opnd2.getDelta());

            // Los sumamos para obtener el designador del struct
            pw.println("i32.add");
        }
    }

    // Función auxiliar para escribir la instrucción correspondiente dentro del
    // archivo, recibe el pw sobre el que se escribe y realiza la escritura.
    private void opToWat(PrintWriter pw) {
        // En el caso en que no sea ni accA, pow o accS, escribimos primero el
        // tipo de la instrucción que vamos a escribir para que por ejemplo se
        // escriba primero i32 y luego .add para la suma
        if (!(op.equals("pow") || op.equals("accS") || op.equals("accA")))
            tipoExp.generateCode(pw);

        // Ahora procedemos con el resto de las instrucciones. Comenzamos con
        // aquellas para las cuales no hay que hacer comprobaciones extra.
        if (op.equals("mas")) pw.println(".add");
        else if (op.equals("menos")) pw.println(".sub");
        else if (op.equals("mul")) pw.println(".mul");
        else if (op.equals("mod")) pw.println(".rem_s");
        else if (op.equals("or")) pw.println(".or");
        else if (op.equals("and")) pw.println(".and");
        else if (op.equals("igual")) pw.println(".eq");
        else if (op.equals("dis")) pw.println(".ne");
        
        // Seguimos ahora con aquellas para las cuales hay que hacer
        // comprobaciones de tipos o que no vienen de forma nativa en wasm.
        // Comenzamos con los casos para los que hay que comprobar tipos.
        else if (op.equals("div")) {
            if (opnd1.getType().getKindT() == KindT.INTIX)
                pw.println(".div_s");
            else pw.println(".div");
        }
        else if (op.equals("leq")) {
            if (opnd1.getType().getKindT() == KindT.INTIX)
                pw.println(".le_s");
            else pw.println(".le");
        }
        else if (op.equals("geq")) {
            if (opnd1.getType().getKindT() == KindT.INTIX)
                pw.println(".ge_s");
            else pw.println(".ge");
        }
        else if (op.equals("mayor")) {
            if (opnd1.getType().getKindT() == KindT.INTIX)
                pw.println(".gt_s");
            else pw.println(".gt");
        }
        else if (op.equals("menor")) {
            if (opnd1.getType().getKindT() == KindT.INTIX)
                pw.println(".lt_s");
            else pw.println(".lt");
        }

        // Caso con llamada a función creada por nosotros y que incluimos en
        // todos los ejecutables.
        else if (op.equals("pow")) {
            if (tipoExp.getKindT() == KindT.FLOATIX) {
                P.powf = true;
                pw.println("call $powf");
            } else {
                P.powi = true;
                pw.println("call $powi");
            }
        }
    }
}
