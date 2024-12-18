package org.example;

import java_cup.runtime.*;
import java.util.HashMap;
import org.example.SymbolTable;
import org.example.sym;

%%

%class Lexer
%public
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
_verano_ = _verano_
saltoLinea           = \r|\n|\r\n
comentario           = @.*\n
comentarioMultilinea = \\_(.*?)_\/
espacio              = [ \t\r\n\f]

// Tipos de datos
rodolfo         = rodolfo
bromista        = bromista
trueno          = trueno
cupido          = cupido
cometa          = cometa

// Tipos de control
elfo            = elfo
hada            = hada
envuelve        = envuelve
duende          = duende
varios          = varios
historia        = historia
ultimo          = ultimo

// Operadores y símbolos especiales
navidad         = navidad
intercambio     = intercambio
reyes           = reyes
nochebuena      = nochebuena
magos           = magos
adviento        = adviento
entrega         = entrega

// Operadores relacionales
snowball        = snowball
evergreen       = evergreen
minstix         = minstix
upatree         = upatree
mary            = mary
openslae        = openslae

// Operadores lógicos
melchor         = melchor
gaspar          = gaspar
baltazar        = baltazar

// Otros tokens especiales
quien           = quien
grinch          = grinch
corta           = corta
envia           = envia
sigue           = sigue
narra           = narra
escucha         = escucha
abreregalo      = abreregalo
cierraregalo    = cierraregalo
abreempaque     = abreempaque
cierraempaque   = cierraempaque
abrecuento      = abrecuento
cierracuento    = cierracuento
finregalo       = finregalo

// Definiciones para literales y otros elementos
letra           = [a-zA-Z]
digito          = [0-9]
identificador   = _[a-zA-Z]([a-zA-Z0-9_])*_
entero          = {digito}+
flotante        = {digito}+\.{digito}+
caracter        = \'[^\'\\\n\r]*\'
cadena          = \"[^\"]*\"
coma            = ,
booleano        = (true|false)

%%

//cosas para ignorar
{comentario} { /* No token, just consume the comment */ }
{comentarioMultilinea} {/* No token, just consume the comment */}
{coma} {
        symbolTable.addSymbol(yytext(), "COMA", yyline, yycolumn, null);
        return new Symbol(sym.COMA, yyline, yycolumn);
}
{espacio} {/* No token, just consume the comment */}



{rodolfo}                {
    symbolTable.addSymbol(yytext(), "TIPO_ENTERO", yyline, yycolumn, null);
    return new Symbol(sym.TIPO_ENTERO, yyline, yycolumn);
}

{bromista}               {
    symbolTable.addSymbol(yytext(), "TIPO_FLOTANTE", yyline, yycolumn, null);
    return new Symbol(sym.TIPO_FLOTANTE, yyline, yycolumn);
}
{trueno}                 {
    symbolTable.addSymbol(yytext(), "TIPO_BOOLEANO", yyline, yycolumn, null);
    return new Symbol(sym.TIPO_BOOLEANO, yyline, yycolumn);
}
{cupido}                 {
    symbolTable.addSymbol(yytext(), "TIPO_CHAR", yyline, yycolumn, null);
    return new Symbol(sym.TIPO_CHAR, yyline, yycolumn);
}
{cometa}                 {
    symbolTable.addSymbol(yytext(), "TIPO_STRING", yyline, yycolumn, null);
    return new Symbol(sym.TIPO_STRING, yyline, yycolumn);
}

/* --- Sección de palabras reservadas de control --- */
{elfo}                   {
    symbolTable.addSymbol(yytext(), "IF", yyline, yycolumn, null);
    return new Symbol(sym.IF, yyline, yycolumn);
}
{hada}                   {
    symbolTable.addSymbol(yytext(), "ELSE", yyline, yycolumn, null);
    return new Symbol(sym.ELSE, yyline, yycolumn);
}
{envuelve}               {
    symbolTable.addSymbol(yytext(), "WHILE", yyline, yycolumn, null);
    return new Symbol(sym.WHILE, yyline, yycolumn);
}
{duende}                 {
    symbolTable.addSymbol(yytext(), "FOR", yyline, yycolumn, null);
    return new Symbol(sym.FOR, yyline, yycolumn);
}
{varios}                 {
    symbolTable.addSymbol(yytext(), "SWITCH", yyline, yycolumn, null);
    return new Symbol(sym.SWITCH, yyline, yycolumn);
}
{historia}               {
    symbolTable.addSymbol(yytext(), "CASE", yyline, yycolumn, null);
    return new Symbol(sym.CASE, yyline, yycolumn);
}
{ultimo}                 {
    symbolTable.addSymbol(yytext(), "DEFAULT", yyline, yycolumn, null);
    return new Symbol(sym.DEFAULT, yyline, yycolumn);
}
{corta}                  {
    symbolTable.addSymbol(yytext(), "BREAK", yyline, yycolumn, null);
    return new Symbol(sym.BREAK, yyline, yycolumn);
}
{envia}                  {
    symbolTable.addSymbol(yytext(), "RETURN", yyline, yycolumn, null);
    return new Symbol(sym.RETURN, yyline, yycolumn);
}
{sigue}                  {
    symbolTable.addSymbol(yytext(), "DOS_PUNTOS", yyline, yycolumn, null);
    return new Symbol(sym.DOS_PUNTOS, yyline, yycolumn);
}

/* --- Sección de operadores aritméticos --- */
{navidad}                {
    symbolTable.addSymbol(yytext(), "SUMA", yyline, yycolumn, null);
    return new Symbol(sym.SUMA, yyline, yycolumn);
}
{intercambio}            {
    symbolTable.addSymbol(yytext(), "RESTA", yyline, yycolumn, null);
    return new Symbol(sym.RESTA, yyline, yycolumn);
}
{reyes}                  {
    symbolTable.addSymbol(yytext(), "DIVISION", yyline, yycolumn, null);
    return new Symbol(sym.DIVISION, yyline, yycolumn);
}
{nochebuena}             {
    symbolTable.addSymbol(yytext(), "MULTIPLICACION", yyline, yycolumn, null);
    return new Symbol(sym.MULTIPLICACION, yyline, yycolumn);
}
{magos}                  {
    symbolTable.addSymbol(yytext(), "MODULO", yyline, yycolumn, null);
    return new Symbol(sym.MODULO, yyline, yycolumn);
}
{adviento}               {
    symbolTable.addSymbol(yytext(), "POTENCIA", yyline, yycolumn, null);
    return new Symbol(sym.POTENCIA, yyline, yycolumn);
}
{entrega}                {
    symbolTable.addSymbol(yytext(), "ASIGNACION", yyline, yycolumn, null);
    return new Symbol(sym.ASIGNACION, yyline, yycolumn);
}
{quien}                  {
    symbolTable.addSymbol(yytext(), "INCREMENTO", yyline, yycolumn, null);
    return new Symbol(sym.INCREMENTO, yyline, yycolumn);
}
{grinch}                 {
    symbolTable.addSymbol(yytext(), "DECREMENTO", yyline, yycolumn, null);
    return new Symbol(sym.DECREMENTO, yyline, yycolumn);
}

/* --- Sección de operadores relacionales --- */
{snowball}               {
    symbolTable.addSymbol(yytext(), "MENOR", yyline, yycolumn, null);
    return new Symbol(sym.MENOR, yyline, yycolumn);
}
{evergreen}              {
    symbolTable.addSymbol(yytext(), "MENOR_IGUAL", yyline, yycolumn, null);
    return new Symbol(sym.MENOR_IGUAL, yyline, yycolumn);
}
{minstix}                {
    symbolTable.addSymbol(yytext(), "MAYOR", yyline, yycolumn, null);
    return new Symbol(sym.MAYOR, yyline, yycolumn);
}
{upatree}                {
    symbolTable.addSymbol(yytext(), "MAYOR_IGUAL", yyline, yycolumn, null);
    return new Symbol(sym.MAYOR_IGUAL, yyline, yycolumn);
}
{mary}                   {
    symbolTable.addSymbol(yytext(), "IGUAL", yyline, yycolumn, null);
    return new Symbol(sym.IGUAL, yyline, yycolumn);
}
{openslae}               {
    symbolTable.addSymbol(yytext(), "DIFERENTE", yyline, yycolumn, null);
    return new Symbol(sym.DIFERENTE, yyline, yycolumn);
}

/* --- Sección de operadores lógicos --- */
{melchor}                {
    symbolTable.addSymbol(yytext(), "AND", yyline, yycolumn, null);
    return new Symbol(sym.AND, yyline, yycolumn);
}

{gaspar}                 {
    symbolTable.addSymbol(yytext(), "OR", yyline, yycolumn, null);
    return new Symbol(sym.OR, yyline, yycolumn);
}

{baltazar}               {
    symbolTable.addSymbol(yytext(), "NOT", yyline, yycolumn, null);
    return new Symbol(sym.NOT, yyline, yycolumn);
}

/* --- Sección de otros tokens especiales --- */
{narra}                  {
    symbolTable.addSymbol(yytext(), "PRINT", yyline, yycolumn, null);
    return new Symbol(sym.PRINT, yyline, yycolumn);
}

{abreempaque}            {
    symbolTable.addSymbol(yytext(), "CORCHETE_ABRE", yyline, yycolumn, null);
    return new Symbol(sym.CORCHETE_ABRE, yyline, yycolumn);
}

{cierraempaque}          {
    symbolTable.addSymbol(yytext(), "CORCHETE_CIERRA", yyline, yycolumn, null);
    return new Symbol(sym.CORCHETE_CIERRA, yyline, yycolumn);
}

{_verano_}               {
    symbolTable.addSymbol(yytext(), "MAIN", yyline, yycolumn, null);
    return new Symbol(sym.MAIN, yyline, yycolumn);
}

{escucha}                {
    symbolTable.addSymbol(yytext(), "READ", yyline, yycolumn, null);
    return new Symbol(sym.READ, yyline, yycolumn);
}

/* --- Sección de literales y valores --- */
{entero}                 {
    symbolTable.addSymbol(yytext(), "LITERAL_ENTERO", yyline, yycolumn, Integer.valueOf(yytext()));
    return new Symbol(sym.LITERAL_ENTERO, yyline, yycolumn, Integer.valueOf(yytext()));
}

{flotante}               {
    symbolTable.addSymbol(yytext(), "LITERAL_FLOTANTE", yyline, yycolumn, Float.valueOf(yytext()));
    return new Symbol(sym.LITERAL_FLOTANTE, yyline, yycolumn, Float.valueOf(yytext()));
}

{cadena}                 {
    symbolTable.addSymbol(yytext(), "LITERAL_STRING", yyline, yycolumn, yytext());
    return new Symbol(sym.LITERAL_STRING, yyline, yycolumn, yytext());
}

{booleano}               {
    symbolTable.addSymbol(yytext(), "LITERAL_BOOLEANO", yyline, yycolumn, null);
    return new Symbol(sym.LITERAL_BOOLEANO, yyline, yycolumn);
}

{abrecuento}             {
    symbolTable.addSymbol(yytext(), "LLAVE_ABRE", yyline, yycolumn, null);
    return new Symbol(sym.LLAVE_ABRE, yyline, yycolumn, yytext());
}

{cierracuento}             {
    symbolTable.addSymbol(yytext(), "LLAVE_CIERRA", yyline, yycolumn, null);
    return new Symbol(sym.LLAVE_CIERRA, yyline, yycolumn, yytext());
}

{abreregalo}             {
    symbolTable.addSymbol(yytext(), "PARENTESIS_ABRE", yyline, yycolumn, null);
    return new Symbol(sym.PARENTESIS_ABRE, yyline, yycolumn, yytext());
}

{cierraregalo}             {
    symbolTable.addSymbol(yytext(), "PARENTESIS_CIERRA", yyline, yycolumn, null);
    return new Symbol(sym.PARENTESIS_CIERRA, yyline, yycolumn, yytext());
}

{finregalo}                {
    symbolTable.addSymbol(yytext(), "FIN_EXPRESION", yyline, yycolumn, null);
    return new Symbol(sym.FIN_EXPRESION, yyline, yycolumn, yytext());
}

/* --- Sección de identificadores --- */
{identificador}          {
    symbolTable.addSymbol(yytext(), "IDENTIFICADOR", yyline, yycolumn, null);
    return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext());
}

/* Manejo de errores */
[^]                      {
    System.err.println("Error léxico: Carácter no reconocido '" + yytext() +
                       "' en línea " + (yyline+1) + ", columna " + (yycolumn+1));
    return new Symbol(sym.ERROR, yyline, yycolumn, yytext());
}