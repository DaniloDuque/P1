
package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class SymbolTable {
    
    private Map<String, SymbolInfo> table;

    public SymbolTable() {
        table = new HashMap<>();
    }

    public void insert(String name, SymbolInfo info) {
        table.put(name, info);
    }

    public SymbolInfo lookup(String name) {
        return table.get(name);
    }

    public boolean exists(String name) {
        return table.containsKey(name);
    }

    public Set<String> getSymbols() {
        return table.keySet();
    }

    public void addSymbol(String yytext, String literalFlotante, Float aFloat) {
        table.put(yytext, new SymbolInfo(literalFlotante, aFloat));
    }
}


