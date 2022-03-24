package ast;

public abstract class EBin extends E {
   private E opnd1;
   private E opnd2;
   private String label;

   public EBin(E opnd1, E opnd2, String op, TablaEtiquetas t) {
     this.opnd1 = opnd1;
     this.opnd2 = opnd2;
     this.label = t.getLabelB(op);
   }

   public String toString() {
     return label+"("+opnd1().toString()+","+opnd2().toString()+")";
   }
   public KindE kind() {return KindE.EBin;}
   public E opnd1() {return opnd1;}
   public E opnd2() {return opnd2;}    
}
