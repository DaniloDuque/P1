package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {

    private Map<String, List<SymbolInfo>> table;

    public SymbolTable() {
        table = new HashMap<>();
    }

    // Método para insertar un nuevo símbolo o agregarlo a la lista si ya existe
    public void insert(String name, SymbolInfo info) {
        if (!table.containsKey(name)) {
            table.put(name, new ArrayList<>());  // Si no existe, creamos una nueva lista
        }
        table.get(name).add(info);  // Agregamos el símbolo a la lista asociada al nombre
    }

    // Método para buscar un símbolo por su nombre
    public List<SymbolInfo> lookup(String name) {
        return table.get(name);  // Devuelve la lista de SymbolInfo asociados al nombre
    }

    // Verifica si un símbolo ya existe en la tabla
    public boolean exists(String name) {
        return table.containsKey(name);
    }

    // Obtiene todos los símbolos almacenados en la tabla
    public Map<String, List<SymbolInfo>> getSymbols() {
        return table;
    }

    // Método para agregar un símbolo a la tabla (con parámetros adicionales)
    public void addSymbol(String yytext, String literal, int line, int column, Object value) {
        SymbolInfo info = new SymbolInfo(literal, line, column, value);
        insert(yytext, info);  // Usamos el método insert para agregarlo
    }
}
