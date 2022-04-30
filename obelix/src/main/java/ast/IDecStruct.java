package ast;

import asem.SymbolMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: Arreglar esta mierda
public class IDecStruct extends IDec {
    private List<IDec> declarations;
    private T tipoStruct;

    public IDecStruct(String id, List<IDec> declarations) {
        super(id);
        this.declarations = declarations;
        // Recorremos las declaraciones viendo si alguna está inicializada
        // En este caso, ponemos que el struct también los está.
        for (IDec idec : declarations)
            if (idec.isIni()) setIni(true);
    }

    public String toString(){
        return getJSON().toJSONString();
    }

    public void bind(SymbolMap ts) {
        ts.insertId(getId(), this);
        ts.openBlock();
        for (IDec dec : declarations)
            dec.bind(ts);
        ts.closeBlock();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION STRUCT");
        obj.put("id", getId());
        if(declarations.isEmpty())
            return obj;
        JSONArray arr = new JSONArray();
        for(IDec dec : declarations)
            arr.add(dec.getJSON());
        obj.put("declarations", arr);
        return obj;
    }

    public KindD kindD() {
        return KindD.POT;
    }

    public List<IDec> getDeclarations() {
        return declarations;
    }

    // Devolvemos INS como tipo para afirmar que los tipos son correctos
	public T type() {
        for(IDec dec : declarations)
            dec.type();
		return new T(KindT.INS);
	}

    public T getType() {
        return new T(declarations);
    }

    // Generamos el código para guardar los valores por defecto inicializados
    // en el struct.
	public void generateCodeI(PrintWriter pw) {
        // Generamos codigo sii alguna variable dentro del struct esta inicializada
        if(isIni()) {
            // Colocamos el valor de localStart en la pila para recuperarlo despues
            pw.println("get_local $localStart ;; save localStart, at the end we revert this change");

            // Cambiamos el localStart a localStart + delta
            pw.println("i32.const " + getDelta());
            pw.println("get_local $localStart");
            pw.println("i32.add");
            pw.println("set_local $localStart");

            // Generamos codigo para las declaraciones internas del struct si estan inicializadas
            for (IDec iDec : declarations) {
                iDec.generateCodeI(pw);
            }

            // Devolvemos el localStart al inicial. Este valor se encontraba ya en la pila.
            pw.println("set_local $localStart");
        }
	}

	public void setDelta(AtomicInteger size, AtomicInteger localSize) {
        // Ponemos el localsize a 0 porque nos encontramos con un struct.
        AtomicInteger newLocalSize = new AtomicInteger(0);
        for (IDec idec : declarations) {
            idec.setDelta(size, newLocalSize);
        }
	}

}
