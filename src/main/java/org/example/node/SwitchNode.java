package org.example.node;

import org.example.generator.ASTVisitor;

import java.util.List;

public class SwitchNode extends ASTNode {
    private ASTNode expression;  // La expresión que evaluamos en el switch
    private List<SwitchCaseNode> cases;  // Lista de casos del switch

    public SwitchNode(ASTNode expression, List<SwitchCaseNode> cases) {
        this.expression = expression;
        this.cases = cases;
    }

    public ASTNode getExpression() {
        return expression;
    }

    public List<SwitchCaseNode> getCases() {
        return cases;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
