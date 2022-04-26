package ast;

import org.json.simple.JSONObject;

public abstract class IDec extends I implements DefSub {
    public KindI kind() {
        return KindI.DEC;
    }

    public abstract String getId();
    public abstract JSONObject getJSON();
    public abstract KindD kindD();
}
