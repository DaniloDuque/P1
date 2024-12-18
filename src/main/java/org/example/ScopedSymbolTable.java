package org.example;

import java.util.Stack;

public class ScopedSymbolTable {

    private Stack<SymbolTable> scopes;

    public ScopedSymbolTable() {
        scopes = new Stack<>();
        scopes.push(new SymbolTable());
    }

    public void enterScope() {
        scopes.push(new SymbolTable());
    }

    public void exitScope() {
        if(!scopes.isEmpty()) {
            scopes.pop();
        }
        else {
            System.err.println("Error: No scope to exit");
        }
    }

    public void insert(String name, SymbolInfo info) {
        scopes.peek().insert(name, info);
    }

    public SymbolInfo lookup(String name) {
        for(int i = scopes.size() - 1; i >= 0; i--) {
            SymbolInfo info = scopes.get(i).lookup(name).get(0);
            if(info != null) {
                return info;
            }
        }
        return null;
    }

    public boolean exists(String name) {
        return lookup(name) != null;
    }


}
