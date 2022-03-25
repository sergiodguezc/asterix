package ast;

public class IDec extends I implements DefSub {
    public KindI kind() {
        return KindI.DEC;
    }
}
