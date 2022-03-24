package ast;

public class EUn extends E {

   private E opnd;
   private String label;

   public EUn(E opnd, String op, TablaEtiquetas t) {
     this.opnd = opnd;
     this.label = t.getLabelU(op);
   }

   public String toString() {
     return label+"("+opnd().toString()+")";
   }
   public KindE kind() {return KindE.EUn;}
   public E opnd() {return opnd;}
}
