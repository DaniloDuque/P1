package org.example.generator;

import org.example.lexer.DataSegment;
import org.example.node.*;
import org.example.register.MIPSRegisterAllocator;
import org.example.register.RegisterAllocator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MIPSCodeGenerator implements ASTVisitor {

    private int labelCounter = 0; // For generating unique labels
    private BufferedWriter writer; // For writing MIPS code to a file
    private RegisterAllocator registerAllocator = new MIPSRegisterAllocator(); // For managing registers
    private DataSegment dataSegment;

    public MIPSCodeGenerator(DataSegment dataSegment) {
        this.dataSegment = dataSegment;
    }

    // Helper method to generate a unique label
    private String generateLabel(String prefix) {
        return prefix + "_" + (labelCounter++);
    }

    // Helper method to write MIPS code to the output file
    private void emit(String code) {
        try {
            writer.write(code + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String visit(ProgramNode node) {
        // Initialize the output file
        try {
            writer = new BufferedWriter(new FileWriter("output.asm"));
        } catch (IOException e) {
            System.err.println("Error: Could not open output file.");
            e.printStackTrace();
            return null;
        }

        // Emit the .data section
        emit(".data");
        emit("newline: .asciiz \"\\n\"");
        emit(dataSegment.toString());
        // For printing newlines

        // Emit the .text section
        emit(".text");
        emit(".globl main");

        // Visit function declarations
        if (node.getGlobalDeclarations() != null) {
            visit((FuncDeclsNode) node.getGlobalDeclarations());
        }

        // Visit the main function
        emit("main:");
        visit((StatementsNode) node.getMainFunction());

        // Exit the program
        emit("li $v0, 10"); // syscall for exit
        emit("syscall");

        // Close the writer
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Error: Could not close output file.");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String visit(FuncDeclNode node) {
        String functionName = node.getFunctionName().toString();
        emit(functionName + ":");

        // Prologue: Save $ra, $fp, and callee-saved registers
        emit("addiu $sp, $sp, -40"); // Adjust stack pointer for $ra, $fp, and $s0-$s7
        emit("sw $ra, 36($sp)");
        emit("sw $fp, 32($sp)");
        for (int i = 0; i < 8; i++) {
            emit("sw $s" + i + ", " + (i * 4) + "($sp)");
        }
        emit("move $fp, $sp");

        // Visit the function body
        visit((StatementsNode) node.getBody());

        // Epilogue: Restore $ra, $fp, and callee-saved registers
        for (int i = 0; i < 8; i++) {
            emit("lw $s" + i + ", " + (i * 4) + "($sp)");
        }
        emit("lw $fp, 32($sp)");
        emit("lw $ra, 36($sp)");
        emit("addiu $sp, $sp, 40"); // Restore stack pointer
        emit("jr $ra");

        return null;
    }

    @Override
    public String visit(FuncDeclsNode node) {
        // Visit the current function declaration
        if (node.getFuncDecl() != null) {
            visit((FuncDeclNode) node.getFuncDecl());
        }

        // Visit the next function declarations (if any)
        if (node.getNextFuncDecls() != null) {
            visit((FuncDeclsNode) node.getNextFuncDecls());
        }

        return null;
    }

    @Override
    public String visit(FuncCallNode node) {
        String functionName = node.getFunctionName().toString();

        // Save caller-saved registers
        emit("addiu $sp, $sp, -40");
        for (int i = 0; i < 10; i++) {
            emit("sw $t" + i + ", " + (i * 4) + "($sp)");
        }

        // Pass arguments
        if (node.getArguments() != null) {
            List<ASTNode> args = ((ArgListNode) node.getArguments()).getArguments();
            for (int i = 0; i < args.size(); i++) {
                String argRegister = args.get(i).accept(this);
                if (argRegister == null) continue;

                if (i < 4) {
                    if (argRegister.startsWith("$f")) { // Floating-point argument
                        emit("mov.s $f" + (12 + i) + ", " + argRegister);
                    } else { // Integer argument
                        emit("move $a" + i + ", " + argRegister);
                    }
                } else {
                    emit("sw " + argRegister + ", " + ((i - 4) * 4) + "($sp)");
                }
                registerAllocator.freeRegister(argRegister);
            }
        }

        // Call the function
        emit("jal " + functionName);

        // Restore caller-saved registers
        for (int i = 0; i < 10; i++) {
            emit("lw $t" + i + ", " + (i * 4) + "($sp)");
        }
        emit("addiu $sp, $sp, 40");

        return null;
    }

    @Override
    public String visit(LiteralNode node) {
        String type = node.getType();
        String value = node.getValue();
        String resultReg = registerAllocator.allocateRegister();

        switch (type) {
            case "int":
            case "bool":
                emit("li " + resultReg + ", " + value);
                break;
            case "float":
                String floatLabel = generateLabel("float_literal");
                // emit(floatLabel + ": .float " + value);
                emit("lwc1 $f0, " + floatLabel);
                emit("mfc1 " + resultReg + ", $f0");
                break;
            case "char":
                emit("li " + resultReg + ", " + (int) value.charAt(0));
                break;
            case "string":
                String stringLabel = generateLabel("string_literal");
                emit("la " + resultReg + ", " + stringLabel);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported literal type: " + type);
        }

        return resultReg;
    }

    @Override
    public String visit(ParamListNode node) {
        for (ASTNode n : node.getParams()) {
            visit((ParamNode) n);
        }
        return null;
    }

    @Override
    public String visit(ParamNode node) {
        emit("# Parameter: " + node.getParamName() + " (" + node.getParamType() + ")");
        return null;
    }

    @Override
    public String visit(TypeNode node) {
        return null;
    }

    @Override
    public String visit(IdentifierNode node) {
        String identifierName = node.getName();
        String resultReg = registerAllocator.allocateRegister();
        emit("la $t0, " + identifierName);
        emit("lw " + resultReg + ", 0($t0)");
        return resultReg;
    }

    @Override
    public String visit(ArithmeticExprNode node) {
        String leftRegister = node.getLeft() != null ? node.getLeft().accept(this) : null;
        String rightRegister = node.getRight() != null ? node.getRight().accept(this) : null;
        String resultRegister = registerAllocator.allocateRegister();

        switch (node.getOperator()) {
            case "+":
                emit("add " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            case "-":
                if (node.getLeft() == null) {
                    emit("sub " + resultRegister + ", $zero, " + rightRegister);
                } else {
                    emit("sub " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                }
                break;
            case "*":
                emit("mult " + leftRegister + ", " + rightRegister);
                emit("mflo " + resultRegister);
                break;
            case "/":
                emit("div " + leftRegister + ", " + rightRegister);
                emit("mflo " + resultRegister);
                break;
            case "++":
                if (node.getLeft() != null) {
                    emit("addi " + leftRegister + ", " + leftRegister + ", 1");
                    emit("move " + resultRegister + ", " + leftRegister);
                } else if (node.getRight() != null) {
                    emit("addi " + rightRegister + ", " + rightRegister + ", 1");
                    emit("move " + resultRegister + ", " + rightRegister);
                } else {
                    throw new IllegalArgumentException("Invalid increment operator usage");
                }
                break;
            case "--":
                if (node.getLeft() != null) {
                    emit("addi " + leftRegister + ", " + leftRegister + ", -1");
                    emit("move " + resultRegister + ", " + leftRegister);
                } else if (node.getRight() != null) {
                    emit("addi " + rightRegister + ", " + rightRegister + ", -1");
                    emit("move " + resultRegister + ", " + rightRegister);
                } else {
                    throw new IllegalArgumentException("Invalid decrement operator usage");
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + node.getOperator());
        }

        if (leftRegister != null) registerAllocator.freeRegister(leftRegister);
        if (rightRegister != null) registerAllocator.freeRegister(rightRegister);

        return resultRegister;
    }

    @Override
    public String visit(LogicalExprNode node) {
        String leftRegister = node.getLeft().accept(this);
        String rightRegister = node.getRight().accept(this);
        String resultRegister = registerAllocator.allocateRegister();

        switch (node.getOperator()) {
            case "&&":
                emit("and " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            case "||":
                emit("or " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            default:
                throw new IllegalArgumentException("Unsupported logical operator: " + node.getOperator());
        }

        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);

        return resultRegister;
    }

    @Override
    public String visit(RelationalExprNode node) {
        String leftRegister = node.getLeft().accept(this);
        String rightRegister = node.getRight().accept(this);
        String resultRegister = registerAllocator.allocateRegister();

        switch (node.getOperator()) {
            case "==":
                emit("seq " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            case "!=":
                emit("sne " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            case "<":
                emit("slt " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            case "<=":
                emit("sle " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            case ">":
                emit("sgt " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            case ">=":
                emit("sge " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            default:
                throw new IllegalArgumentException("Unsupported relational operator: " + node.getOperator());
        }

        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);

        return resultRegister;
    }

    @Override
    public String visit(ArrayAccessNode node) {
        String arrayName = node.getArray().toString();
        String indexRegister = node.getIndex().accept(this);
        String resultRegister = registerAllocator.allocateRegister();

        emit("sll $t0, " + indexRegister + ", 2");
        emit("la $t1, " + arrayName);
        emit("add $t0, $t1, $t0");
        emit("lw " + resultRegister + ", 0($t0)");

        registerAllocator.freeRegister(indexRegister);
        return resultRegister;
    }

    @Override
    public String visit(ArrayDeclNode node) {
        String arrayName = node.getArrayName().toString();
        int size = 100; // Default size for simplicity

        return null;
    }

    @Override
    public String visit(AssignNode node) {
        String lhs = node.getLeft().accept(this);
        String rhs = node.getRight().accept(this);

        emit("sw " + rhs + ", 0(" + lhs + ")");
        registerAllocator.freeRegister(lhs);
        registerAllocator.freeRegister(rhs);

        return null;
    }

    @Override
    public String visit(BreakNode node) {
        String endLabel = generateLabel("end_loop");
        emit("j " + endLabel);
        return null;
    }

    @Override
    public String visit(ElementListNode node) {
        for (ASTNode element : node.getElements()) {
            element.accept(this);
        }
        return null;
    }

    @Override
    public String visit(ErrorNode node) {
        emit(".data");
        emit("error_msg: .asciiz \"" + node.getErrorMessage() + "\"");
        emit(".text");
        emit("li $v0, 4");
        emit("la $a0, error_msg");
        emit("syscall");
        emit("li $v0, 10");
        emit("syscall");
        return null;
    }

    @Override
    public String visit(ForNode node) {
        String startLabel = generateLabel("start_loop");
        String endLabel = generateLabel("end_loop");

        if (node.getInitialization() != null) {
            visit((AssignNode) node.getInitialization());
        }

        emit(startLabel + ":");

        if (node.getCondition() != null) {
            String conditionRegister = node.getCondition().accept(this);
            emit("beqz " + conditionRegister + ", " + endLabel);
            registerAllocator.freeRegister(conditionRegister);
        }

        visit((StatementsNode) node.getBody());

        if (node.getUpdate() != null) {
            visit((ArithmeticExprNode) node.getUpdate());
        }

        emit("j " + startLabel);
        emit(endLabel + ":");

        return null;
    }

    @Override
    public String visit(IfNode node) {
        String elseLabel = generateLabel("else");
        String endLabel = generateLabel("end_if");

        String conditionRegister = node.getCondition().accept(this);
        emit("beqz " + conditionRegister + ", " + elseLabel);
        registerAllocator.freeRegister(conditionRegister);

        visit((StatementsNode) node.getThenBlock());
        emit("j " + endLabel);

        emit(elseLabel + ":");
        if (node.getElseBlock() != null) {
            visit((StatementsNode) node.getElseBlock());
        }

        emit(endLabel + ":");
        return null;
    }

    @Override
    public String visit(ReturnNode node) {
        if (node.getReturnValue() != null) {
            String resultRegister = node.getReturnValue().accept(this);
            emit("move $v0, " + resultRegister);
            registerAllocator.freeRegister(resultRegister);
        }

        emit("j " + generateLabel("function_epilogue"));
        return null;
    }

    @Override
    public String visit(VarDeclNode node) {
        String varName = node.getVarName().toString();
        String varType = node.getVarType().toString();

        emit(".data");
        emit(varName + ": .space 4");
        emit(".text");

        return null;
    }

    @Override
    public String visit(WhileNode node) {
        String startLabel = generateLabel("start_loop");
        String endLabel = generateLabel("end_loop");

        emit(startLabel + ":");
        String conditionRegister = node.getCondition().accept(this);
        emit("beqz " + conditionRegister + ", " + endLabel);
        registerAllocator.freeRegister(conditionRegister);

        visit((StatementsNode) node.getBody());
        emit("j " + startLabel);

        emit(endLabel + ":");
        return null;
    }

    @Override
    public String visit(ArgListNode node) {
        for (ASTNode arg : node.getArguments()) {
            arg.accept(this);
        }
        return null;
    }

    @Override
    public String visit(PrintNode node) {
        String resultRegister = node.getExpression().accept(this);
        emit("li $v0, 1");
        emit("move $a0, " + resultRegister);
        emit("syscall");
        registerAllocator.freeRegister(resultRegister);
        return null;
    }

    @Override
    public String visit(ReadNode node) {
        String varName = node.getIdentifier().toString();
        emit("li $v0, 5");
        emit("syscall");
        emit("sw $v0, " + varName);
        return null;
    }

    @Override
    public String visit(StatementsNode node) {
        for (ASTNode stmt : node.getStatements()) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public void write(String code) {
        emit(code);
    }
}