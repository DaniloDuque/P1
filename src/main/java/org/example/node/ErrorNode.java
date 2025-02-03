package org.example.node;

import org.example.generator.ASTVisitor;
import org.example.node.ASTNode;

public class ErrorNode implements ASTNode {
    private ASTNode errorMessage;

    public ErrorNode() {

    }

    public ASTNode getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
