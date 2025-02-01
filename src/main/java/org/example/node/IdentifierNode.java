package org.example.node;

import org.example.generator.ASTVisitor;

public class IdentifierNode extends ASTNode {
    private String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "IdentifierNode(" + name + ")";
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}