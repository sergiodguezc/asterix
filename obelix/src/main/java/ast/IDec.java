package ast;

import org.json.simple.JSONObject;

public abstract class IDec extends I implements DefSub {
    public KindI kind() {
        return KindI.DEC;
    }

    public abstract JSONObject getJSON();
}
