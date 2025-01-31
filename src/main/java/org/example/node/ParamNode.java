package org.example.node;

import org.example.generator.ASTVisitor;

public class ParamNode extends ASTNode {
    private String paramName; // Nombre del parámetro
    private String paramType; // Tipo del parámetro (ej: "int", "float")

    public ParamNode(String paramType, String paramName) {
        this.paramType = paramType;
        this.paramName = paramName;
    }

    // Getters
    public String getParamName() {
        return paramName;
    }

    public String getParamType() {
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