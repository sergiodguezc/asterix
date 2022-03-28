package alexop;

import asint.ClaseLexica;
import alex.AnalizadorLexicoAsterix;

public class ALexOperations {
  private AnalizadorLexicoAsterix alex;
  public ALexOperations(AnalizadorLexicoAsterix alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadDosPuntos() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DOSPUNTOS,alex.lexema()); 
  } 
  public UnidadLexica unidadForix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FORIX,alex.lexema()); 
  } 
  public UnidadLexica unidadPot() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POT,alex.lexema()); 
  } 
  public UnidadLexica unidadReturn() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.RET,alex.lexema()); 
  } 
  public UnidadLexica unidadPotion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POTION,alex.lexema()); 
  } 
  public UnidadLexica unidadPanoramix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PANOR,alex.lexema()); 
  } 
  public UnidadLexica unidadInteger() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.INTEGER, alex.lexema()); 
  } 
  public UnidadLexica unidadFloating() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FLOATING, alex.lexema()); 
  } 
  public UnidadLexica unidadParentesisAbierto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PAA,alex.lexema()); 
  } 
  public UnidadLexica unidadParentesisCerrado() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PAC,alex.lexema()); 
  } 
  public UnidadLexica unidadOperadorIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IGUAL,alex.lexema()); 
  } 
  public UnidadLexica unidadNot() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.NOT,alex.lexema()); 
  } 
  public UnidadLexica unidadIf() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IF,alex.lexema()); 
  } 
  public UnidadLexica unidadElse() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ELSE,alex.lexema()); 
  } 
  public UnidadLexica unidadGalo() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.GALO,alex.lexema()); 
  } 
  public UnidadLexica unidadRomano() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ROMANO,alex.lexema()); 
  } 
  public UnidadLexica unidadModulo() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MOD,alex.lexema()); 
  } 
  public UnidadLexica unidadReferencia() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.REF,alex.lexema()); 
  } 
  public UnidadLexica unidadOperadorMultiplicacion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MUL,alex.lexema()); 
  } 
  public UnidadLexica unidadOperadorDivision() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DIV,alex.lexema()); 
  } 
  public UnidadLexica unidadOperadorMas() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAS,alex.lexema()); 
  } 
  public UnidadLexica unidadOperadorMenos() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MENOS,alex.lexema()); 
  } 
  public UnidadLexica unidadPunto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PUNTO,alex.lexema()); 
  } 
  public UnidadLexica unidadPuntoComa() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PUNTOCOMA,alex.lexema()); 
  } 
  public UnidadLexica unidadMenor() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MENOR,alex.lexema()); 
  } 
  public UnidadLexica unidadOperadorMenorIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MEIGUAL,alex.lexema()); 
  } 
  public UnidadLexica unidadOperadorMayorIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAIGUAL,alex.lexema()); 
  } 
  public UnidadLexica unidadAsignacion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ASIG,alex.lexema()); 
  } 
  public UnidadLexica unidadMayor() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAYOR,alex.lexema()); 
  } 
  public UnidadLexica unidadId() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ID, alex.lexema()); 
  } 
  public UnidadLexica unidadCorcheteAbierto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CORA,alex.lexema()); 
  } 
  public UnidadLexica unidadCorcheteCerrado() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CORC,alex.lexema()); 
  } 
  public UnidadLexica unidadLlaveAbierto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.LLA,alex.lexema()); 
  } 
  public UnidadLexica unidadLlaveCerrada() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.LLC,alex.lexema()); 
  } 
  public UnidadLexica unidadPotencia() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POW,alex.lexema()); 
  } 
  public UnidadLexica unidadDistinto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DIS,alex.lexema()); 
  } 
  public UnidadLexica unidadAnd() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.AND,alex.lexema()); 
  } 
  public UnidadLexica unidadOr() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.OR,alex.lexema()); 
  } 
  public UnidadLexica unidadFlecha() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FLECHA,alex.lexema()); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.COMA,alex.lexema()); 
  } 
  public UnidadLexica unidadIntix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.INTIX,alex.lexema()); 
  } 
  public UnidadLexica unidadBoolix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.BOOLIX,alex.lexema()); 
  } 

  public UnidadLexica unidadStilus() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.STIL,alex.lexema()); 
  } 
  public UnidadLexica unidadVectix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.VECTIX,alex.lexema()); 
  } 
  public UnidadLexica unidadWhilix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.WHILIX,alex.lexema()); 
  } 
  public UnidadLexica unidadFloatix() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FLOATIX,alex.lexema()); 
  } 
  public UnidadLexica unidadTabellae() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.TABEL,alex.lexema()); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EOF,alex.lexema()); 
  }
  public void error() {
    System.err.println("***"+alex.fila()+", "+alex.columna()+" Caracter inexperado: "+alex.lexema());
  }
}
