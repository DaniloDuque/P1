package org.example.node;

import org.example.generator.ASTVisitor;

public class VarDeclNode implements ASTNode {
    private ASTNode varName;  // Nombre de la variable
    private ASTNode varType;  // Tipo de la variable (por ejemplo, "int")
    private ASTNode initValue;  // Valor inicial (puede ser null si no tiene inicialización)

    public VarDeclNode(ASTNode varName, ASTNode varType, ASTNode initValue) {
        this.varName = varName;
        this.varType = varType;
        this.initValue = initValue;
    }

    public ASTNode getVarName() {
        return varName;
    }

    public ASTNode getVarType() {
        return varType;
    }

    public ASTNode getInitValue() {
        return initValue;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
