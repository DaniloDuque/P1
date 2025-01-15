package org.example;

import java.util.*;

public class SymbolTable {
    private Map<String, List<SymbolInfo>> table = new HashMap<>();

    public void addSymbol(String lexema, String tipo, int linea, int columna) {
        SymbolInfo symbolInfo = new SymbolInfo(lexema, tipo, linea, columna);
        table.computeIfAbsent(lexema, k -> new ArrayList<>()).add(symbolInfo);
    }

    public List<SymbolInfo> lookup(String lexema) {
        return table.getOrDefault(lexema, Collections.emptyList());
    }

    public Map<String, List<SymbolInfo>> getSymbols() {
        return table;
    }
}