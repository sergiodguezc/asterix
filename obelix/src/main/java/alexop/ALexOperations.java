package alexop;

import asint.ClaseLexica;
import alex.AnalizadorLexicoAsterix;

public class ALexOperations {
  private AnalizadorLexicoAsterix alex;
  public ALexOperations(AnalizadorLexicoAsterix alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadDosPuntos() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DOSPUNTOS); 
  } 
  public UnidadLexica unidadForix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FORIX); 
  } 
  public UnidadLexica unidadPot() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POT); 
  } 
  public UnidadLexica unidadReturn() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.RET); 
  } 
  public UnidadLexica unidadPotion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POTION); 
  } 
  public UnidadLexica unidadPanoramix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PANOR); 
  } 
  public UnidadLexica unidadInteger() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.INTEGER, alex.lexema()); 
  } 
  public UnidadLexica unidadFloating() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FLOATING, alex.lexema()); 
  } 
  public UnidadLexica unidadParentesisAbierto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PAA); 
  } 
  public UnidadLexica unidadParentesisCerrado() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PAC); 
  } 
  public UnidadLexica unidadOperadorIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IGUAL); 
  } 
  public UnidadLexica unidadNot() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.NOT); 
  } 
  public UnidadLexica unidadIf() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IF); 
  } 
  public UnidadLexica unidadElse() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ELSE); 
  } 
  public UnidadLexica unidadGalo() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.GALO); 
  } 
  public UnidadLexica unidadRomano() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ROMANO); 
  } 
  public UnidadLexica unidadModulo() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MOD); 
  } 
  public UnidadLexica unidadReferencia() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.REF); 
  } 
  public UnidadLexica unidadOperadorMultiplicacion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MUL); 
  } 
  public UnidadLexica unidadOperadorDivision() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DIV); 
  } 
  public UnidadLexica unidadOperadorMas() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAS); 
  } 
  public UnidadLexica unidadOperadorMenos() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MENOS); 
  } 
  public UnidadLexica unidadPunto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PUNTO); 
  } 
  public UnidadLexica unidadPuntoComa() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PUNTOCOMA); 
  } 
  public UnidadLexica unidadMenor() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MENOR); 
  } 
  public UnidadLexica unidadOperadorMenorIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MEIGUAL); 
  } 
  public UnidadLexica unidadOperadorMayorIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAIGUAL); 
  } 
  public UnidadLexica unidadAsignacion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ASIG); 
  } 
  public UnidadLexica unidadMayor() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAYOR); 
  } 
  public UnidadLexica unidadId() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ID, alex.lexema()); 
  } 
  public UnidadLexica unidadCorcheteAbierto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CORA); 
  } 
  public UnidadLexica unidadCorcheteCerrado() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CORC); 
  } 
  public UnidadLexica unidadLlaveAbierto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.LLA); 
  } 
  public UnidadLexica unidadLlaveCerrada() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.LLC); 
  } 
  public UnidadLexica unidadPotencia() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POW); 
  } 
  public UnidadLexica unidadDistinto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DIS); 
  } 
  public UnidadLexica unidadAnd() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.AND); 
  } 
  public UnidadLexica unidadOr() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.OR); 
  } 
  public UnidadLexica unidadFlecha() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FLECHA); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.COMA); 
  } 
  public UnidadLexica unidadDatix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DATIX); 
  } 
  public UnidadLexica unidadIntix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.INTIX); 
  } 
  public UnidadLexica unidadBoolix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.BOOLIX); 
  } 

  public UnidadLexica unidadStilus() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.STIL); 
  } 
  public UnidadLexica unidadVectix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.VECTIX); 
  } 
  public UnidadLexica unidadWhilix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.WHILIX); 
  } 
  public UnidadLexica unidadFloatix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FLOATIX); 
  } 
  public UnidadLexica unidadTabellae() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.TABEL); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EOF); 
  }
  public void error() {
    System.err.println("***"+alex.fila()+", "+alex.columna()+" Caracter inexperado: "+alex.lexema());
  }
}
