package ast;

import org.json.simple.JSONObject;

public interface DefSub {
    // Interfaz que sirve para relacionar las IDec con los Subp
    public String toString(); 
    public NodeKind nodeKind();
    public JSONObject getJSON();
}

