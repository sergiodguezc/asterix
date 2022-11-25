package utils;

import ast.I;

import java.io.PrintWriter;
import java.util.List;

public class CodeUtils {
    private boolean copyn;
    private boolean powi;
    private boolean powf;

    // Constructora
    public CodeUtils() {
        copyn = false;
        powi = false;
        powf = false;
    }

    // FUNCIONES GENERICAS
    public String generateCabecera() {
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
                        + "(start $init)\n"
                        + "(global $SP (mut i32) (i32.const 0)) ;; start of stack\n"
                        + "(global $MP (mut i32) (i32.const 0)) ;; mark pointer\n"
                        + "(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4\n" ;
    }

    public void generateMetaMain(PrintWriter pw, List<I> definiciones) {
        pw.println("(func $init");

        // Creamos el código de las definiciones globales
        Entero size = new Entero(8);
        Entero localSize = new Entero(0);
        for(I def : definiciones ) {
            def.setDelta(size,localSize);
        }

        // Escribimos el encabezado del subprograma
        pw.println("(local $temp i32)\n"
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
                + "\n\n ;; instrucciones ");

        // Generamos el codigo para las definiciones globales
        for(I def : definiciones) {
            def.generateCodeI(pw);
        }

        // Llamamos a la función panoramix
        pw.println("call $panoramix");
        pw.println("call $freeStack");
        pw.println(")");
    }

    public String generateAuxFunc() {
        String auxCode = auxFunc();
        if (copyn) {
            auxCode += "(func $copyn (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest\n"
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

    public String auxFunc() {
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

    // GETTERS Y SETTERS
    public boolean isPowi() {
        return powi;
    }

    public boolean isPowf() {
        return powf;
    }

    public boolean isCopyn() {
        return copyn;
    }

    public void showPowi() {
        this.powi = true;
    }

    public void showPowf() {
        this.powf = true;
    }

    public void showCopyn() {
        this.copyn = true;
    }

}
