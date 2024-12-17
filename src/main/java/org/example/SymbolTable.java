
package org.example;

import java.util.HashMap;
import java.util.Map;


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



}


