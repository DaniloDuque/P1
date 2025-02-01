package org.example.node;

import org.example.generator.ASTVisitor;

public class ForNode extends ASTNode {

    private ASTNode initialization;   // Inicialización del bucle
    private ASTNode condition;        // Condición del bucle
    private ASTNode update;           // Actualización de la variable de control
    private ASTNode body;             // Cuerpo del bucle

    public ForNode(ASTNode initialization, ASTNode condition, ASTNode update, ASTNode body) {
        this.initialization = initialization;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }

    public ASTNode getInitialization() {
        return initialization;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getUpdate() {
        return update;
    }

    public ASTNode getBody() {
        return body;
    }

    @Override
    public String accept(ASTVisitor visitor) {
        return visitor.visit(this);
    }

}
