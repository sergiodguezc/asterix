package alex;

import errors.GestionErroresAsterix;

%%
%cup
%line
%column
%class AnalizadorLexicoAsterix
%type  UnidadLexica
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresAsterix errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresAsterix errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteDecimal = {digito}* {digitoPositivo}
separador = [ \t\r\b\n]
comentario = \/\/[^\n]*

operadorMas = \+
operadorMenos = -
operadorMultiplicacion = \*
operadorDivision = \/
operadorIgual = \=\=
operadorOr = \|\|
operadorAnd = &&
operadorDistinto = \!\=
operadorMenorIgual = \<\=
operadorMayorIgual = \>\=
operadorModulo = \%
operadorPotencia = \^
operadorNot = \!
operadorReferencia = &
operadorPunto = \.

identificador = {letra}({letra}|{digito})*
operadorAsignacion = \=
intix = intix
integer = ({digitoPositivo}{digito}*|{digito})
boolix = boolix
galo = galo
romano = romano
enumix = enumix
coma  = ,
puntoComa = \;
floatix = floatix
floating = {integer}\.{parteDecimal}
datix = datix

pot = pot
vectix = vectix
mayor = \>
menor = \<
corcheteAbierto = \[
corcheteCerrado = \]
llaveAbierto = \{
llaveCerrado = \}
parentesisAbierto = \(
parentesisCerrado = \)

if = if
else = else
whilix = whilix
forix = forix
panoramix = panoramix
potion = potion
dosPuntos = :
flecha = \-\>
return = return

tabellae = tabellae
stilus = stilus

%%
{separador}               {}
{comentario}              {}
{intix}                   {return ops.unidadIntix();}
{boolix}                   {return ops.unidadBoolix();}
{enumix}                   {return ops.unidadEnumix();}
{floatix}                   {return ops.unidadFloatix();}
{pot}                   {return ops.unidadPot();}
{vectix}                   {return ops.unidadVectix();}
{datix}                   {return ops.unidadDatix();}
{puntoComa}                   {return ops.unidadPuntoComa();}
{coma}                   {return ops.unidadComa();}
{flecha}              {return ops.unidadFlecha();}

{integer}                   {return ops.unidadInteger();}
{floating}                   {return ops.unidadFloating();}
{galo}                   {return ops.unidadGalo();}
{romano}                   {return ops.unidadRomano();}
{menor}                   {return ops.unidadMenor();}
{mayor}                   {return ops.unidadMayor();}
{corcheteAbierto}        {return ops.unidadCorcheteAbierto();}
{corcheteCerrado}        {return ops.unidadCorcheteCerrado();}

{parentesisAbierto}        {return ops.unidadParentesisAbierto();}
{parentesisCerrado}        {return ops.unidadParentesisCerrado();}
{llaveAbierto}        {return ops.unidadLlaveAbierto();}
{llaveCerrado}        {return ops.unidadLlaveCerrada();}

{operadorMas}              {return ops.unidadOperadorMas();}
{operadorMenos}              {return ops.unidadOperadorMenos();}
{operadorMultiplicacion}              {return ops.unidadOperadorMultiplicacion();}
{operadorDivision}              {return ops.unidadOperadorDivision();}
{operadorIgual}              {return ops.unidadOperadorIgual();}
{operadorOr}              {return ops.unidadOr();}
{operadorAnd}              {return ops.unidadAnd();}
{operadorDistinto}              {return ops.unidadDistinto();}
{operadorAsignacion}                   {return ops.unidadAsignacion();}
{operadorMenorIgual}              {return ops.unidadOperadorMenorIgual();}
{operadorMayorIgual}              {return ops.unidadOperadorMayorIgual();}
{operadorModulo}              {return ops.unidadModulo();}
{operadorPotencia}              {return ops.unidadPotencia();}
{operadorNot}              {return ops.unidadNot();}
{operadorReferencia}              {return ops.unidadReferencia();}
{operadorPunto}              {return ops.unidadPunto();}
{if}              {return ops.unidadIf();}
{else}              {return ops.unidadElse();}
{whilix}              {return ops.unidadWhilix();}
{forix}              {return ops.unidadForix();}
{panoramix}              {return ops.unidadPanoramix();}
{potion}              {return ops.unidadPotion();}
{dosPuntos}              {return ops.unidadDosPuntos();}
{return}              {return ops.unidadReturn();}
{tabellae}              {return ops.unidadTabellae();}
{stilus}              {return ops.unidadStilus();}

{identificador}                   {return ops.unidadId();}

[^]                       {ops.error();}  
