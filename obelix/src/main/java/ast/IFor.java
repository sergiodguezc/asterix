package ast;

import java.util.List;

public class IFor extends I {
    private T tipo; 
    private String id;
    private List<I> cuerpoFor;

    public IFor(T tipo, String id, List<I> cuerpoFor) {
        this.tipo = tipo;
        this.id = id;
        this.cuerpoFor = cuerpoFor;
    }

	public KindI kind() {
		return KindI.FOR;
	}

    public String toString() {
        return "for("+tipo.toString()+","+id+","+cuerpoFor.toString()+")";
    }

    
}
