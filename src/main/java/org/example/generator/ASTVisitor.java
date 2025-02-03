package org.example.generator;

import org.example.node.*;

public interface ASTVisitor {
    String visit(ArithmeticExprNode node);
    String visit(ArrayAccessNode node);
    String visit(ArrayDeclNode node);
    String visit(AssignNode node);
    String visit(ErrorNode node);
    String visit(ForNode node);
    String visit(FuncDeclNode node);
    String visit(FuncCallNode node);
    String visit(IfNode node);
    String visit(LiteralNode node);
    String visit(LogicalExprNode node);
    String visit(RelationalExprNode node);
    String visit(ReturnNode node);
    String visit(VarDeclNode node);
    String visit(WhileNode node);
    String visit(ProgramNode node);
    String visit(ParamListNode node);
    String visit(TypeNode node);
    String visit(IdentifierNode node);
    String visit(ArgListNode node);
    String visit(BreakNode node);
    String visit(ElementListNode node);
    String visit(FuncDeclsNode node);
    String visit(ParamNode node);
    String visit(PrintNode node);
    String visit(ReadNode node);
    String visit(StatementsNode node);
    void write(String code);
}
