import java_cup.runtime.*;

%%
%class Scanner
%cup
%line
%column
%unicode

// Definiciones regulares
espacio     = [ \t\r\n\f]
saltoLinea  = \r|\n|\r\n
comentarioLinea = #.*
comentarioMultilinea = _[^_]*_

// Tipos de datos
rodolfo     = rodolfo
bromista    = bromista
trueno      = trueno
cupido      = cupido
cometa      = cometa

// Tipos de control
elfo        = elfo
hada        = hada
envuelve    = envuelve
duende      = duende
varios      = varios
historia    = historia
ultimo      = ultimo

// Operadores y símbolos especiales
navidad     = navidad
intercambio = intercambio
reyes       = reyes
nochebuena  = nochebuena
magos       = magos
adviento    = adviento

// Operadores relacionales
snowball    = snowball
evergreen   = evergreen
minstix     = minstix
upatree     = upatree
mary        = mary
openslae    = openslae

// Operadores lógicos
melchor     = melchor
gaspar      = gaspar
baltazar    = baltazar

// Otros tokens especiales
quien       = quien
grinch      = grinch
corta       = corta
envia       = envia
sigue       = sigue
narra       = narra
escucha     = escucha

// Definiciones para literales y otros elementos
letra       = [a-zA-Z]
digito      = [0-9]
identificador = _[a-zA-Z0-9]+_
entero      = [0-9]+
flotante    = [0-9]+\.[0-9]+
caracter    = '.'
cadena      = \"[^\"]*\"

%%
/* Reglas léxicas */

/* Ignorar espacios en blanco y comentarios */
{espacio}+               { /* Ignorar */ }
{comentarioLinea}        { /* Ignorar comentarios de línea */ }
{comentarioMultilinea}   { /* Ignorar comentarios multilínea */ }

/* Palabras reservadas de tipos de datos */
{rodolfo}                { return new Symbol(sym.TIPO_ENTERO, yyline, yycolumn); }
{bromista}               { return new Symbol(sym.TIPO_FLOTANTE, yyline, yycolumn); }
{trueno}                 { return new Symbol(sym.TIPO_BOOLEANO, yyline, yycolumn); }
{cupido}                 { return new Symbol(sym.TIPO_CHAR, yyline, yycolumn); }
{cometa}                 { return new Symbol(sym.TIPO_STRING, yyline, yycolumn); }

/* Palabras reservadas de control */
{elfo}                   { return new Symbol(sym.IF, yyline, yycolumn); }
{hada}                   { return new Symbol(sym.ELSE, yyline, yycolumn); }
{envuelve}               { return new Symbol(sym.WHILE, yyline, yycolumn); }
{duende}                 { return new Symbol(sym.FOR, yyline, yycolumn); }
{varios}                 { return new Symbol(sym.SWITCH, yyline, yycolumn); }
{historia}               { return new Symbol(sym.CASE, yyline, yycolumn); }
{ultimo}                 { return new Symbol(sym.DEFAULT, yyline, yycolumn); }

/* Operadores aritméticos */
{navidad}                { return new Symbol(sym.SUMA, yyline, yycolumn); }
{intercambio}            { return new Symbol(sym.RESTA, yyline, yycolumn); }
{reyes}                  { return new Symbol(sym.DIVISION, yyline, yycolumn); }
{nochebuena}             { return new Symbol(sym.MULTIPLICACION, yyline, yycolumn); }
{magos}                  { return new Symbol(sym.MODULO, yyline, yycolumn); }
{adviento}               { return new Symbol(sym.POTENCIA, yyline, yycolumn); }

/* Operadores relacionales */
{snowball}               { return new Symbol(sym.MENOR, yyline, yycolumn); }
{evergreen}              { return new Symbol(sym.MENOR_IGUAL, yyline, yycolumn); }
{minstix}                { return new Symbol(sym.MAYOR, yyline, yycolumn); }
{upatree}                { return new Symbol(sym.MAYOR_IGUAL, yyline, yycolumn); }
{mary}                   { return new Symbol(sym.IGUAL, yyline, yycolumn); }
{openslae}               { return new Symbol(sym.DIFERENTE, yyline, yycolumn); }

/* Operadores lógicos */
{melchor}                { return new Symbol(sym.AND, yyline, yycolumn); }
{gaspar}                 { return new Symbol(sym.OR, yyline, yycolumn); }
{baltazar}               { return new Symbol(sym.NOT, yyline, yycolumn); }

/* Operadores unarios */
{quien}                  { return new Symbol(sym.INCREMENTO, yyline, yycolumn); }
{grinch}                 { return new Symbol(sym.DECREMENTO, yyline, yycolumn); }

/* Otros tokens especiales */
{corta}                  { return new Symbol(sym.BREAK, yyline, yycolumn); }
{envia}                  { return new Symbol(sym.RETURN, yyline, yycolumn); }
{sigue}                  { return new Symbol(sym.DOS_PUNTOS, yyline, yycolumn); }
{narra}                  { return new Symbol(sym.PRINT, yyline, yycolumn); }
{escucha}                { return new Symbol(sym.READ, yyline, yycolumn); }

/* Literales y identificadores */
{identificador}          { return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext()); }
{entero}                 { return new Symbol(sym.LITERAL_ENTERO, yyline, yycolumn, new Integer(yytext())); }
{flotante}               { return new Symbol(sym.LITERAL_FLOTANTE, yyline, yycolumn, new Float(yytext())); }
{caracter}               { return new Symbol(sym.LITERAL_CHAR, yyline, yycolumn, yytext().charAt(1)); }
{cadena}                 { return new Symbol(sym.LITERAL_STRING, yyline, yycolumn, yytext()); }

/* Símbolos especiales */
"abreregalo"             { return new Symbol(sym.PARENTESIS_ABRE, yyline, yycolumn); }
"cierraregalo"           { return new Symbol(sym.PARENTESIS_CIERRA, yyline, yycolumn); }
"abreempaque"            { return new Symbol(sym.CORCHETE_ABRE, yyline, yycolumn); }
"cierraempaque"          { return new Symbol(sym.CORCHETE_CIERRA, yyline, yycolumn); }
"abrecuento"             { return new Symbol(sym.LLAVE_ABRE, yyline, yycolumn); }
"cierracuento"           { return new Symbol(sym.LLAVE_CIERRA, yyline, yycolumn); }
"entrega"                { return new Symbol(sym.ASIGNACION, yyline, yycolumn); }
"finregalo"              { return new Symbol(sym.FIN_EXPRESION, yyline, yycolumn); }

/* Manejo de errores */
[^]         {
    System.err.println("Error léxico: Carácter no reconocido '" + yytext() +
                       "' en línea " + (yyline+1) + ", columna " + (yycolumn+1));
    return new Symbol(sym.ERROR, yyline, yycolumn, yytext());
}