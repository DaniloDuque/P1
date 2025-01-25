package org.example.table;

public class SymbolInfo {
    private String lexema;
    private String tipo;
    private int scope; // Alcance del símbolo
    private int linea;
    private int columna;

    // Constructor
    public SymbolInfo(String lexema, String tipo, int scope, int linea, int columna) {
        this.lexema = lexema;
        this.tipo = tipo;
        this.scope = scope;
        this.linea = linea;
        this.columna = columna;
    }

    // Constructor
    public SymbolInfo(String lexema, String tipo, int linea, int columna) {
        this.lexema = lexema;
        this.tipo = tipo;
        this.scope = 0;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "SymbolInfo{" +
                "lexema='" + lexema + '\'' +
                ", tipo='" + tipo + '\'' +
                ", scope='" + scope + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                '}';
    }

    public String getLexema() {
        return lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public int getScope() {
        return scope;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}