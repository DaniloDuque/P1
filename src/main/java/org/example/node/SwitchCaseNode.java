package org.example.node;

import org.example.generator.ASTVisitor;

import java.util.List;

public class SwitchCaseNode extends ASTNode {
    private int caseValue;  // El valor del caso
    private boolean isDefault;  // Indica si es el caso "default"
    private List<ASTNode> statements;  // Instrucciones dentro del caso

    public SwitchCaseNode(int caseValue, boolean isDefault, List<ASTNode> statements) {
        this.caseValue = caseValue;
        this.isDefault = isDefault;
        this.statements = statements;
    }

    public int getCaseValue() {
        return caseValue;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public List<ASTNode> getStatements() {
        return statements;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
