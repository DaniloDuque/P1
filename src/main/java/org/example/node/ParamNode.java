package org.example.node;

import org.example.generator.ASTVisitor;

public class ParamNode implements ASTNode {
    private ASTNode paramName; // Nombre del parámetro
    private ASTNode paramType; // Tipo del parámetro (ej: "int", "float")

    public ParamNode(ASTNode paramType, ASTNode paramName) {
        this.paramType = paramType;
        this.paramName = paramName;
    }

    // Getters
    public ASTNode getParamName() {
        return paramName;
    }

    public ASTNode getParamType() {
        return paramType;
    }

    @Override
    public String toString() {
        return "ParamNode{" +
                "paramName='" + paramName + '\'' +
                ", paramType='" + paramType + '\'' +
                '}';
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}