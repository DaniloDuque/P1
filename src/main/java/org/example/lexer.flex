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
_verano_             = _verano_
saltoLinea           = \r|\n|\r\n
comentario           = @.*\n
comentarioMultilinea = "\\_"([^\\_]|\\n|\\r)*"_/"
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
identificador   = _{letra}({letra}|{digito})*_
entero          = -?([1-9]{digito}*|0)
flotante        = {entero}\.{digito}+
caracter        = \'[^\n\r\'\\]*\'
cadena          = \"[^\"]*\"
coma            = ,
booleano        = (true|false)

%%

//cosas para ignorar
{comentario} { /* No token, just consume the comment */ }
{comentarioMultilinea} {/* No token, just consume the comment */}
{coma} {
        symbolTable.addSymbol(yytext(), "COMA", yyline, yycolumn);
        return new Symbol(sym.COMA, yyline, yycolumn);
}

{espacio} {/* No token, just consume the comment */}

{rodolfo}                {
    symbolTable.addSymbol(yytext(), "ENTERO", yyline, yycolumn);
    return new Symbol(sym.ENTERO, yyline, yycolumn);
}

{bromista}               {
    symbolTable.addSymbol(yytext(), "FLOTANTE", yyline, yycolumn);
    return new Symbol(sym.FLOTANTE, yyline, yycolumn);
}
{trueno}                 {
    symbolTable.addSymbol(yytext(), "BOOLEANO", yyline, yycolumn);
    return new Symbol(sym.BOOLEANO, yyline, yycolumn);
}
{cupido}                 {
    symbolTable.addSymbol(yytext(), "CARACTER", yyline, yycolumn);
    return new Symbol(sym.CARACTER, yyline, yycolumn);
}
{cometa}                 {
    symbolTable.addSymbol(yytext(), "CADENA", yyline, yycolumn);
    return new Symbol(sym.CADENA, yyline, yycolumn);
}

/* --- Sección de palabras reservadas de control --- */
{elfo}                   {
    symbolTable.addSymbol(yytext(), "IF", yyline, yycolumn);
    return new Symbol(sym.IF, yyline, yycolumn);
}
{hada}                   {
    symbolTable.addSymbol(yytext(), "ELSE", yyline, yycolumn);
    return new Symbol(sym.ELSE, yyline, yycolumn);
}
{envuelve}               {
    symbolTable.addSymbol(yytext(), "WHILE", yyline, yycolumn);
    return new Symbol(sym.WHILE, yyline, yycolumn);
}
{duende}                 {
    symbolTable.addSymbol(yytext(), "FOR", yyline, yycolumn);
    return new Symbol(sym.FOR, yyline, yycolumn);
}
{varios}                 {
    symbolTable.addSymbol(yytext(), "SWITCH", yyline, yycolumn);
    return new Symbol(sym.SWITCH, yyline, yycolumn);
}
{historia}               {
    symbolTable.addSymbol(yytext(), "CASE", yyline, yycolumn);
    return new Symbol(sym.CASE, yyline, yycolumn);
}
{ultimo}                 {
    symbolTable.addSymbol(yytext(), "DEFAULT", yyline, yycolumn);
    return new Symbol(sym.DEFAULT, yyline, yycolumn);
}
{corta}                  {
    symbolTable.addSymbol(yytext(), "BREAK", yyline, yycolumn);
    return new Symbol(sym.BREAK, yyline, yycolumn);
}
{envia}                  {
    symbolTable.addSymbol(yytext(), "RETURN", yyline, yycolumn);
    return new Symbol(sym.RETURN, yyline, yycolumn);
}
{sigue}                  {
    symbolTable.addSymbol(yytext(), "DOS_PUNTOS", yyline, yycolumn);
    return new Symbol(sym.DOS_PUNTOS, yyline, yycolumn);
}

/* --- Sección de operadores aritméticos --- */
{navidad}                {
    symbolTable.addSymbol(yytext(), "SUMA", yyline, yycolumn);
    return new Symbol(sym.SUMA, yyline, yycolumn);
}
{intercambio}            {
    symbolTable.addSymbol(yytext(), "RESTA", yyline, yycolumn);
    return new Symbol(sym.RESTA, yyline, yycolumn);
}
{reyes}                  {
    symbolTable.addSymbol(yytext(), "DIVISION", yyline, yycolumn);
    return new Symbol(sym.DIVISION, yyline, yycolumn);
}
{nochebuena}             {
    symbolTable.addSymbol(yytext(), "MULTIPLICACION", yyline, yycolumn);
    return new Symbol(sym.MULTIPLICACION, yyline, yycolumn);
}
{magos}                  {
    symbolTable.addSymbol(yytext(), "MODULO", yyline, yycolumn);
    return new Symbol(sym.MODULO, yyline, yycolumn);
}
{adviento}               {
    symbolTable.addSymbol(yytext(), "POTENCIA", yyline, yycolumn);
    return new Symbol(sym.POTENCIA, yyline, yycolumn);
}
{entrega}                {
    symbolTable.addSymbol(yytext(), "ASIGNACION", yyline, yycolumn);
    return new Symbol(sym.ASIGNACION, yyline, yycolumn);
}
{quien}                  {
    symbolTable.addSymbol(yytext(), "INCREMENTO", yyline, yycolumn);
    return new Symbol(sym.INCREMENTO, yyline, yycolumn);
}
{grinch}                 {
    symbolTable.addSymbol(yytext(), "DECREMENTO", yyline, yycolumn);
    return new Symbol(sym.DECREMENTO, yyline, yycolumn);
}

/* --- Sección de operadores relacionales --- */
{snowball}               {
    symbolTable.addSymbol(yytext(), "MENOR", yyline, yycolumn);
    return new Symbol(sym.MENOR, yyline, yycolumn);
}
{evergreen}              {
    symbolTable.addSymbol(yytext(), "MENOR_IGUAL", yyline, yycolumn);
    return new Symbol(sym.MENOR_IGUAL, yyline, yycolumn);
}
{minstix}                {
    symbolTable.addSymbol(yytext(), "MAYOR", yyline, yycolumn);
    return new Symbol(sym.MAYOR, yyline, yycolumn);
}
{upatree}                {
    symbolTable.addSymbol(yytext(), "MAYOR_IGUAL", yyline, yycolumn);
    return new Symbol(sym.MAYOR_IGUAL, yyline, yycolumn);
}
{mary}                   {
    symbolTable.addSymbol(yytext(), "IGUAL", yyline, yycolumn);
    return new Symbol(sym.IGUAL, yyline, yycolumn);
}
{openslae}               {
    symbolTable.addSymbol(yytext(), "DIFERENTE", yyline, yycolumn);
    return new Symbol(sym.DIFERENTE, yyline, yycolumn);
}

/* --- Sección de operadores lógicos --- */
{melchor}                {
    symbolTable.addSymbol(yytext(), "AND", yyline, yycolumn);
    return new Symbol(sym.AND, yyline, yycolumn);
}

{gaspar}                 {
    symbolTable.addSymbol(yytext(), "OR", yyline, yycolumn);
    return new Symbol(sym.OR, yyline, yycolumn);
}

{baltazar}               {
    symbolTable.addSymbol(yytext(), "NOT", yyline, yycolumn);
    return new Symbol(sym.NOT, yyline, yycolumn);
}

/* --- Sección de otros tokens especiales --- */
{narra}                  {
    symbolTable.addSymbol(yytext(), "PRINT", yyline, yycolumn);
    return new Symbol(sym.PRINT, yyline, yycolumn);
}

{abreempaque}            {
    symbolTable.addSymbol(yytext(), "CORCHETE_ABRE", yyline, yycolumn);
    return new Symbol(sym.CORCHETE_ABRE, yyline, yycolumn);
}

{cierraempaque}          {
    symbolTable.addSymbol(yytext(), "CORCHETE_CIERRA", yyline, yycolumn);
    return new Symbol(sym.CORCHETE_CIERRA, yyline, yycolumn);
}

{_verano_}               {
    symbolTable.addSymbol(yytext(), "MAIN", yyline, yycolumn);
    return new Symbol(sym.MAIN, yyline, yycolumn);
}

{escucha}                {
    symbolTable.addSymbol(yytext(), "READ", yyline, yycolumn);
    return new Symbol(sym.READ, yyline, yycolumn);
}

/* --- Sección de literales y valores --- */
{entero}                 {
    symbolTable.addSymbol(yytext(), "LIT_ENTERO", yyline, yycolumn);
    return new Symbol(sym.LIT_ENTERO, yyline, yycolumn, Integer.valueOf(yytext()));
}

{flotante}               {
    symbolTable.addSymbol(yytext(), "LIT_FLOTANTE", yyline, yycolumn);
    return new Symbol(sym.LIT_FLOTANTE, yyline, yycolumn, Float.valueOf(yytext()));
}

{cadena}                 {
    symbolTable.addSymbol(yytext(), "LIT_CADENA", yyline, yycolumn);
    return new Symbol(sym.LIT_CADENA, yyline, yycolumn, yytext());
}

{caracter}               {
   symbolTable.addSymbol(yytext(), "LITERAL_CHAR", yyline, yycolumn);
   return new Symbol(sym.LIT_CHAR, yyline, yycolumn);
}

{booleano}               {
    symbolTable.addSymbol(yytext(), "LIT_BOOL", yyline, yycolumn);
    return new Symbol(sym.LIT_BOOL, yyline, yycolumn);
}

{abrecuento}             {
    symbolTable.addSymbol(yytext(), "LLAVE_ABRE", yyline, yycolumn);
    return new Symbol(sym.LLAVE_ABRE, yyline, yycolumn, yytext());
}

{cierracuento}             {
    symbolTable.addSymbol(yytext(), "LLAVE_CIERRA", yyline, yycolumn);
    return new Symbol(sym.LLAVE_CIERRA, yyline, yycolumn, yytext());
}

{abreregalo}             {
    symbolTable.addSymbol(yytext(), "PARENTESIS_ABRE", yyline, yycolumn);
    return new Symbol(sym.PARENTESIS_ABRE, yyline, yycolumn, yytext());
}

{cierraregalo}             {
    symbolTable.addSymbol(yytext(), "PARENTESIS_CIERRA", yyline, yycolumn);
    return new Symbol(sym.PARENTESIS_CIERRA, yyline, yycolumn, yytext());
}

{finregalo}                {
    symbolTable.addSymbol(yytext(), "FIN_EXPRESION", yyline, yycolumn);
    return new Symbol(sym.FIN_EXPRESION, yyline, yycolumn, yytext());
}

/* --- Sección de identificadores --- */
{identificador}          {
    symbolTable.addSymbol(yytext(), "IDENTIFICADOR", yyline, yycolumn);
    return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext());
}

/* Manejo de errores */
[^]                      {
    System.err.println("Error léxico: Carácter no reconocido '" + yytext() +
                       "' en línea " + (yyline+1) + ", columna " + (yycolumn+1));
    return new Symbol(sym.ERROR, yyline, yycolumn, yytext());
}