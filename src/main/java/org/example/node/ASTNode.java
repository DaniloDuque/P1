package org.example.node;

import org.example.generator.ASTVisitor;

public interface ASTNode {
    String accept(ASTVisitor visitor);
}
