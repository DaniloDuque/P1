package org.example.node;

import org.example.generator.ASTVisitor;

public class ReturnNode implements ASTNode {
    private ASTNode returnValue;  // El valor que se retorna

    public ReturnNode(ASTNode returnValue) {
        this.returnValue = returnValue;
    }

    public ASTNode getReturnValue() {
        return returnValue;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
