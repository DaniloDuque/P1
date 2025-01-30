package org.example.node;

import org.example.generator.ASTVisitor;

public class WhileNode extends ASTNode {
    private ASTNode condition;  // La condición booleana del while
    private ASTNode body;  // El cuerpo del bucle (nodo que contiene las instrucciones a ejecutar)

    public WhileNode(ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getBody() {
        return body;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
