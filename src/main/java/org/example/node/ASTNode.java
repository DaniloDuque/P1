package org.example.node;

import org.example.generator.ASTVisitor;

public abstract class ASTNode {
    public abstract void accept(ASTVisitor visitor);
}
