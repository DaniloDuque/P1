package org.example.node;

import org.example.generator.ASTVisitor;

import java.util.List;

public class FunctionCallNode extends ASTNode {
    private String functionName;
    private List<ASTNode> arguments;

    public FunctionCallNode(String functionName, List<ASTNode> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<ASTNode> getArguments() {
        return arguments;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
