package asem;

import ast.ASTNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolMap {
    private List<Map<String, ASTNode>> ts;

    public SymbolMap () {
        ts = new ArrayList<>();
    }

    public void openBlock() {
        ts.add(new HashMap<>());
    }

    public void closeBlock() {
        ts.remove(ts.size() - 1);
    }

    public boolean insertId(String id, ASTNode puntero) {
        boolean insertable = !ts.get(ts.size()-1).containsKey(id);
        if(insertable) {
            ts.get(ts.size()-1).put(id, puntero);
        }
        else {
            System.err.println("Warning: La variable "+ id + " ya estaba declarada, se ha tomado su primera declaración\n");
        }
        return insertable;
    }

    public ASTNode searchId (String id) {
        for(int i = ts.size()-1; i >= 0; --i) {
            if(ts.get(i).containsKey(id))
                return ts.get(i).get(id);
        }
        return null;
    }
}