package org.example.node;

import org.example.generator.ASTVisitor;

public class TypeNode extends ASTNode {
    private String type;

    public TypeNode(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return "";
    }
}