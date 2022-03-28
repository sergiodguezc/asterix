package ast;

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
        return "struct("+id+","+declarations.toString()+")";
    }


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
}
