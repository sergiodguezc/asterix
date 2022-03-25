package ast;

import java.util.List;

public class IDecStruct extends IDec {
    private List<IDec> declarations;
    private E id;

    public IDecStruct(E id, List<IDec> declarations, IDec nuevo) {
        this.declarations = declarations;
        this.id = id;
        declarations.add(nuevo);
    }

    public String toString(){
        return "struct("+id.toString()+","+declarations.toString()+")";
    }

    
}
