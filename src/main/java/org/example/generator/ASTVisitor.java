package org.example.generator;

import org.example.node.*;

import java.io.File;

public interface ASTVisitor {
    String visit(ArithmeticExprNode node);
    String visit(ArrayAccessNode node);
    String visit(ArrayDeclNode node);
    String visit(AssignNode node);
    String visit(ErrorNode node);
    String visit(ForNode node);
    String visit(FuncDeclNode node);
    String visit(FuncExecNode node);
    String visit(FunctionCallNode node);
    String visit(IfNode node);
    String visit(LiteralNode node);
    String visit(LogicalExprNode node);
    String visit(RelationalExprNode node);
    String visit(ReturnNode node);
    String visit(SwitchNode node);
    String visit(SwitchCaseNode node);
    String visit(VarDeclNode node);
    String visit(WhileNode node);
    String visit(ProgramNode node);
    void write(String code);
}
