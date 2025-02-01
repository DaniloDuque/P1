package org.example.node;

import org.example.generator.ASTVisitor;

import java.util.concurrent.atomic.AtomicStampedReference;

public class FuncDeclsNode implements ASTNode {
    private ASTNode funcDecl; // Current function declaration
    private ASTNode nextFuncDecls; // Next list of function declarations

    // Constructor for a single function declaration
    public FuncDeclsNode(ASTNode funcDecl) {
        this(funcDecl, null);
    }

    // Constructor for a function declaration with a next list
    public FuncDeclsNode(ASTNode funcDecl, ASTNode nextFuncDecls) {
        this.funcDecl = funcDecl;
        this.nextFuncDecls = nextFuncDecls;
    }

    // Getters
    public ASTNode getFuncDecl() {
        return funcDecl;
    }

    public ASTNode getNextFuncDecls() {
        return nextFuncDecls;
    }

    @Override
    public String toString() {
        return "FuncDeclsNode{" +
                "funcDecl=" + funcDecl +
                ", nextFuncDecls=" + nextFuncDecls +
                '}';
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}