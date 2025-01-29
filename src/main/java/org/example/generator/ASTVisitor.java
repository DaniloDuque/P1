package org.example.generator;

import org.example.node.*;

import java.io.File;

public interface ASTVisitor {
    void visit(ArithmeticExprNode node);
    void visit(ArrayAccessNode node);
    void visit(ArrayDeclNode node);
    void visit(AssignNode node);
    void visit(ErrorNode node);
    void visit(ForNode node);
    void visit(FuncDeclNode node);
    void visit(FuncExecNode node);
    void visit(FunctionCallNode node);
    void visit(IfNode node);
    void visit(LiteralNode node);
    void visit(LogicalExprNode node);
    void visit(RelationalExprNode node);
    void visit(ReturnNode node);
    void visit(SwitchNode node);
    void visit(SwitchCaseNode node);
    void visit(VarDeclNode node);
    void visit(WhileNode node);
    void write(File file, String code);
}
