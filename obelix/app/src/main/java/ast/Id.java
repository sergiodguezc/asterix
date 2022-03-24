package ast;

public class Id extends E {
  private String v;
  public Id(String v) {
   this.v = v;   
  }
  public String num() {return v;}
  public KindE kind() {return KindE.ID;}   
  public String toString() {return v;}  
}
