package org.example.node;

import org.example.generator.ASTVisitor;

public class RelationalExprNode implements ASTNode {
    private ASTNode left;      // Operando izquierdo
    private ASTNode right;     // Operando derecho
    private String operator;   // Operador relacional (==, !=, <, <=, >, >=)

    public RelationalExprNode(ASTNode right, String operator, ASTNode left) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
