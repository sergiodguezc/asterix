package ast;

import asem.SymbolMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class IDecStruct extends IDec {
    private List<IDec> declarations;
    private String id;

    public IDecStruct(String id, List<IDec> declarations) {
        this.declarations = declarations;
        this.id = id;
    }

    public String toString(){
        return getJSON().toJSONString();
    }

    public void bind(SymbolMap ts) {
        ts.insertId(id, this);
        ts.openBlock();
        for (IDec dec : declarations)
            dec.bind(ts);
        ts.closeBlock();
    }

    public String getId() {
        return id;
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

	public List<IDec> getDeclarations() {
        return declarations;
    }

    // Devolvemos INS como tipo para afirmar que los tipos son correctos
	public T type() {
        for(IDec dec : declarations)
            dec.type();
		return new T(KindT.INS);
	}
}
