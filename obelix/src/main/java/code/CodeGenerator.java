package code;

import ast.P;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CodeGenerator {
    private File output;
    private P programa;
    private PrintWriter pw;

    public CodeGenerator(P programa, String filename) {
        this.programa = programa;
        output = new File(filename + ".wat");
    }

    public void generateCode() {
        programa.generateCode(pw);
    }
}
