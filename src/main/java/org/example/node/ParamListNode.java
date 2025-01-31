package org.example.node;

import org.example.generator.ASTVisitor;

import java.util.List;

public class ParamListNode extends ASTNode {
    private List<ASTNode> params; // Lista de parámetros (cada uno es un ParamNode)

    public ParamListNode(List<ASTNode> params) {
        this.params = params;
    }

    // Getters
    public List<ASTNode> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "ParamListNode{" +
                "params=" + params +
                '}';
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}