package ast;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class IFor extends I {
    private T tipo;
    private String id;
    private E lista;
    private List<I> cuerpoFor;

    public IFor(T tipo, String id, E lista, List<I> cuerpoFor) {
        this.tipo = tipo;
        this.id = id;
        this.lista = lista;
        this.cuerpoFor = cuerpoFor;
    }

    public KindI kind() {
        return KindI.FOR;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "INSTRUCCION FOR");
        obj.put("tipo", tipo.getJSON());
        obj.put("id", id);
        if (cuerpoFor.isEmpty())
            return obj;
        JSONArray arr = new JSONArray();
        for (I i : cuerpoFor)
            arr.add(i.getJSON());
        obj.put("cuerpo", arr);
        return obj;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    public T type() {
        T tipo = this.tipo.type();
        T tipoLista = lista.type(); // Tipo interno de la lista

        // Comprobamos que la declaracion del for esta correctamente tipada
        boolean error = false;
        if (!ASemUtils.checkEqualTypes(tipo, tipoLista)) {
            GestionErroresAsterix.errorSemantico("Error de tipado en la declaracion del for");
            error = true;
        }

        // Llamamos recursivamente a type() en las instrucciones del cuerpo del for
        for (I ins : cuerpoFor)
            ins.type();

        if (error)
            return new T(KindT.ERROR);
        return new T(KindT.INS);
    }

    public void bind(SymbolMap ts) {
        // Abro el ambito del for
        ts.openBlock();

        // Llamadas recursivas a bind()
        tipo.bind(ts);

        // Añadimos una referencia a la declaracion del for en la tabla de simbolos
        IDec dec = new IDecVar(tipo, id);
        ts.insertId(id, dec);

        // Llamadas recursivas a bind()
        lista.bind(ts);
        for (I ins : cuerpoFor)
            ins.bind(ts);

        // Cierro el ambito del for
        ts.closeBlock();
    }

}
