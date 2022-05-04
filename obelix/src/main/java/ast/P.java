package ast;

import asem.SymbolMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.util.List;

public class P implements ASTNode {
    private List<DefSub> defsubs;

    public static boolean copyni = false;
    public static boolean copynf = false;
    public static boolean powi = false;
    public static boolean powf = false;


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
        pw.println("(module ");
        // Cabecera del archivo wat, contiene las paginas de memoria del programa
        // y los punteros a la pila de memoria.
        pw.print(generateCabecera());
        
        // Generamos el código de las funciones y las definiciones
        for (DefSub df : defsubs) {
            df.generateCode(pw);
        }


        // Antes de cerrar el módulo, creamos las funciones auxiliares $reserveStack
        // $freeStack y $copyn.
        pw.print(generateAuxFunc());
        
        // Cerramos el módulo
        pw.print(")");
    }

    private String generateAuxFunc() {
        String auxCode = auxFunc();
        if (copyni) {
           auxCode += "(func $copyni (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest\n"
                     + "(param $src i32)\n"
                     + "(param $dest i32)\n"
                     + "(param $n i32)\n"
                     + "block\n"
                     + "loop\n"
                     + "get_local $n\n"
                     + "i32.eqz\n"
                     + "br_if 1\n"
                     + "get_local $n\n"
                     + "i32.const 1\n"
                     + "i32.sub\n"
                     + "set_local $n\n"
                     + "get_local $dest\n"
                     + "get_local $src\n"
                     + "i32.load\n"
                     + "i32.store\n"
                     + "get_local $dest\n"
                     + "i32.const 4\n"
                     + "i32.add\n"
                     + "set_local $dest\n"
                     + "get_local $src\n"
                     + "i32.const 4\n"
                     + "i32.add\n"
                     + "set_local $src\n"
                     + "br 0\n"
                     + "end\n"
                     + "end\n"
                     + ")\n";
        }
        if (copynf) {
            auxCode += "(func $copynf (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest\n"
                    + "(param $src i32)\n"
                    + "(param $dest i32)\n"
                    + "(param $n i32)\n"
                    + "block\n"
                    + "loop\n"
                    + "get_local $n\n"
                    + "i32.eqz\n"
                    + "br_if 1\n"
                    + "get_local $n\n"
                    + "i32.const 1\n"
                    + "i32.sub\n"
                    + "set_local $n\n"
                    + "get_local $dest\n"
                    + "get_local $src\n"
                    + "f32.load\n"
                    + "f32.store\n"
                    + "get_local $dest\n"
                    + "i32.const 4\n"
                    + "i32.add\n"
                    + "set_local $dest\n"
                    + "get_local $src\n"
                    + "i32.const 4\n"
                    + "i32.add\n"
                    + "set_local $src\n"
                    + "br 0\n"
                    + "end\n"
                    + "end\n"
                    + ")\n";
        }
        if (powi) {
            auxCode += "(func $powi (export \"powi\") (param $x i32) (param $y i32) (result i32) ;; pow of integer\n"
                      + "(local $out i32)\n"
                      + "(local $index i32)\n"
                      + "i32.const 1\n"
                      + "set_local $out\n"
                      + "i32.const 1\n"
                      + "set_local $index\n"
                      + "get_local $y\n"
                      + "(i32.const 0)\n"
                      + "i32.eq\n"
                      + "if $i0\n"
                      + "  i32.const 1\n"
                      + "return\n"
                      + "end\n"

                      + " (block $b0\n"
                      + "(loop $l0\n"
                      + "(i32.mul \n"
                      + "(get_local $out)\n"
                      + "(get_local $x)\n"
                      + ")\n"
                      + "set_local $out\n"

                      + "(i32.add \n"
                      + "(get_local $index)\n"
                      + "(i32.const 1)\n"
                      + ")\n"
                      + "tee_local $index\n"
                      + "get_local $y\n"
                      + "i32.gt_s\n"
                      + "br_if 1\n"

                      + "br 0\n"
                      + ")\n"
                      + ")\n"
                      + "  get_local $out\n"
                      + ")\n";
        }
        if (powf) {
            auxCode += "(func $powf (export \"powf\") (param $x f32) (param $y i32) (result f32) ;; pow of float\n"
                    + "(local $out f32)\n"
                    + "(local $index i32)\n"
                    + "f32.const 1.0\n"
                    + "set_local $out\n"
                    + "i32.const 1\n"
                    + "set_local $index\n"

                    + "get_local $y\n"
                    + "(i32.const 0)\n"
                    + "i32.eq\n"
                    + "if $i0\n"
                    + "  f32.const 1.0\n"
                    + "return\n"
                    + "end\n"

                    + " (block $b0\n"
                    + "(loop $l0\n"
                    + "(f32.mul \n"
                    + "(get_local $out)\n"
                    + "(get_local $x)\n"
                    + ")\n"
                    + "set_local $out\n"

                    + "(i32.add \n"
                    + "(get_local $index)\n"
                    + "(i32.const 1)\n"
                    + ")\n"
                    + "tee_local $index\n"
                    + "get_local $y\n"
                    + "i32.gt_s\n"
                    + "br_if 1\n"

                    + "br 0\n"
                    + ")\n"
                    + ")\n"
                    + "  get_local $out\n"
                    + ")\n";
        }
        return auxCode;
    }

    private String generateCabecera() {
		return 
              "(type $_sig_i32i32i32 (func (param i32 i32 i32) ))\n"
            + "(type $_sig_i32ri32 (func (param i32) (result i32)))\n"
            + "(type $_sig_i32 (func (param i32)))\n"
            + "(type $_sig_ri32 (func (result i32)))\n"
            + "(type $_sig_rf32 (func (result f32)))\n"
            + "(type $_sig_void (func ))\n"
            + "(import \"runtime\" \"exceptionHandler\" (func $exception (type $_sig_i32)))\n"
            + "(import \"runtime\" \"print\" (func $printi (type $_sig_i32)))\n"
            + "(import \"runtime\" \"print\" (func $printf (param f32)))\n"
            + "(import \"runtime\" \"read\" (func $readi (type $_sig_ri32)))\n"
            + "(import \"runtime\" \"read\" (func $readf (type $_sig_rf32)))\n"
            + "(memory 2000)\n"
            + "(start $panoramix)\n"
            + "(global $SP (mut i32) (i32.const 0)) ;; start of stack\n"
            + "(global $MP (mut i32) (i32.const 0)) ;; mark pointer\n"
            + "(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4\n" ;
	}

	private String auxFunc() {
        return 
            "(func $reserveStack (param $size i32)\n"
             + "(result i32)\n"
             + "get_global $MP\n"
             + "get_global $SP\n"
             + "set_global $MP\n"
             + "get_global $SP\n"
             + "get_local $size\n"
             + "i32.add\n"
             + "set_global $SP\n"
             + "get_global $SP\n"
             + "get_global $NP\n"
             + "i32.gt_u\n"
             + "if\n"
             + "i32.const 3\n"
             + "call $exception\n"
             + "end\n"
            + ")\n"
            + "(func $freeStack (type $_sig_void)\n"
             + "get_global $MP\n"
             + "i32.load\n"
             + "i32.load offset=4\n"
             + "set_global $SP\n"
             + "get_global $MP\n"
             + "i32.load\n"
             + "set_global $MP   \n"
            + ")\n";
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
