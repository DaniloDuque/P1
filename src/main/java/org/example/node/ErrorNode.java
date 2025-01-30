package org.example.node;

import org.example.generator.ASTVisitor;
import org.example.node.ASTNode;

public class ErrorNode extends ASTNode {
    private String errorMessage;

    public ErrorNode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
