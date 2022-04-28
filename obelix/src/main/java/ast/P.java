package ast;

import asem.SymbolMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class P implements ASTNode {
    private List<DefSub> defsubs;

    public P(List<DefSub> defsubs, DefSub nuevo) {
        this.defsubs = defsubs;
        defsubs.add(nuevo);
    }

    public void bind(SymbolMap ts) {
        // Creamos el bloque principal con los identificadores
        // de las definiciones
        ts.openBlock();

        // Recursion a las definiciones
        for (DefSub ds : defsubs) {
            ds.bind(ts);
        }

        ts.closeBlock();
    }

    public void generateCode(PrintWriter pw) {
        // TODO: Escribir encabezado
        for (DefSub df : defsubs)
            df.generateCode(pw);

    }

    public int maxMemory(Integer c) {
        int maxMemSub = 0;
        for (DefSub df: defsubs)
            maxMemSub = Integer.max(maxMemSub, df.maxMemory(c));
        return maxMemSub;
    }

    public NodeKind nodeKind() {
        return NodeKind.PROGRAMA;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "PROGRAMA");
        if (!defsubs.isEmpty()) {
            JSONArray defsubsjson = new JSONArray();
            for (DefSub df : defsubs) {
                defsubsjson.add(df.getJSON());
            }
            obj.put("DefSub", defsubs);
        }
        return obj;
    }

    public T type() {
        for (DefSub df : defsubs)
            df.type();
        return new T(KindT.INS);
    }
}
