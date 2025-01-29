package org.example.node;

import org.example.generator.ASTVisitor;

public class ArrayDeclNode extends ASTNode {
    private String arrayName;  // Nombre del arreglo
    private int arraySize;     // Tamaño del arreglo
    private String arrayType;  // Tipo del arreglo (ENTERO, FLOTANTE, etc.)

    public ArrayDeclNode(String arrayName, int arraySize, String arrayType) {
        this.arrayName = arrayName;
        this.arraySize = arraySize;
        this.arrayType = arrayType;
    }

    public String getArrayName() {
        return arrayName;
    }

    public int getArraySize() {
        return arraySize;
    }

    public String getArrayType() {
        return arrayType;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}