package org.example.generator;

import org.example.node.*;
import org.example.register.MIPSRegisterAllocator;
import org.example.register.RegisterAllocator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MIPSCodeGenerator implements ASTVisitor {

    @Override
    public String visit(ArithmeticExprNode node) {
        return "";
    }

    @Override
    public String visit(ArrayAccessNode node) {
        return "";
    }

    @Override
    public String visit(ArrayDeclNode node) {
        return "";
    }

    @Override
    public String visit(AssignNode node) {
        return "";
    }

    @Override
    public String visit(ErrorNode node) {
        return "";
    }

    @Override
    public String visit(ForNode node) {
        return "";
    }

    @Override
    public String visit(FuncDeclNode node) {
        return "";
    }

    @Override
    public String visit(FuncCallNode node) {
        return "";
    }

    @Override
    public String visit(IfNode node) {
        return "";
    }

    @Override
    public String visit(LiteralNode node) {
        return "";
    }

    @Override
    public String visit(LogicalExprNode node) {
        return "";
    }

    @Override
    public String visit(RelationalExprNode node) {
        return "";
    }

    @Override
    public String visit(ReturnNode node) {
        return "";
    }

    @Override
    public String visit(VarDeclNode node) {
        return "";
    }

    @Override
    public String visit(WhileNode node) {
        return "";
    }

    @Override
    public String visit(ProgramNode node) {
        return "";
    }

    @Override
    public String visit(ParamListNode node) {
        return "";
    }

    @Override
    public String visit(TypeNode node) {
        return "";
    }

    @Override
    public String visit(IdentifierNode node) {
        return "";
    }

    @Override
    public String visit(ArgListNode node) {
        return "";
    }

    @Override
    public String visit(BreakNode node) {
        return "";
    }

    @Override
    public String visit(ElementListNode node) {
        return "";
    }

    @Override
    public String visit(FuncDeclsNode node) {
        return "";
    }

    @Override
    public String visit(ParamNode node) {
        return "";
    }

    @Override
    public String visit(PrintNode node) {
        return "";
    }

    @Override
    public String visit(ReadNode node) {
        return "";
    }

    @Override
    public String visit(StatementsNode node) {
        return "";
    }

    @Override
    public void write(String code) {

    }

}