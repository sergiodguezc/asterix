package ast;

import asem.SymbolMap;
import errors.GestionErroresAsterix;
import org.json.simple.JSONObject;

import java.util.List;

public class T implements ASTNode {
    private KindT kindT;
    private String id; // Identificador que hace referencia a un alias o a un struct
    private int N; // Longitud del vector
    private T tipo; // Tipo del vector o del alias (significado doble)
    private ASTNode astDef; // En el caso de que sea un alias, referencia al momento de su definicion
    private List<IDec> decs; // En el caso de que sea un struct, es la lista de declaraciones.

    public T(KindT kindT) {
        this.kindT = kindT;
    }

    public T(String id) {
        this.id = id;
    }

    public T(T tipo, int N) {
        this.kindT = KindT.VECTIX;
        this.tipo = tipo;
        this.N = N;
    }

    // Constructor para el struct
    public T(List<IDec> decs) {
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
        }
        else if (kindT == KindT.VECTIX) {
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

    // Si es un ALIAS : Devuelve el tipo al que hace referencia
    // Si es un VECTIX : Devuelve el tipo de los elementos del vector
    // En cc : Devolvemos el tipo
    public T type() {
        // Es un alias, devolvemos el tipo al que hace referencia
        if (id != null) {
            // Comprobamos que hace referencia a una instruccion
            if(astDef == null || astDef.nodeKind() != NodeKind.INSTRUCCION) {
                GestionErroresAsterix.errorSemantico("ERROR: Tipo no reconocido");
                kindT = KindT.ERROR;
            }
            else {
                I def = (I) astDef;
                // Despues comprobamos que la instruccion es realmente un alias o un struct
                if(def.kind() != KindI.ALIAS && def.kind() != KindI.DEC ) {
                    GestionErroresAsterix.errorSemantico("ERROR: Tipo no reconocido");
                    kindT = KindT.ERROR;
                }
                else if (def.kind() == KindI.ALIAS) {
                    copy(astDef.type());
                }
                else {
                    // Obtenemos la lista de declaraciones del struct
                    List<IDec> decs = ((IDecStruct) astDef).getDeclarations();
                    // Devolvemos el tipo struct con esta lista de declaraciones
                    copy(new T(decs));
                }
            }
        }
        // Es un vector, devolvemos su tipo interno
        else if (kindT == KindT.VECTIX)
            return tipo;
        // Devolvemos el tipo
        return this;
    }

    private void copy(T type) {
        kindT = type.kindT;
        id = type.id;
        if(kindT == KindT.VECTIX) {
            N = type.N;
            tipo = type.tipo;
        }
        else if(kindT == KindT.POT) {
            decs = type.decs;
        }
    }
}
