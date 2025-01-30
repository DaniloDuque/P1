package org.example.node;

import org.example.generator.ASTVisitor;

import java.util.List;

public class SwitchNode extends ASTNode {
    private ASTNode expression;  // La expresión que evaluamos en el switch
    private List<SwitchCaseNode> cases;  // Lista de casos del switch
    private ASTNode defaultCase;

    public SwitchNode(ASTNode expression, List<SwitchCaseNode> cases) {
        this.expression = expression;
        this.cases = cases;
        for (SwitchCaseNode caseNode : cases) {
            if(caseNode.isDefault()) {
                defaultCase=caseNode;
            }
        }
    }

    public ASTNode getDefaultCase() {
        return defaultCase;
    }

    public ASTNode getExpression() {
        return expression;
    }

    public List<SwitchCaseNode> getCases() {
        return cases;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
