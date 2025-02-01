package org.example.node;

import org.example.generator.ASTVisitor;

public class BreakNode implements ASTNode {
    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
