package org.example.node;

import org.example.generator.ASTVisitor;

public class AssignNode extends ASTNode {
    private ASTNode left;  // Nodo a la izquierda del "=" (variable o acceso a arreglo)
    private ASTNode right; // Nodo a la derecha del "=" (expresión a evaluar)

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

