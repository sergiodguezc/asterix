package alex;

import asint.ClaseLexica;

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
  public UnidadLexica unidadPow() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POW); 
  } 
  public UnidadLexica unidadMaIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAIGUAL); 
  } 
  public UnidadLexica unidadPot() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POT); 
  } 
  public UnidadLexica unidadRet() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.RET); 
  } 
  public UnidadLexica unidadPotion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POTION); 
  } 
  public UnidadLexica unidadMeIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MEIGUAL); 
  } 
  public UnidadLexica unidadPanor() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PANOR); 
  } 
  public UnidadLexica unidadInteger() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.INTEGER); 
  } 
  public UnidadLexica unidadPCierre() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PCIERRE); 
  } 
  public UnidadLexica unidadIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IGUAL); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.COMA); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EOF); 
  }
}
