package org.example.node;

import org.example.generator.ASTVisitor;
import java.util.ArrayList;
import java.util.List;

public class ElementListNode extends ASTNode {
    private List<ASTNode> elements; // List of elements (expressions)

    // Constructor for a single element
    public ElementListNode(ASTNode element) {
        this.elements = new ArrayList<>();
        this.elements.add(element);
    }

    // Constructor for multiple elements
    public ElementListNode(ASTNode element, ASTNode rest) {
        this(element); // Initialize with the first element
        ElementListNode node = (ElementListNode) rest;
        if (rest != null) {
            this.elements.addAll(node.getElements()); // Add the rest of the elements
        }
    }

    // Getter for the list of elements
    public List<ASTNode> getElements() {
        return elements;
    }

    // Add an element to the list
    public void addElement(ASTNode element) {
        this.elements.add(element);
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
        sb.append("ElementListNode[");
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i));
            if (i < elements.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}