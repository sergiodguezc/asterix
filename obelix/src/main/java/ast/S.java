package ast;

import java.util.ArrayList;
import java.util.List;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class S implements ASTNode, DefSub {
    private List<I> cuerpo;
    private List<Arg> args;
    private boolean isFunction;
    private boolean isMain;
    private String id;
    private T tRet;
    private E vRet;

    public S(List<I> cuerpo, List<Arg> args, String id, T tRet, E vRet) {
        this.cuerpo = cuerpo;
        this.args = args;
        this.id = id;
        this.tRet = tRet;
        this.vRet = vRet;
        isFunction = true;
        isMain = false;
    }

    public S(List<I> cuerpo, List<Arg> args, String id) {
        this.cuerpo = cuerpo;
        this.id = id;
        this.args = args;
        isMain = false;
        isFunction = false;
    }

    public S(List<I> cuerpo, E vRet) {
        this.cuerpo = cuerpo;
        this.vRet = vRet;
        this.id = "panoramix";
        this.args = new ArrayList<Arg>();
        isMain = true;
    }

    public void bind(SymbolMap ts) {
        // Insertamos identificador al ambito general del programa
        ts.insertId(id, this);
        // Creamos el ambito del subprograma
        ts.openBlock();
        for (Arg arg : args) {
            arg.bind(ts);
        }
        for (I ins : cuerpo) {
            ins.bind(ts);
        }

        ts.closeBlock();
    }

    public NodeKind nodeKind() {
        return NodeKind.SUBPROGRAMA;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "SUBPROGRAMA");
        if (isMain) {
            obj.put("id", "panoramix");
        } else {
            obj.put("id", id);
            if (isFunction) {
                obj.put("tRet", tRet.getJSON());
                obj.put("vRet", vRet.getJSON());
            }
            if (!args.isEmpty()) {
                JSONArray argsjson = new JSONArray();
                for (Arg a : args) {
                    argsjson.add(a.getJSON());
                }
                obj.put("args", argsjson);
            }

        }
        if (!cuerpo.isEmpty()) {
            JSONArray cuerpojson = new JSONArray();
            for (I i : cuerpo) {
                cuerpojson.add(i.getJSON());
            }
            obj.put("cuerpo", cuerpojson);
        }

        return obj;
    }

    public T type() {
        for (Arg a : args)
            a.type();
        for (I ins : cuerpo)
            ins.type();

        T tipoVRet = vRet.type();
        boolean error = false;

        if (!ASemUtils.checkEqualTypes(tipoVRet, tRet)){
            error = true;
            GestionErroresAsterix.errorSemantico("El tipo del valor de retorno no coincide con el tipo de la función.");
        }


        return error ? new T(KindT.INS) : new T(KindT.ERROR);
    }

    public List<Arg> getArguments() {
        return args;
    }

}
