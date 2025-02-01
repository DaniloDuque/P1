package org.example.node;

import org.example.generator.ASTVisitor;

public class AssignNode extends ASTNode {
    private ASTNode left;
    private ASTNode right;

    public AssignNode(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}

