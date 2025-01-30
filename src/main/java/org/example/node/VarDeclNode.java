package org.example.node;

import org.example.generator.ASTVisitor;

public class VarDeclNode extends ASTNode {
    private String varName;  // Nombre de la variable
    private String varType;  // Tipo de la variable (por ejemplo, "int")
    private ASTNode initValue;  // Valor inicial (puede ser null si no tiene inicialización)

    public VarDeclNode(String varName, String varType, ASTNode initValue) {
        this.varName = varName;
        this.varType = varType;
        this.initValue = initValue;
    }

    public String getVarName() {
        return varName;
    }

    public String getVarType() {
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
