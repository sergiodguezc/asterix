package ast;

public class Float extends E {
  private String v;
  public Float(String v) {
   this.v = v;   
  }
  public String num() {return v;}
  public KindE kind() {return KindE.FLOAT;}   
  public String toString() {return v;}  
}
