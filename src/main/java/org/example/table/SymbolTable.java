package org.example.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class SymbolTable {
    private final Map<String, List<SymbolInfo>> symbols = new HashMap<>();

    // Add a symbol to the table
    public void addSymbol(String lexema, String tipo, int scope, int linea, int columna) {
        SymbolInfo info = new SymbolInfo(lexema, tipo, scope, linea, columna);
        symbols.computeIfAbsent(lexema, k -> new ArrayList<>()).add(info);
    }

    // Check if a symbol is declared in the current or outer scopes
    public boolean isDeclared(String lexema, int currentScope) {
        List<SymbolInfo> infos = symbols.get(lexema);
        if (infos == null) return false;
        for (SymbolInfo info : infos) {
            if (info.getScope() <= currentScope) {
                return true;
            }
        }
        return false;
    }

    // Get the type of a symbol in the current or outer scopes
    public String getType(String lexema, int currentScope) {
        List<SymbolInfo> infos = symbols.get(lexema);
        if (infos == null) return null;
        for (SymbolInfo info : infos) {
            if (info.getScope() <= currentScope) {
                return info.getTipo();
            }
        }
        return null;
    }

    // Get all symbols in the table
    public Map<String, List<SymbolInfo>> getSymbols() {
        return symbols;
    }
}