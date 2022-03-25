package ast;

import java.util.List;

public class IIf extends I {
    private E cond; 
    private List<I> cuerpoIf;
    private List<I> cuerpoElse;
    private boolean ifelse;

    public IIf(E cond, List<I> cuerpoIf) {
        this.cond = cond;
        this.cuerpoIf = cuerpoIf;
        ifelse = false;
    }

    public IIf(E cond, List<I> cuerpoIf, List<I> cuerpoElse) {
        this.cond = cond;
        this.cuerpoIf = cuerpoIf;
        this.cuerpoElse = cuerpoElse;
        ifelse = true;
    }

	public KindI kind() {
		return KindI.IF;
	}

    public String toString() {
        if (ifelse) {
            return "ifelse("+cond.toString()+","+cuerpoIf.toString()+","+cuerpoElse.toString()+")";
        }
        return "if("+cond.toString()+","+cuerpoIf.toString()+")";
    }

    
}
