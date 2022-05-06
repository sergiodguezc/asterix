package ast;

import asem.SymbolMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Entero;

import java.io.PrintWriter;
import java.util.List;

public class IDefStruct extends I {
    private String id;
    private List<IDec> declarations;
    private boolean ini;
    private T tipoStruct;

    public IDefStruct(String id, List<IDec> declarations) {
        this.id = id;
        this.declarations = declarations;

        // Recorremos las declaraciones viendo si alguna está inicializada
        // En este caso, ponemos que el struct también los está.
        setIni(false);
        for (IDec idec : declarations)
            if (idec.isIni()) setIni(true);
    }

    // Devolvemos INS como tipo para afirmar que los tipos son correctos
    public T type() {
        for(IDec dec : declarations)
            dec.type();
        return new T(KindT.INS);
    }

    public List<IDec> getDeclarations() {
        return declarations;
    }

    public T getType() {return tipoStruct;}

    public void bind(SymbolMap ts) {
        ts.insertId(id, this);
        ts.openBlock();
        for (IDec dec : declarations)
            dec.bind(ts);
        ts.closeBlock();
    }

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

    @Override
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

    public void generateCodeI(PrintWriter pw) {
        // Esta instrucción no genera código, el código se genera cuando
        // declaremos una variable como struct. Pues esa es  la única
        // accesible.
    }

    public boolean isIni() {
        return ini;
    }

    public void setIni(boolean ini) {
        this.ini = ini;
    }

    public void generateCode(PrintWriter pw) {
        setDelta(new Entero(0),new Entero(0));
    }

}
