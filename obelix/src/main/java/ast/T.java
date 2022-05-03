package ast;

import asem.SymbolMap;
import com.rits.cloning.Cloner;
import errors.GestionErroresAsterix;
import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class T implements ASTNode {
    private KindT kindT;

    public String getId() {
        return id;
    }

    private String id; // Identificador que hace referencia a un alias o a un struct
    private int N; // Longitud del vector
    private T tipo; // Tipo del vector o del alias (significado doble)
    private ASTNode astDef; // En el caso de que sea un alias, referencia al momento de su definicion
    private List<IDec> decs; // En el caso de que sea un struct, es la lista de declaraciones.
    private int sizeT; // Size del tipo en bytes

    //  CONSTRUCTOR PARA TIPOS BÁSICOS, ERRORES E INS
    public T(KindT kindT) {
        this.kindT = kindT;
    }

    //  CONSTRUCTOR PARA EL ALIAS O PARA UN STRUCT DECLARADO PREVIAMENTE
    public T(String id) {
        this.id = id;
    }

    //  CONSTRUCTOR PARA EL ARRAY
    public T(T tipo, int N) {
        this.kindT = KindT.VECTIX;
        this.tipo = tipo;
        this.N = N;
    }

    // CONSTRUCTOR PARA EL STRUCT
    public T(List<IDec> decs) {
        this.kindT = KindT.POT;
        this.decs = decs;
    }
    public T(List<IDec> decs, String id) {
        this.id = id;
        this.kindT = KindT.POT;
        this.decs = decs;
    }

    public int getVSize() {
        return N;
    }

    public KindT getKindT() {
        return kindT;
    }

    public void bind(SymbolMap ts) {
        if (id != null) { // Caso que sea un alias
            astDef = ts.searchId(id);
        } else if (kindT == KindT.VECTIX) {
            tipo.bind(ts);
        }
    }

    public List<IDec> getDec() {
        return decs;
    }

    public NodeKind nodeKind() {
        return NodeKind.TIPO;
    }

    public String toString() {
        return getJSON().toJSONString();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("node", "TIPO");
        if (kindT == KindT.VECTIX) {
            obj.put("kindT", "vectix");
            obj.put("tipo", tipo.getJSON());
            obj.put("longitud", N);
        } else {
            obj.put("kindT", kindT);
        }
        return obj;
    }

    public T getType() {
        return this;
    }

    public T getTInterno() {
        return tipo;
    }
    public void setTInterno(T tipo) {
        this.tipo = tipo;
    }

    // Si es un ALIAS : Devuelve el tipo al que hace referencia
    // Si es un VECTIX : Devuelve el tipo de los elementos del vector
    // En cc : Devolvemos el tipo
    public T type() {
        // Es un alias, devolvemos el tipo al que hace referencia
        if (id != null) {
            // Comprobamos que hace referencia a una instruccion
            if (astDef == null || astDef.nodeKind() != NodeKind.INSTRUCCION) {
                GestionErroresAsterix.errorSemantico("ERROR: Tipo no reconocido");
                kindT = KindT.ERROR;
            } else {
                I def = (I) astDef;
                // Despues comprobamos que la instruccion es realmente un alias o un struct
                if (def.kind() != KindI.ALIAS && def.kind() != KindI.POTDEF) {
                    GestionErroresAsterix.errorSemantico("ERROR: Tipo no reconocido");
                    kindT = KindT.ERROR;
                } else if (def.kind() == KindI.ALIAS) {
                    copy(astDef.type());
                } else {
                    // Obtenemos la lista de declaraciones del struct
                    List<IDec> decs = ((IDefStruct) astDef).getDeclarations();
                    // Devolvemos el tipo struct con esta lista de declaraciones
                    copy(new T(decs, id));
                }
            }
        }
        // Es un vector, devolvemos su tipo interno
        else if (kindT == KindT.VECTIX) {
            tipo.type();
            return tipo;
        }

        // Devolvemos el tipo
        return this;
    }

    // En este método no copiamos el tamaño porque aún no estamos en condiciones
    // de haberlo calculado.
    private void copy(T type) {
        kindT = type.kindT;
        id = type.id;
        if (kindT == KindT.VECTIX) {
            N = type.N;
            tipo = type.tipo;
        } else if (kindT == KindT.POT) {
            decs = type.decs;
        }
    }

    // Creamos las traducciones, estas traducciones se escribiran por ejemplo
    // en los param de las funciones o cuando apliquemos operaciones sobre estos
    // tipos: sum, sub, mul, etc.
    public void generateCode(PrintWriter pw) {
        pw.print((kindT == KindT.FLOATIX) ? "f32" : "i32");
    }

    // Método que calcula el tamaño de los tipos en bytes de forma recursiva.
    public void setSizeT() {
        sizeT = 0;
        if (kindT == KindT.VECTIX) {
            tipo.setSizeT();
            sizeT = tipo.getSizeT()*N;
        }
        else if (kindT == KindT.POT) {
            for (IDec d : decs) {
                d.getType().setSizeT();
                sizeT += d.getType().getSizeT();
            }
        }
        else sizeT = 4;
    }

    // Método público para devolver el tamaño del tipo en bytes.
    public int getSizeT() {
        return sizeT;
    }

    public KindT getKindTBasico() {
        return (tipo == null) ? kindT : tipo.getKindTBasico();
    }
}
