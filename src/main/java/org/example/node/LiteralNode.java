package org.example.node;

import org.example.generator.ASTVisitor;

public class LiteralNode extends ASTNode {
    private String type, value;

    public LiteralNode(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getType(){ return type;}

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
