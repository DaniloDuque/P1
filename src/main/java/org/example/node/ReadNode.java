package org.example.node;

import org.example.generator.ASTVisitor;

public class ReadNode extends ASTNode {
    @Override
    public String accept(ASTVisitor visitor) {
        return "";
    }
}