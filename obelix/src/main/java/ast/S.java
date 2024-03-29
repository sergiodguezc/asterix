package ast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Entero;

public class S implements ASTNode {
    private List<I> cuerpo;
    private List<Arg> args;
    private boolean isFunction;
    private boolean isMain;
    private String id;
    private T tRet;
    private E vRet;

    // Funciones
    public S(List<I> cuerpo, List<Arg> args, String id, T tRet, E vRet) {
        this.cuerpo = cuerpo;
        this.args = args;
        this.id = id;
        this.tRet = tRet;
        this.vRet = vRet;
        isFunction = true;
        isMain = false;
    }

    // Procedimientos
    public S(List<I> cuerpo, List<Arg> args, String id) {
        this.cuerpo = cuerpo;
        this.id = id;
        this.args = args;
        isMain = false;
        isFunction = false;
    }

    // Main
    public S(List<I> cuerpo, E vRet) {
        this.cuerpo = cuerpo;
        this.vRet = vRet;
        this.tRet = new T(KindT.INTIX);
        this.id = "panoramix";
        this.args = new ArrayList<Arg>();
        isMain = true;
        isFunction = true;
    }

    // AST
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

    // VINCULACION
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
        if(isFunction) {
            vRet.bind(ts);
            tRet.bind(ts);
        }

        ts.closeBlock();
    }

    // TIPADO
    public T type() {
        for (Arg a : args)
            a.type();
        for (I ins : cuerpo)
            ins.type();

        boolean error = false;
        if(isFunction) {
            T tipoVRet = vRet.type();
            tRet.type();
            if (!ASemUtils.checkEqualTypes(tipoVRet, tRet)) {
                error = true;
                GestionErroresAsterix.errorSemantico("El tipo del valor de retorno no coincide con el tipo de la función.");
            }

        }
        return !error ? new T(KindT.INS) : new T(KindT.ERROR);
    }

    // GENERACION DE CODIGO
    public void generateCode(PrintWriter pw) {
        // Cabecera con el nombre de la función
        pw.print("(func $" + id + " ");

        // Generamos ahora el código del resultado si hubiera
        if (isFunction && !isMain) {
            pw.print("(result ");
            tRet.generateCode(pw);
            pw.println(")");
        } else pw.println(); // Salto de línea por si no es una función

        // Calculamos los delta para cada cada variable. Comenzamos en 8
        // porque es el valor de esta variable en el encabezado despues de 
        // realizar sus operaciones. Aquí uso AtomicInteger en vez de
        // Integer porque al final Integer no es un puntero a un int,
        // en cambio, AtomicInteger sí.
        Entero size = new Entero(8);
        Entero localSize = new Entero(0);
        for (Arg arg: args) {
            arg.setDelta(size, localSize);
        }

        for (I ins : cuerpo) {
            ins.setDelta(size, localSize);
        }

        // Escribimos el encabezado que necesitan todas las funciones
        pw.println(generateEncabezado(size));

        // Inicializamos las variables locales que lo necesiten
        for (I ins : cuerpo)
            ins.generateCodeI(pw);

        // En caso de que sea una función escribimos el valor del retorno al
        // final
        if (isFunction && !isMain) {
            pw.println(";; valor de retorno");
            if (tRet.getKindT() == KindT.VECTIX || tRet.getKindT() == KindT.POT) {
                // Obtenemos la direccion de origen
                vRet.generateCodeD(pw);
            } else {
                vRet.generateCodeE(pw);
            }
        }

        // Antes de cerrar la función tenemos que llamar a la función
        // $freeStack
        pw.println("call $freeStack");

        // Cerramos la función
        pw.println(")");
            
    }

    // Método privado que devuelve el encabezado de cada función en wat. Todas
    // necesitan las dos variables locales localStart y temp, del mismo modo
    // tienen que hacer las operaciones con los punteros globales.
    private String generateEncabezado(Entero size) {
        return "(local $temp i32)\n"
                + "(local $localStart i32)\n"
                + "i32.const " + size + " ;; let this be the stack size needed (params+locals+2)*4\n"
                + "call $reserveStack  ;; returns old MP (dynamic link)\n"
                + "set_local $temp\n"
                + "get_global $MP\n"
                + "get_local $temp\n"
                + "i32.store\n"
                + "get_global $MP\n"
                + "get_global $SP\n"
                + "i32.store offset=4\n"
                + "get_global $MP\n"
                + "i32.const 8\n"
                + "i32.add\n"
                + "set_local $localStart\n"
                + "\n\n ;; instrucciones ";
    }

    // GETTERS Y SETTERS

    public List<Arg> getArguments() {
        return args;
    }

    public T getType() {
        return tRet;
    }

    public boolean isFunction() {
        return isFunction || isMain;
    }

}
