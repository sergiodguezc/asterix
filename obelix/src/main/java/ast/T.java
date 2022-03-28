package ast;

import org.json.simple.JSONObject;

public class T implements ASTNode {
    private String kindT;
    private String N; // Longitud del vector
    private T tipo;
    private Boolean isVector;

    public T(String kindT){
        this.kindT = kindT;
        isVector = false;
    }
    public T(T tipo, String N){
        isVector = true;
        this.tipo = tipo;
        this.N = N;
    }
    public NodeKind nodeKind() {return NodeKind.TIPO;}
    public String toString() {
        if (isVector)
            return "vectix(" + tipo.toString() + "," + N + ")";
        return kindT;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "TIPO");
        obj.put("kindT", kindT);
        if(isVector)
            obj.put("longitud", N);
        return obj;
    }
}
