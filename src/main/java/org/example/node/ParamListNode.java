package org.example.node;

import org.example.generator.ASTVisitor;
import java.util.List;
import java.util.ArrayList;

public class ParamListNode implements ASTNode {
    private List<ASTNode> params; // List of parameter nodes

    public ParamListNode(List<ASTNode> params) {
        this.params = params;
    }

    public ParamListNode(ASTNode param, ASTNode paramList) {
        this.params = new ArrayList<>();
        this.params.add(param);
        if (paramList != null) {
            this.params.addAll(((ParamListNode) paramList).getParams());
        }
    }

    public List<ASTNode> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "ParamListNode{" + "params=" + params + '}';
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }
}
