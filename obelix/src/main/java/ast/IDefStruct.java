package ast;

import asem.SymbolMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Entero;

import java.io.PrintWriter;
import java.util.List;

public class IDefStruct extends I {
    private String id; // identificador
    private List<IDec> declarations; // declaraciones internas del struct que definimos
    private boolean ini; // booleano que indica si está inicializado
    private T tipoStruct;

    // CONSTRUCTOR
    public IDefStruct(String id, List<IDec> declarations) {
        this.id = id;
        this.declarations = declarations;

        // Recorremos las declaraciones viendo si alguna está inicializada
        // En este caso, ponemos que el struct también los está.
        setIni(false);
        for (IDec idec : declarations)
            if (idec.isIni()) setIni(true);
    }

    // AST
    public KindI kind() {
        return KindI.POTDEF;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION STRUCT");
        obj.put("id", id);
        if(declarations.isEmpty())
            return obj;
        JSONArray arr = new JSONArray();
        for(IDec dec : declarations)
            arr.add(dec.getJSON());
        obj.put("declarations", arr);
        return obj;
    }

    // VINCULACIÓN
    public void bind(SymbolMap ts) {
        ts.insertId(id, this);
        ts.openBlock();
        for (IDec dec : declarations)
            dec.bind(ts);
        ts.closeBlock();
    }

    // TIPADO
    // Devolvemos INS como tipo para afirmar que los tipos son correctos
    public T type() {
        for(IDec dec : declarations)
            dec.type();
        return new T(KindT.INS);
    }

    // GENERACIÓN DE CÓDIGO
    // No obstante en esta instrucción sí que necesitamos
    // calcular los deltas internos de las declaraciones
    // del struct.
    public void setDelta(Entero size, Entero localSize) {
        Entero newLocalSize = new Entero(0);
        Entero newSize = new Entero(0);

        for (IDec iDec : declarations) {
            iDec.setDelta(newSize,newLocalSize);
        }

        if (localSize.get() + newSize.get() > size.get()) {
            size.set(localSize.get() + newSize.get());
        }
    }
    // En generate code, simplemente llamamos a la función que asigna los delta,
    // no genera código, pero lo necesitamos cuando llamamos de forma recursiva
    // a generate code de las instrucciones.
    public void generateCode(PrintWriter pw) {
        setDelta(new Entero(0),new Entero(0));
    }

    // GETTERS Y SETTERS
    // getter y setter que indican si el struct tiene variables inicializadas por defecto
    public boolean isIni() {
        return ini;
    }
    public void setIni(boolean ini) {
        this.ini = ini;
    }

    // getter que devuelve las declaraciones internas del struct
    public List<IDec> getDeclarations() {
        return declarations;
    }

    // getter que devuelve el tipo del struct
    public T getType() {return tipoStruct;}
}
