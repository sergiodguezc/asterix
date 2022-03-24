package ast;

public class Bool extends E {
  private String v;
  public Bool(String v) {
   this.v = v;   
  }
  public String num() {return v;}
  public KindE kind() {return KindE.BOOL;}   
  public String toString() {return v;}  
}
