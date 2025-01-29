package org.example.node;

import org.example.generator.ASTVisitor;

public class IfNode extends ASTNode {
    private ASTNode condition;  // La condición que se evalúa
    private ASTNode thenBlock;  // El bloque 'then' (si la condición es verdadera)
    private ASTNode elseBlock;  // El bloque 'else' (si la condición es falsa)

    public IfNode(ASTNode condition, ASTNode thenBlock, ASTNode elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getThenBlock() {
        return thenBlock;
    }

    public ASTNode getElseBlock() {
        return elseBlock;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
