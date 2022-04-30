package ast;

import asem.SymbolMap;
import errors.GestionErroresAsterix;

import org.json.simple.JSONObject;

import java.io.PrintWriter;

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

    public String getVal() {
        return v;
    }

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

    @Override
    public T getType() {
        return tipoECte;
    }

    public T type() {
        if (dec != null) {
            return (tipoECte = dec.getType());
        }
        else if (kindT == KindT.INTIX || kindT == KindT.FLOATIX || kindT == KindT.BOOLIX)
            return (tipoECte = new T(kindT));

        GestionErroresAsterix.errorSemantico("Variable '" + v + "' sin declarar");
        return (tipoECte = new T(KindT.ERROR));
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    // Si es una variable dec hace referencia a la declaracion de esa variable
    // En caso contrario, dec es null.
    public void bind(SymbolMap ts) {
        dec = ts.searchId(v);
    }

	public void generateCode(PrintWriter pw) {
        // Este caso corresponde con constantes: 1, 1.0, galo, romano, etc
		if (dec == null) {
            tipoECte.generateCode(pw);
            // Convertimos galo y romano a 1 y 0 en wasm, respectivamente.
            if (kindT == KindT.BOOLIX)
                v = v.equals("galo") ? "1" : "0";
            pw.println(".const " + v);
        // Caso correspondiente a accesos a variables ya declaradas
        } else {
            // Buscamos en memoria la dirección de la variable
            pw.println("get_local $localStart");
            IDec decVar = (IDec) dec; // Casteo seguro porque dec != null
            pw.println("i32.const " + decVar.getDelta());
            pw.println("i32.add");
            decVar.getType().generateCode(pw);
            pw.println(".load");
        }
	}

	public void generateSinLoad(PrintWriter pw) {
	    if (dec != null) {
            pw.println("get_local $localStart");
            IDec decVar = (IDec) dec; // Casteo seguro porque dec != null
            pw.println("i32.const " + decVar.getDelta());
            pw.println("i32.add");
        }
	}
}
