package org.example.node;

import org.example.generator.ASTVisitor;

public class FuncDeclNode extends ASTNode {
    private ASTNode returnType; // Tipo de retorno de la función (ej: "int", "float")
    private ASTNode functionName; // Nombre de la función
    private ASTNode parameters; // Lista de parámetros (ParamListNode)
    private ASTNode body; // Cuerpo de la función (StatementsNode o similar)

    public FuncDeclNode(ASTNode returnType, ASTNode functionName, ASTNode parameters, ASTNode body) {
        this.returnType = returnType;
        this.functionName = functionName;
        this.parameters = parameters;
        this.body = body;
    }

    // Getters
    public ASTNode getReturnType() {
        return returnType;
    }

    public ASTNode getFunctionName() {
        return functionName;
    }

    public ASTNode getParameters() {
        return parameters;
    }

    public ASTNode getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "FuncDeclNode{" +
                "returnType='" + returnType + '\'' +
                ", functionName='" + functionName + '\'' +
                ", parameters=" + parameters +
                ", body=" + body +
                '}';
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}