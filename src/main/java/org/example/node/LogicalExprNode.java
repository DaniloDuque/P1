package org.example.node;

import org.example.generator.ASTVisitor;

public class LogicalExprNode extends ASTNode {
    private ASTNode left;      // Operando izquierdo
    private ASTNode right;     // Operando derecho (en el caso de operaciones binarias)
    private String operator;   // Operador lógico (&&, ||, !)

    public LogicalExprNode(ASTNode left, String operator, ASTNode right) {
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
