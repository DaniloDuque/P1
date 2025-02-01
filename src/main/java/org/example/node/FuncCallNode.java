package org.example.node;

import org.example.generator.ASTVisitor;

import java.util.List;

public class FuncCallNode extends ASTNode {
    private ASTNode functionName;
    private ASTNode arguments;

    public FuncCallNode(ASTNode functionName, ASTNode arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public ASTNode getFunctionName() {
        return functionName;
    }

    public ASTNode getArguments() {
        return arguments;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
