package org.example.node;

import org.example.generator.ASTVisitor;

public class ArithmeticExprNode implements ASTNode {

    public ASTNode left;
    public String operator;
    public ASTNode right;

    public ArithmeticExprNode(ASTNode left, String operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
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
