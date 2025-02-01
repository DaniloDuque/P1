package org.example.node;

import org.example.generator.ASTVisitor;

public class ArrayDeclNode implements ASTNode {
    private ASTNode type;  // Nombre del arreglo
    private ASTNode name;     // Tamaño del arreglo
    private ASTNode opt;  // Tipo del arreglo (ENTERO, FLOTANTE, etc.)
    private Boolean el;

    public ArrayDeclNode(ASTNode type, ASTNode name, Boolean el, ASTNode opt) {
        this.type = type;
        this.name = name;
        this.opt = opt;
        this.el = el;
    }

    public ASTNode getArrayName() {
        return name;
    }

    public ASTNode getArrayType() {
        return type;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }

}