

package org.example.node;

import org.example.generator.ASTVisitor;

import java.util.List;

public class ProgramNode extends ASTNode {
    private List<ASTNode> globalDeclarations; // List of global declarations (e.g., functions, variables)
    private ASTNode mainFunction; // The main function block

    public ProgramNode(List<ASTNode> globalDeclarations, ASTNode mainFunction) {
        this.globalDeclarations = globalDeclarations;
        this.mainFunction = mainFunction;
    }

    public List<ASTNode> getGlobalDeclarations() {
        return globalDeclarations;
    }

    public ASTNode getMainFunction() {
        return mainFunction;
    }

    @Override
    public String toString() {
        return "ProgramNode(" +
                "globalDeclarations=" + globalDeclarations +
                ", mainFunction=" + mainFunction +
                ")";
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }

}
