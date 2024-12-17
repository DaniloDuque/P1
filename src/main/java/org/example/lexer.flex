import java_cup.runtime.*;
import java.util.HashMap;
import org.example.SymbolTable;

%%
%class Lexer
%cup
%line
%column
%unicode

%{
    // Tabla de símbolos
    SymbolTable symbolTable = new SymbolTable();

    // Método para obtener la tabla de símbolos
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
%}

// Definiciones regulares
espacio         = [ \t\r\n\f]
saltoLinea      = \r|\n|\r\n
comentarioLinea = \#.*
comentarioMultilinea = \_([^_]|[\r\n])*_

// Tipos de datos
rodolfo         = \brodolfo\b
bromista        = \bbromista\b
trueno          = \btrueno\b
cupido          = \bcupido\b
cometa          = \bcometa\b

// Tipos de control
elfo            = \belfo\b
hada            = \bhada\b
envuelve        = \benvuelve\b
duende          = \bduende\b
varios          = \bvarios\b
historia        = \bhistoria\b
ultimo          = \bultimo\b

// Operadores y símbolos especiales
navidad         = \bnavidad\b
intercambio     = \bintercambio\b
reyes           = \breyes\b
nochebuena      = \bnochebuena\b
magos           = \bmagos\b
adviento        = \badviento\b

// Operadores relacionales
snowball        = \bsnowball\b
evergreen       = \bevergreen\b
minstix         = \bminstix\b
upatree         = \bupatree\b
mary            = \bmary\b
openslae        = \bopenslae\b

// Operadores lógicos
melchor         = \bmelchor\b
gaspar          = \bgaspar\b
baltazar        = \bbaltazar\b

// Otros tokens especiales
quien           = \bquien\b
grinch          = \bgrinch\b
corta           = \bcorta\b
envia           = \benvia\b
sigue           = \bsigue\b
narra           = \bnarra\b
escucha         = \bescucha\b

// Definiciones para literales y otros elementos
letra           = [a-zA-Z]
digito          = [0-9]
identificador   = {letra}({letra}|{digito})*
entero          = {digito}+
flotante        = {digito}+\.{digito}+
caracter        = \'[^\']\'
cadena          = \"[^\"]*\"

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

/* Literales y valores */
{entero}                 {
    symbolTable.addSymbol(yytext(), "LITERAL_ENTERO", Integer.valueOf(yytext()));
    return new Symbol(sym.LITERAL_ENTERO, yyline, yycolumn, Integer.valueOf(yytext()));
}

{flotante}               {
    symbolTable.addSymbol(yytext(), "LITERAL_FLOTANTE", Float.valueOf(yytext()));
    return new Symbol(sym.LITERAL_FLOTANTE, yyline, yycolumn, Float.valueOf(yytext()));
}

{cadena}                 {
    symbolTable.addSymbol(yytext(), "LITERAL_CADENA", yytext());
    return new Symbol(sym.LITERAL_CADENA, yyline, yycolumn, yytext());
}

{identificador}          {
    symbolTable.addSymbol(yytext(), "IDENTIFICADOR", null);
    return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext());
}

/* Símbolos especiales */
"abreregalo"             { return new Symbol(sym.PARENTESIS_ABRE, yyline, yycolumn); }
"cierraregalo"           { return new Symbol(sym.PARENTESIS_CIERRA, yyline, yycolumn); }

/* Manejo de errores */
[^]                      {
    System.err.println("Error léxico: Carácter no reconocido '" + yytext() +
                       "' en línea " + (yyline+1) + ", columna " + (yycolumn+1));
    return new Symbol(sym.ERROR, yyline, yycolumn, yytext());
}
