package org.example.node;

import org.example.generator.ASTVisitor;

import java.util.List;

public class FuncDeclNode extends ASTNode {
    private String functionName;
    private List<String> parameters;
    private ASTNode body;

    public FuncDeclNode(String functionName, List<String> parameters, ASTNode body) {
        this.functionName = functionName;
        this.parameters = parameters;
        this.body = body;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public ASTNode getBody() {
        return body;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
