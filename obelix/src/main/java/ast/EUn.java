package ast;

public class EUn extends E {

   private E opnd;
   private String op;

   public EUn(E opnd, String op) {
     this.opnd = opnd;
     this.op = op;
   }

   public String toString() {
     return op+"("+opnd().toString()+")";
   }
   public KindE kind() {return KindE.EUn;}
   public E opnd() {return opnd;}
}
