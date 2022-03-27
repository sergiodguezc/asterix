package ast;

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

    
}
