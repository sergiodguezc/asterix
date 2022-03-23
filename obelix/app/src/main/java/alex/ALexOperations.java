package alex;

import asint.ClaseLexica;

public class ALexOperations {
  private AnalizadorLexicoAsterix alex;
  public ALexOperations(AnalizadorLexicoAsterix alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadDosPuntos() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.dospuntos); 
  } 
  public UnidadLexica unidadForix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.forix); 
  } 
  public UnidadLexica unidadPot() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.pot); 
  } 
  public UnidadLexica unidadReturn() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ret); 
  } 
  public UnidadLexica unidadPotion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.potion); 
  } 
  public UnidadLexica unidadPanoramix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.panor); 
  } 
  public UnidadLexica unidadInteger() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.integer, alex.lexema()); 
  } 
  public UnidadLexica unidadFloating() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.floating, alex.lexema()); 
  } 
  public UnidadLexica unidadParentesisAbierto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.paa); 
  } 
  public UnidadLexica unidadParentesisCerrado() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.pac); 
  } 
  public UnidadLexica unidadOperadorIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.igual); 
  } 
  public UnidadLexica unidadNot() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.not); 
  } 
  public UnidadLexica unidadIf() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ift); 
  } 
  public UnidadLexica unidadElse() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.elset); 
  } 
  public UnidadLexica unidadGalo() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.galo); 
  } 
  public UnidadLexica unidadRomano() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.romano); 
  } 
  public UnidadLexica unidadModulo() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.mod); 
  } 
  public UnidadLexica unidadReferencia() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ref); 
  } 
  public UnidadLexica unidadOperadorMultiplicacion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.mul); 
  } 
  public UnidadLexica unidadOperadorDivision() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.div); 
  } 
  public UnidadLexica unidadOperadorMas() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.mas); 
  } 
  public UnidadLexica unidadOperadorMenos() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.menos); 
  } 
  public UnidadLexica unidadPunto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.punto); 
  } 
  public UnidadLexica unidadPuntoComa() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.puntocoma); 
  } 
  public UnidadLexica unidadMenor() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.menor); 
  } 
  public UnidadLexica unidadOperadorMenorIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.meigual); 
  } 
  public UnidadLexica unidadOperadorMayorIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.maigual); 
  } 
  public UnidadLexica unidadAsignacion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.asig); 
  } 
  public UnidadLexica unidadMayor() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.mayor); 
  } 
  public UnidadLexica unidadId() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.id, alex.lexema()); 
  } 
  public UnidadLexica unidadCorcheteAbierto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.cora); 
  } 
  public UnidadLexica unidadCorcheteCerrado() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.corc); 
  } 
  public UnidadLexica unidadLlaveAbierto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.lla); 
  } 
  public UnidadLexica unidadLlaveCerrada() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.llc); 
  } 
  public UnidadLexica unidadPotencia() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.pow); 
  } 
  public UnidadLexica unidadDistinto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.dis); 
  } 
  public UnidadLexica unidadAnd() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.and); 
  } 
  public UnidadLexica unidadOr() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.or); 
  } 
  public UnidadLexica unidadFlecha() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.flecha); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.coma); 
  } 
  public UnidadLexica unidadDatix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.datix); 
  } 
  public UnidadLexica unidadIntix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.intix); 
  } 
  public UnidadLexica unidadBoolix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.boolix); 
  } 
  public UnidadLexica unidadEnumix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.enumix); 
  } 
  public UnidadLexica unidadStilus() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.stil); 
  } 
  public UnidadLexica unidadVectix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.vectix); 
  } 
  public UnidadLexica unidadWhilix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.whilix); 
  } 
  public UnidadLexica unidadFloatix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.floatix); 
  } 
  public UnidadLexica unidadTabellae() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.tabel); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EOF); 
  }
  public void error() {
    System.err.println("***"+alex.fila()+", "+alex.columna()+" Caracter inexperado: "+alex.lexema());
  }
}
