package org.example.node;

import org.example.generator.ASTVisitor;

public class LiteralNode extends ASTNode {
    private String value;  // El valor del literal, por ejemplo, "42" o "true"

    public LiteralNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
