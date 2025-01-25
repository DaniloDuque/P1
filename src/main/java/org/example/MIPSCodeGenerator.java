package org.example;

import org.example.node.*;

public class MIPSCodeGenerator implements CodeGenerator {

    @Override
    public String varDeclCode(VarDeclNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String arithmeticExprCode(ArithmeticExprNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String assignmentCode(AssignNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String relationalExprCode(RelationalExprNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String logicalExprCode(LogicalExprNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String ifStatementCode(IfNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String whileStatementCode(WhileNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String forStatementCode(ForNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String switchStatementCode(SwitchNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String functionCallCode(FunctionCallNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String arrayDeclCode(ArrayDeclNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String arrayAccessCode(ArrayAccessNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String literalCode(LiteralNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String funcDeclCode(FuncDeclNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String funcExecutionCode(FuncExecNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String returnCode(ReturnNode node, RegisterAllocator regAllocator) {
        return "";
    }

    @Override
    public String errorCode(ErrorNode node) {
        return "";
    }
}
