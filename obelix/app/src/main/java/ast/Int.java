package ast;

public class Int extends E {
  private String v;
  public Int(String v) {
   this.v = v;   
  }
  public String num() {return v;}
  public KindE kind() {return KindE.INT;}   
  public String toString() {return v;}  
}
