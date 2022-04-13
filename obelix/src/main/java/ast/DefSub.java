package ast;

import asem.SymbolMap;
import org.json.simple.JSONObject;

public interface DefSub {
    // Interfaz que sirve para relacionar las IDec con los Subp
    public String toString(); 
    public NodeKind nodeKind();
    public JSONObject getJSON();
    public void bind(SymbolMap ts);
    public T type();
}

