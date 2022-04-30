package ast;

import org.json.simple.JSONObject;

public abstract class IDec extends I implements DefSub {
    private String id; // Identificador de la variable
    private int delta; // Posición relativa de la variable dentro del bloque

    // CONSTRUCTOR DE LA DECLARACION
    public IDec(String id) { this.id = id; }

    public KindI kind() { return KindI.DEC; }
    public String getId() { return id; } // Geter para el identificador
    public void setId(String id) { this.id = id; } // Geter para el identificador
    public int getDelta() {return delta; } // Getter para el delta
    public void setDelta(int delta) {this.delta = delta; } // Getter para el delta


    public abstract JSONObject getJSON();
    public abstract KindD kindD();

}
