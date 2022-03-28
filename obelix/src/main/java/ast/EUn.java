package ast;

import org.json.simple.JSONObject;

public class EUn extends E {

   private E opnd;
   private String op;

   public EUn(E opnd, String op) {
     this.opnd = opnd;
     this.op = op;
   }

   public String toString() {
     return getJSON().toJSONString();
   }
   public KindE kind() {return KindE.EUn;}

    @Override
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "EXPRESION UNARIA");
        obj.put("operacion", op);
        obj.put("operando", opnd.getJSON());
        return obj;
    }

    public E opnd() {return opnd;}
}
