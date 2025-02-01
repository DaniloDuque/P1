package org.example.node;

import org.example.generator.ASTVisitor;

public class ReadNode extends ASTNode {
    private String identifier;

    public ReadNode(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}