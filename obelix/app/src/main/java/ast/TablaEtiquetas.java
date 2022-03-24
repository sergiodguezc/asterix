package ast;

import java.util.HashMap;

public class TablaEtiquetas {

    // Tabla de Etiquetas de los binarios
    private HashMap<String,String> tBin;
    // Tabla de Etiquetas de los unarios
    private HashMap<String,String> tUn;

    public TablaEtiquetas(){
        tBin = new HashMap<String,String>();
        tUn = new HashMap<String,String>();
        tBin.put("+", "sum");
        tBin.put("-", "res");
        tBin.put("*", "mul");
        tBin.put("/", "div");
        tBin.put("^", "pow");
        tBin.put("%", "mod");
        tBin.put("||", "or");
        tBin.put("&&", "and");
        tBin.put("<", "menor");
        tBin.put(">", "mayor");
        tBin.put("<=", "menoreq");
        tBin.put(">=", "mayoreq");
        tBin.put("==", "igual");
        tUn.put("-", "menos");
        tUn.put("!", "not");
    }

    public String getLabelB(String key) {
        return tBin.get(key);
    }

    public String getLabelU(String key) {
        return tUn.get(key);
    }
    
}
