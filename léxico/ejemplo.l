package alex;

%%
%char
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode

%{
  private ALexOperations ops;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
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
parteEntera = {digitoPositivo}{digito}*
parteDecimal = {digito}* {digitoPositivo}
separador = [ \t\r\b\n]
comentario = \/\/[^\n]*


operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
operadorIgual = \=\=
operadorOr = \|\|
operadorAnd = &&
operadorDistinto = \!\=
operadorMenorIgual = \<\=
operadorMayorIgual = \>\=
operadorConcat = #
operadorModulo = \%
operadorPotencia = \^
operadorNot = \!
operadorReferencia = &
operadorIncremento = \+\+
operadorDecremento = \-\-
operadorPunto = .

identificador = {letra}({letra}|{digito})*
asignacion = \=
intix = intix
integer =  [\+\-]?{parteEntera}
boolix = boolix
galo = galo
romano = romano
enumix = enumix
coma  = ,
puntoComa = \;
floatix = floatix
floating = [\+\-]?{parteEntera}\.{parteDecimal}
datix = datix

pot = pot
vectix = vectix
mayor = \>
menor = \<
corcheteAbierto = \[
corcheteCerrado = \]
llaveAbierto = \{
llaveCerrado = \}
parentesisApertura = \(
parentesisCierre = \)

if = if
else = else
whilix = whilix
forix = forix
panoramix = panoramix
potion = potion
dosPuntos = :
flecha = \-\>
return = return
skip = skip

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
{asignacion}                   {return ops.unidadAsignacion();}
{puntoComa}                   {return ops.unidadPuntoComa();}
{coma}                   {return ops.unidadComa();}

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
{operadorSuma}              {return ops.unidadOperadorSuma();}
{operadorResta}              {return ops.unidadOperadorResta();}
{operadorMultiplicacion}              {return ops.unidadOperadorMultiplicacion();}
{operadorDivision}              {return ops.unidadOperadorDivision();}
{operadorIgual}              {return ops.unidadOperadorIgual();}
{operadorMenorIgual}              {return ops.unidadOperadorMenorIgual();}
{operadorMayorIgual}              {return ops.unidadOperadorMayorIgual();}
{operadorConcat}              {return ops.unidadOperadorConcat();}





operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
operadorIgual = \=\=
operadorOr = \|\|
operadorAnd = &&
operadorDistinto = \!\=
operadorMenorIgual = \<\=
operadorMayorIgual = \>\=
operadorConcat = #
operadorModulo = \%
operadorPotencia = \^
operadorNot = \!
operadorReferencia = &
operadorIncremento = \+\+
operadorDecremento = \-\-
operadorPunto = .




{identificador}           {return ops.unidadId();}
{numeroEntero}            {return ops.unidadEnt();}
{numeroReal}              {return ops.unidadReal();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{igual}                   {return ops.unidadIgual();} 
{coma}                    {return ops.unidadComa();}
[^]                       {ops.error();}  
