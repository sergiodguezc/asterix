package ast;

public class ECte extends E {
    private String v;
    public ECte(String v) {
        this.v = v;
    }
    public String num() {return v;}
    public KindE kind() {return KindE.CTE;}
    public String toString() {return v;}
}
