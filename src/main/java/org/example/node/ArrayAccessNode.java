package org.example.node;

import org.example.generator.ASTVisitor;
import org.example.node.ASTNode;

public class ArrayAccessNode implements ASTNode {
    private ASTNode array;
    private ASTNode index;

    public ArrayAccessNode(ASTNode array, ASTNode index) {
        this.array = array;
        this.index = index;
    }

    public ASTNode getArray() {
        return array;
    }

    public ASTNode getIndex() {
        return index;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }

}