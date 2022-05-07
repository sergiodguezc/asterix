package ast;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.util.List;

public class ECte extends E {
    private KindT kindT;
    private String v;
    private ASTNode dec;
    private T tipoECte;

    public ECte(String v) {
        this.v = v;
    }

    public ECte(String v, KindT kindT) {
        this.v = v;
        this.kindT = kindT;
    }

    // AST
    public KindE kind() {
        return KindE.CTE;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "EXPRESION CONSTANTE");
        obj.put("valor", v);
        return obj;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    // VINCULACION

    // Si es una variable dec hace referencia a la declaracion de esa variable
    // En caso contrario, dec es null.
    public void bind(SymbolMap ts) {
        dec = ts.searchId(v);
    }

    // TIPADO
    public T type() {
        if (dec != null) {
            kindT = dec.getType().getKindT();
            return (tipoECte = dec.getType());
        } else if (kindT == KindT.INTIX || kindT == KindT.FLOATIX || kindT == KindT.BOOLIX)
            return (tipoECte = new T(kindT));

        GestionErroresAsterix.errorSemantico("Variable '" + v + "' sin declarar");
        return (tipoECte = new T(KindT.ERROR));
    }

    // GENERACION DE CODIGO
    public void generateCodeE(PrintWriter pw) {
        // Este caso corresponde con constantes: 1, 1.0, galo, romano, etc
        if (dec == null) {
            tipoECte.generateCode(pw);
            // Convertimos galo y romano a 1 y 0 en wasm, respectivamente.
            if (kindT == KindT.BOOLIX)
                v = v.equals("galo") ? "1" : "0";
            pw.println(".const " + v);
            // Caso correspondiente a accesos a variables ya declaradas
        } else if (tipoECte.getKindT() != KindT.VECTIX && tipoECte.getKindT() != KindT.POT) {
            // Buscamos en memoria la dirección de la variable
            pw.println(";; Obtener valor de variable ya declarada");
            if (dec.nodeKind() == NodeKind.ARG) {
                Arg arg = (Arg) dec;
                // Ponemos en la pila la dirección de memoria del valor del arg
                pw.println("get_local $localStart");
                pw.println("i32.const " + arg.getDelta() + " ;; delta: variable declarada");
                pw.println("i32.add");

                if (arg.isRef()) {
                    // Cargamos en la pila la dirección de memoria
                    pw.println("i32.load");
                }

                pw.println("i32.load");
            }
            else {
                I insVar = (I) dec; // Casteo seguro porque dec != null
                if (insVar.kind() == KindI.DEC) {
                    IDec decVar = (IDec) dec;
                    pw.println(decVar.isGlobal() ? "i32.const 8 ;; pos inicio variables globales" : "get_local $localStart");
                    pw.println("i32.const " + decVar.getDelta() + " ;; delta: variable declarada");
                    pw.println("i32.add");

                    decVar.getType().generateCode(pw);
                    pw.println(".load");
                } else if (insVar.kind() == KindI.FOR) {
                    IFor ifor = (IFor) insVar;
                    // Obtenemos la dirección de memoria de la variable delclarada
                    // en el for.
                    pw.println(";; Codigo de la lista que estamos recorriendo");
                    ifor.getLista().generateCodeD(pw);
                    pw.println(";; Fin del codigo de la lista que estamos recorriendo");

                    // Obtenemos la iteracion en la que nos encontramos
                    pw.println("get_local $localStart");
                    pw.println("i32.const " + ifor.getItDelta() + " ;; pos del iterador");
                    pw.println("i32.add");
                    pw.println("i32.load");

                    // Calculamos el tamaño del tipo interno del
                    ifor.getType().setSizeT();
                    pw.println("i32.const " + ifor.getType().getSizeT() + " ;; tamaño del tipo interno que recorremos ");

                    // Accedemos a la posicion del vector
                    pw.println("i32.mul");
                    pw.println("i32.add");

                    ifor.getType().generateCode(pw);
                    pw.println(".load");
                }
            }
        }
    }

    public void generateCodeD(PrintWriter pw) {
        if (dec != null) {
            if(dec.nodeKind() == NodeKind.ARG) {
                Arg arg = (Arg) dec;
                // Ponemos en la pila la dirección de memoria del valor del arg
                pw.println("get_local $localStart");
                pw.println("i32.const " + arg.getDelta() + " ;; delta: variable declarada");
                pw.println("i32.add");

                if (arg.isRef()) {
                    // Cargamos en la pila la dirección de memoria
                    pw.println("i32.load");
                }
            }
            else {
                pw.println(";; codigo del designador");
                I insVar = (I) dec; // Casteo seguro porque dec != null
                if (insVar.kind() == KindI.DEC) {
                    IDec decVar = (IDec) insVar;
                    pw.println(decVar.isGlobal() ? "i32.const 8 ;; pos inicio variables globales" : "get_local $localStart");
                    pw.println("i32.const " + decVar.getDelta() + " ;; delta: declaración");
                    pw.println("i32.add");
                } else if (insVar.kind() == KindI.FOR) {
                    IFor ifor = (IFor) insVar;

                    // Generamos el codigo del designador de la lista
                    ifor.getLista().generateCodeD(pw);

                    // Obtenemos la iteracion en la que nos encontramos
                    pw.println("get_local $localStart");
                    pw.println("i32.const " + ifor.getItDelta() + " ;; pos del iterador");
                    pw.println("i32.add");
                    pw.println("i32.load");

                    // Calculamos el tamaño del tipo interno del vector
                    ifor.getType().setSizeT();
                    pw.println("i32.const " + ifor.getType().getSizeT() + " ;; tamaño del tipo interno que recorremos ");

                    // Obtenemos la posicion del vector
                    pw.println("i32.mul");
                    pw.println("i32.add");
                }
            }
        }
    }

    public void generateCodeAccS(PrintWriter pw, E opnd2, boolean designador) {
        List<IDec> declaraciones = null;

        if (dec.nodeKind() == NodeKind.ARG) {
            Arg arg = (Arg) dec;
            declaraciones = arg.getType().getDec();

        } else {
            I ins = (I) dec;

            if (ins.kind() == KindI.FOR) {
                IFor ifor = (IFor) ins;
                declaraciones = ifor.getLista().getType().getTInterno().getDec();
            } else if (ins.kind() == KindI.DEC ) {
                IDec idec = (IDec) ins;
                declaraciones = idec.getDeclarations();
            }
        }

        // Generamos el código del designador (Puede ser dec o ifor)
        generateCodeD(pw);

        // Recorremos las declaraciones en busca del delta del operando 2.
            for (IDec idec : declaraciones) {
                if (idec.getId().equals(opnd2.getVal())) {
                    pw.println("i32.const " + idec.getDelta() + " ;; delta interno struct");
                    pw.println("i32.add");
                    // si no es un designador escribimos su tipo, así luego solo tendríamos que yuxtaponer el .load/.store
                    if (!designador) {
                        idec.getType().generateCode(pw);
                        pw.println(".load ;; load accS");
                    }
                }
            }
    }

    // GETTERS AND SETTERS
    @Override
    public T getType() {
        return tipoECte;
    }

    public String getVal() {
        return v;
    }


}
