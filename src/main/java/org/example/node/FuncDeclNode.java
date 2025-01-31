package org.example.node;

import org.example.generator.ASTVisitor;

public class FuncDeclNode extends ASTNode {
    private String returnType; // Tipo de retorno de la función (ej: "int", "float")
    private String functionName; // Nombre de la función
    private ASTNode parameters; // Lista de parámetros (ParamListNode)
    private ASTNode body; // Cuerpo de la función (StatementsNode o similar)

    public FuncDeclNode(String returnType, String functionName, ASTNode parameters, ASTNode body) {
        this.returnType = returnType;
        this.functionName = functionName;
        this.parameters = parameters;
        this.body = body;
    }

    // Getters
    public String getReturnType() {
        return returnType;
    }

    public String getFunctionName() {
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