package org.example;

public class SymbolInfo {
    private final String type;
    private final int line;
    private final int column;
    private Object value;

    public SymbolInfo(String type, int line, int column, Object value) {
        this.type = type;
        this.line = line;
        this.column = column;
        this.value = value;
    }

    @Override
    public String toString() {
        String str = "Tipo de token: " + type + ", Línea: " + line + ", Columna: " + column;
        if(value != null) {str += ", Valor: " + value;}
        return str;
    }
}
