package ast;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONObject;

public class IDecVar extends IDec {
    private T type;
    private String id;
    private E valor;

    // Variable no inicializada
    public IDecVar(T type, String id) {
        super(id);
        this.type = type;
        setIni(false);
    }

    // Variable inicializada
    public IDecVar(T type, String id, E valor) {
        super(id);
        this.type = type;
        this.valor = valor;
        setIni(true);
    }

    public KindI kind() {
        return KindI.DEC;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION DECLARACION");
        obj.put("id", getId());
        obj.put("tipo", type.getJSON());
        if (!isIni())
            return obj;
        obj.put("valor", valor.getJSON());
        return obj;
    }

    @Override
    public KindD kindD() {
        return KindD.VAR;
    }

    public void bind(SymbolMap ts) {
        type.bind(ts);
        if(isIni())
            valor.bind(ts);
        ts.insertId(getId(), this);
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    public T type() {
        type.type();
        if(isIni()) {
            T tipoValor = valor.type();
            if (!ASemUtils.checkEqualTypes(type, tipoValor)) {
                GestionErroresAsterix.errorSemantico("Error de tipado en la declaracion.");
                return new T(KindT.ERROR);
            }
        }
        return type;
    }

    public T getType() {
        return type;
    }

    // Generamos el código para guardar el valor de las variables inicializadas
    // en memoria
	public void generateCodeI(PrintWriter pw) {
        if (isIni()) {
            // Generamos el código que ponga en la pila el delta más el
            // localStart. Como calculamos esto, no hace falta poner un offset.
            pw.println("get_local $localStart");
            pw.println("i32.const " + getDelta());
            pw.println("i32.add");

            // Generamos el código que calcula el valor
            valor.generateCodeE(pw);

            // Generamos el codigo del tipo primero para luego concatenarlo
            // con el .load, así podríamos escribir por ejemplo i32.load
            type.generateCode(pw);
            pw.println(".store");
        }
	}

	public void setDelta(AtomicInteger size, AtomicInteger localSize) {
        // Igualamos el delta a la posicion local sobre la que lo calculamos.
        setDelta(localSize.intValue());
        // Calculamos primero el tamaño del tipo y luego sumamos al size y localSize
        // el tamaño del tipo.
        type.setSizeT();
        localSize.set(localSize.intValue() + type.getSizeT());
        size.set(size.intValue() + type.getSizeT());

	}
}
