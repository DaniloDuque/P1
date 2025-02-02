package org.example.node;

import org.example.generator.ASTVisitor;

public class IdentifierNode implements ASTNode {
    private String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return (name != null) ? name : "";
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}