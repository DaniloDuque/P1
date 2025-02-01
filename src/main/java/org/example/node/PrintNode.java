package org.example.node;

import org.example.generator.ASTVisitor;

public class PrintNode implements ASTNode {
    private ASTNode expression;

    public PrintNode(ASTNode expression) {
        this.expression = expression;
    }

    public ASTNode getExpression() {
        return expression;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}

