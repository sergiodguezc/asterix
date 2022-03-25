package ast;

import java.util.List;

public class IWhile extends I {
    private E cond; 
    private List<I> cuerpoWhile;

    public IWhile(E cond, List<I> cuerpoWhile) {
        this.cond = cond;
        this.cuerpoWhile = cuerpoWhile;
    }

	public KindI kind() {
		return KindI.WHILE;
	}

    public String toString() {
        return "while("+cond.toString()+","+cuerpoWhile.toString()+")";
    }

    
}
