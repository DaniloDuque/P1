package org.example.node;

import org.example.generator.ASTVisitor;

public class ReturnNode extends ASTNode {
    private ASTNode returnValue;  // El valor que se retorna

    public ReturnNode(ASTNode returnValue) {
        this.returnValue = returnValue;
    }

    public ASTNode getReturnValue() {
        return returnValue;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
