package ast;

import errors.GestionErroresAsterix;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import asem.ASemUtils;
import asem.SymbolMap;

import java.io.PrintWriter;
import java.util.List;

public class ELista extends E {
    private List<E> V;
    private T tipoELista;

    public ELista(List<E> V) {
        this.V = V;
    }

    // AST
    public KindE kind() {
        return KindE.LISTA;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "EXPRESION LISTA");
        if (V.isEmpty())
            return obj;
        JSONArray arr = new JSONArray();
        for (E v : V)
            arr.add(v.getJSON());
        obj.put("lista", arr);
        return obj;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    // VINCULACION
    public void bind(SymbolMap ts) {
        for (E elem : V) {
            elem.bind(ts);
        }
    }

    // TIPADO
    public T type() {
        boolean error = false;
        if (V.isEmpty())
            return (tipoELista = new T(KindT.VECTIX));
        T tipoVector = V.get(0).type();
        for (E elem : V) {
            if (!ASemUtils.checkEqualTypes(tipoVector, elem.type())) {
                GestionErroresAsterix.errorSemantico("Vector tiene elementos de distinto tipo");
                error = true;
            }
        }
        if (error)
            return (tipoELista = new T(KindT.ERROR));
        return (tipoELista = new T(tipoVector, V.size()));
    }

    // GETTERS AND SETTERS
    public List<E> getLista() {
        return V;
    }

    public T getType() {
        return tipoELista;
    }
}
