package org.example.node;

import org.example.generator.ASTVisitor;
import java.util.ArrayList;
import java.util.List;

public class ArgListNode implements ASTNode {
    private List<ASTNode> arguments; // List of argument expressions

    // Constructor for an empty argument list
    public ArgListNode() {
        this.arguments = new ArrayList<>();
    }

    // Constructor for a single argument
    public ArgListNode(ASTNode argument) {
        this();
        this.arguments.add(argument);
    }

    // Constructor for multiple arguments
    public ArgListNode(ASTNode argument, ASTNode rest) {
        this();
        ArgListNode node = (ArgListNode) rest;
        this.arguments.add(argument);
        if (rest != null) {
            this.arguments.addAll(node.getArguments());
        }
    }

    // Getter for the list of arguments
    public List<ASTNode> getArguments() {
        return arguments;
    }

    // Add an argument to the list
    public void addArgument(ASTNode argument) {
        this.arguments.add(argument);
    }

    // Accept method for the visitor pattern
    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this); // Delegate to the visitor
    }

    // Override toString for debugging
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArgListNode[");
        for (int i = 0; i < arguments.size(); i++) {
            sb.append(arguments.get(i));
            if (i < arguments.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}