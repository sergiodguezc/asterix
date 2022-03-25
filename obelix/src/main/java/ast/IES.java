package ast;

import java.util.List;

public class IES extends I {
    private E valor;
    private String label;

    public IES(E valor, String label) {
        this.valor = valor;
        this.label = label;
    }

	public KindI kind() {
		return KindI.ES;
	}

    public String toString() {
        return label+"("+valor.toString()+")";
    } 
}
