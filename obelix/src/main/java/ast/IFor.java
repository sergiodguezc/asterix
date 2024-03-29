package ast;

import asem.ASemUtils;
import asem.SymbolMap;
import errors.GestionErroresAsterix;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Entero;

import java.io.PrintWriter;
import java.util.List;

public class IFor extends I {
    private T tipo;
    private String id;
    private E lista;
    private int itDelta;
    private List<I> cuerpoFor;

    public IFor(T tipo, String id, E lista, List<I> cuerpoFor) {
        this.tipo = tipo;
        this.id = id;
        this.lista = lista;
        this.cuerpoFor = cuerpoFor;
    }

    // AST
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

    // VINCULACION
    public void bind(SymbolMap ts) {
        // Abro el ambito del for
        ts.openBlock();

        // Llamadas recursivas a bind()
        tipo.bind(ts);

        ts.insertId(id, this);

        // Llamadas recursivas a bind()
        lista.bind(ts);
        for (I ins : cuerpoFor)
            ins.bind(ts);

        // Cierro el ambito del for
        ts.closeBlock();
    }

    // TIPADO
    // Devolvemos el tipo de la declaración de la variable auxiliar. Lo necesitamos
    // cuando hacemos bind() en otros puntos del AST.
    public T type() {
        tipo.type();
        T tipo = this.tipo;
        T tipoLista = lista.type().type(); // Tipo interno de la lista

        // Comprobamos que la declaracion del for esta correctamente tipada
        boolean error = false;
        if (!ASemUtils.checkEqualTypes(tipo, tipoLista) || (tipo.getKindT() == KindT.POT && !tipo.getId().equals(tipoLista.getId()))) {
            GestionErroresAsterix.errorSemantico("Error de tipado en la declaracion del for");
            error = true;
        }

        // Llamamos recursivamente a type() en las instrucciones del cuerpo del for
        for (I ins : cuerpoFor)
            ins.type();

        if (error)
            return new T(KindT.ERROR);
        return tipo;
    }

    // GENERACION DE CODIGO
	public void generateCodeI(PrintWriter pw) {
        // Antes de abrir el bloque para el bucle tenemos que poner a 0 el
        // iterador del for.
        pw.println("\n;; Bucle for");

        pw.println("get_local $localStart");
        pw.println("i32.const " + itDelta);
        pw.println("i32.add");
        pw.println("i32.const 0");
        pw.println("i32.store");
        
        // Abrimos un bloque nuevo para el bucle
        pw.println("(block");

        // Creamos ahora el loop
        pw.println("(loop");

        // Generamos el código de la condicion y la cargamos desde memoria
        // a la pila
        
        // Codigo para la condicion del bucle, i.e haber dado tantas
        // vueltas como el tamaño de la lista. Para ello primero tenemos que
        // crear una variable local en memoria que usemos como contador.

        pw.println("get_local $localStart");
        pw.println("i32.const " + itDelta);
        pw.println("i32.add");
        pw.println("i32.load");
        pw.println("i32.const " + lista.getType().getVSize());
        pw.println("i32.lt_s");

        pw.println("i32.eqz");
        pw.println("br_if 1");

        // Ahora tenemos que calcular el varDelta, tenemos que conseguir
        // el delta del primer elemento de la lista de declaraciones

        // Recorremos ahora el cuerpo del bucle generando el código
        pw.println(";; Cuerpo del for");
        for (I ins : cuerpoFor)
            ins.generateCodeI(pw);
        pw.println(";; Fin del cuerpo del for");
        // Antes de cerrar el for tenemos que incrementar en 1 el valor del
        // iterador.
        // Calcular posición de i
        pw.println("get_local $localStart");
        pw.println("i32.const " + itDelta);
        pw.println("i32.add");
        // Calcular i + 1
        pw.println("get_local $localStart");
        pw.println("i32.const " + itDelta);
        pw.println("i32.add");
        pw.println("i32.load"); // lectura del valor del iterador
        pw.println("i32.const 1"); 
        pw.println("i32.add"); // sumamos uno con el valor del iterador

        // Escribir i + 1 en i
        pw.println("i32.store"); // guardamos el valor en la posicion primera.
                                 
        // Salto incondicional al inicio del bucle, etiqueta 0 representa este
        // punto.
        pw.println("br 0");
        // Cerramos el loop y el bloque
        pw.println(")");
        pw.println(")");
	}

    public void setDelta(Entero size, Entero localSize) {
        // Reservamos 4 bytes para el entero que usaremos en la condición del
        // bucle como iterador.
        // Es un bloque

        Entero newLocalSize = new Entero(localSize.get() + 4);
        Entero newSize = new Entero(0);

        itDelta = localSize.get();

        for (I ins : cuerpoFor)
            ins.setDelta(newSize, newLocalSize);

        if (localSize.get() + newSize.get() > size.get()) {
            size.set(localSize.get() + newSize.get());
        }
	}

    // GETTERS Y SETTERS

    // Getters y setters para los delta
	public int getItDelta() {
		return itDelta;
	}

    public E getLista() {
        return lista;
    }

    public T getType() {
        return tipo;
    }
}
