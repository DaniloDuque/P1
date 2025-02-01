package org.example.node;

import org.example.generator.ASTVisitor;
import java.util.List;
import java.util.ArrayList;

public class StatementsNode implements ASTNode {
    private List<ASTNode> statements;

    public StatementsNode(ASTNode statement, ASTNode statementsNode) {
        this.statements = new ArrayList<>();
        StatementsNode node = (StatementsNode) statementsNode;
        this.statements.add(statement);
        if (statementsNode != null) {
            this.statements.addAll(node.getStatements());
        }
    }

    public List<ASTNode> getStatements() {
        return statements;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
