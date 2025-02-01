package org.example.generator;

import org.example.node.*;
import org.example.register.MIPSRegisterAllocator;
import org.example.register.RegisterAllocator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MIPSCodeGenerator implements ASTVisitor {

    private int labelCounter = 0; // For generating unique labels
    private BufferedWriter writer; // For writing MIPS code to a file
    private RegisterAllocator registerAllocator = new MIPSRegisterAllocator(); // For managing registers

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
            e.printStackTrace();
            return null;
        }

        // Emit the .data section
        emit(".data");
        emit("newline: .asciiz \"\\n\""); // For printing newlines

        // Emit the .text section
        emit(".text");
        emit(".globl main");

        // Visit function declarations
        if (node.getGlobalDeclarations() != null) {
            visit((FuncDeclsNode) node.getGlobalDeclarations());
        }

        // Visit the main function
        emit("main:");
        visit((FuncDeclNode) node.getMainFunction());

        // Exit the program
        emit("li $v0, 10"); // syscall for exit
        emit("syscall");

        // Close the writer
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String visit(FuncDeclNode node) {
        String functionName = node.getFunctionName().toString();
        emit(functionName + ":");

        // Prologue: Save $ra and $fp
        emit("addiu $sp, $sp, -8");
        emit("sw $ra, 4($sp)");
        emit("sw $fp, 0($sp)");
        emit("move $fp, $sp");

        // Visit the function body
        visit((StatementsNode) node.getBody());

        // Epilogue: Restore $ra and $fp, then return
        emit("lw $ra, 4($sp)");
        emit("lw $fp, 0($sp)");
        emit("addiu $sp, $sp, 8");
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

        // Save $t0-$t9 and $a0-$a3 (caller-saved registers)
        emit("addiu $sp, $sp, -40");
        for (int i = 0; i < 10; i++) {
            emit("sw $t" + i + ", " + (i * 4) + "($sp)");
        }

        // Pass arguments (if any)
        if (node.getArguments() != null) {
            visit((ArgListNode) node.getArguments());
        }

        // Call the function
        emit("jal " + functionName);

        // Restore $t0-$t9 and $a0-$a3
        for (int i = 0; i < 10; i++) {
            emit("lw $t" + i + ", " + (i * 4) + "($sp)");
        }
        emit("addiu $sp, $sp, 40");

        return null;
    }

    @Override
    public String visit(LiteralNode node) {
        // Get the type and value of the literal
        String type = node.getType();
        String value = node.getValue();

        // Allocate a register for the literal value
        String resultReg = registerAllocator.allocateRegister();

        // Generate MIPS code based on the literal type
        switch (type) {
            case "int":
            case "bool": // Booleans are treated as integers (0 or 1)
                emit("li " + resultReg + ", " + value); // Load immediate integer value
                break;
            case "float":
                // Load float value into a floating-point register
                emit("li.s $f0, " + value); // Load immediate float value
                emit("mfc1 " + resultReg + ", $f0"); // Move float value to general-purpose register
                break;
            case "char":
                emit("li " + resultReg + ", " + (int) value.charAt(0)); // Load ASCII value of the character
                break;
            case "string":
                // Store the string in the .data section and load its address
                String label = generateLabel("string_literal");
                emit(".data");
                emit(label + ": .asciiz \"" + value + "\"");
                emit(".text");
                emit("la " + resultReg + ", " + label); // Load address of the string
                break;
            default:
                throw new UnsupportedOperationException("Unsupported literal type: " + type);
        }

        // Return the register holding the literal value
        return resultReg;
    }

    @Override
    public String visit(ParamListNode node) {
        for (ASTNode n : node.getParams()) {
            visit((ParamNode) n);
        }
        return null; // Parameter list doesn't return a value
    }

    @Override
    public String visit(ParamNode node) {
        // Get the parameter name and type
        String paramName = node.getParamName().toString();
        String paramType = node.getParamType().toString();

        // Allocate space for the parameter on the stack
        emit("# Parameter: " + paramName + " (" + paramType + ")");

        // Parameters are typically passed in registers or on the stack
        // For simplicity, assume parameters are passed on the stack
        // The caller will push arguments onto the stack before calling the function
        return null; // Parameter node doesn't return a value
    }

    @Override
    public String visit(TypeNode node) {
        // Types are used for declarations and type checking
        // No MIPS code is generated for a type node
        return null; // Type node doesn't return a value
    }

    @Override
    public String visit(IdentifierNode node) {
        // Get the identifier name
        String identifierName = node.getName();

        // Allocate a register for the identifier's value
        String resultReg = registerAllocator.allocateRegister();

        // Load the value of the identifier into the register
        emit("lw " + resultReg + ", " + identifierName); // Assuming the variable is in memory

        // Return the register holding the identifier's value
        return resultReg;
    }

    @Override
    public String visit(ArithmeticExprNode node) {
        // Allocate a register for the left operand
        String leftRegister = registerAllocator.allocateRegister();
        String leftCode = node.getLeft().accept(this); // Evaluate the left operand
        emit(leftCode); // Emit the code for the left operand

        // Allocate a register for the right operand
        String rightRegister = registerAllocator.allocateRegister();
        String rightCode = node.getRight().accept(this); // Evaluate the right operand
        emit(rightCode); // Emit the code for the right operand

        // Determine the MIPS instruction based on the operator
        String operator = node.getOperator();
        String mipsInstruction;
        switch (operator) {
            case "+": mipsInstruction = "add"; break;
            case "-": mipsInstruction = "sub"; break;
            case "*": mipsInstruction = "mul"; break;
            case "/": mipsInstruction = "div"; break;
            default: throw new IllegalArgumentException("Unsupported operator: " + operator);
        }

        // Allocate a register for the result
        String resultRegister = registerAllocator.allocateRegister();

        // Emit the MIPS code for the operation
        emit(mipsInstruction + " " + resultRegister + ", " + leftRegister + ", " + rightRegister);

        // Free the registers used for the left and right operands (they are no longer needed)
        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);

        return resultRegister; // Return the register where the result is stored
    }

    @Override
    public String visit(LogicalExprNode node) {
        // Allocate a register for the left operand
        String leftRegister = registerAllocator.allocateRegister();
        String leftCode = node.getLeft().accept(this); // Evaluate the left operand
        emit(leftCode); // Emit the code for the left operand

        // Allocate a register for the right operand
        String rightRegister = registerAllocator.allocateRegister();
        String rightCode = node.getRight().accept(this); // Evaluate the right operand
        emit(rightCode); // Emit the code for the right operand

        // Determine the MIPS instruction based on the operator
        String operator = node.getOperator();
        String mipsInstruction;
        switch (operator) {
            case "&&": mipsInstruction = "and"; break;
            case "||": mipsInstruction = "or"; break;
            default: throw new IllegalArgumentException("Unsupported logical operator: " + operator);
        }

        // Allocate a register for the result
        String resultRegister = registerAllocator.allocateRegister();

        // Emit the MIPS code for the operation
        emit(mipsInstruction + " " + resultRegister + ", " + leftRegister + ", " + rightRegister);

        // Free the registers used for the left and right operands (they are no longer needed)
        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);

        return resultRegister; // Return the register where the result is stored
    }

    @Override
    public String visit(BreakNode node) {
        // Generate a label for the end of the loop
        String endLabel = generateLabel("end_loop");
        emit("j " + endLabel); // Jump to the end of the loop
        return null;
    }

    @Override
    public String visit(StatementsNode node) {
        // Visit each statement in the list
        for (ASTNode stmt : node.getStatements()) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public String visit(ArrayAccessNode node) {
        // Get the array name and index
        String arrayName = node.getArray().toString();
        String indexRegister = node.getIndex().accept(this); // Evaluate the index expression

        // Allocate a register for the result
        String resultRegister = registerAllocator.allocateRegister();

        // Calculate the address of the array element
        emit("sll $t0, " + indexRegister + ", 2"); // Multiply index by 4 (word size)
        emit("add $t0, $t0, " + arrayName); // Add base address of the array
        emit("lw " + resultRegister + ", 0($t0)"); // Load the array element

        // Free the index register
        registerAllocator.freeRegister(indexRegister);

        return resultRegister; // Return the register holding the array element
    }

    @Override
    public String visit(ArrayDeclNode node) {
        // Get the array name and size
        String arrayName = node.getArrayName().toString();
        //int size = node.getSize();
        int size = 100;

        // Allocate space for the array in the .data section
        emit(".data");
        emit(arrayName + ": .space " + (size * 4)); // Allocate size * 4 bytes (word-aligned)
        emit(".text");

        return null;
    }

    @Override
    public String visit(AssignNode node) {
        // Get the left-hand side (LHS) and right-hand side (RHS) of the assignment
        String lhs = node.getLeft().accept(this); // Evaluate the LHS
        String rhs = node.getRight().accept(this); // Evaluate the RHS

        // Emit the MIPS code for the assignment
        emit("sw " + rhs + ", " + lhs); // Store the RHS value into the LHS address

        // Free the registers used for the LHS and RHS
        registerAllocator.freeRegister(lhs);
        registerAllocator.freeRegister(rhs);

        return null;
    }

    @Override
    public String visit(ErrorNode node) {
        // Handle errors (e.g., print an error message)
        emit("# Error: " + node.getErrorMessage());
        return null;
    }

    @Override
    public String visit(ForNode node) {
        // Generate labels for the loop
        String startLabel = generateLabel("start_loop");
        String endLabel = generateLabel("end_loop");

        // Visit the initialization statement
        if (node.getInitialization() != null) {
            visit((AssignNode) node.getInitialization());
        }

        // Emit the start label
        emit(startLabel + ":");

        // Visit the condition expression
        if (node.getCondition() != null) {
            String conditionRegister = node.getCondition().accept(this);
            emit("beqz " + conditionRegister + ", " + endLabel); // Branch to end if condition is false
            registerAllocator.freeRegister(conditionRegister);
        }

        // Visit the body of the loop
        visit((StatementsNode) node.getBody());

        // Visit the update statement
        if (node.getUpdate() != null) {
            visit((AssignNode) node.getUpdate());
        }

        // Jump back to the start of the loop
        emit("j " + startLabel);

        // Emit the end label
        emit(endLabel + ":");

        return null;
    }

    @Override
    public String visit(IfNode node) {
        // Generate labels for the if-else structure
        String elseLabel = generateLabel("else");
        String endLabel = generateLabel("end_if");

        // Visit the condition expression
        String conditionRegister = node.getCondition().accept(this);
        emit("beqz " + conditionRegister + ", " + elseLabel); // Branch to else if condition is false
        registerAllocator.freeRegister(conditionRegister);

        // Visit the if body
        visit((StatementsNode) node.getThenBlock());

        // Jump to the end of the if-else structure
        emit("j " + endLabel);

        // Emit the else label
        emit(elseLabel + ":");

        // Visit the else body (if it exists)
        if (node.getElseBlock() != null) {
            visit((StatementsNode) node.getElseBlock());
        }

        // Emit the end label
        emit(endLabel + ":");

        return null;
    }

    @Override
    public String visit(RelationalExprNode node) {
        // Allocate a register for the left operand
        String leftRegister = registerAllocator.allocateRegister();
        String leftCode = node.getLeft().accept(this); // Evaluate the left operand
        emit(leftCode); // Emit the code for the left operand

        // Allocate a register for the right operand
        String rightRegister = registerAllocator.allocateRegister();
        String rightCode = node.getRight().accept(this); // Evaluate the right operand
        emit(rightCode); // Emit the code for the right operand

        // Determine the MIPS instruction based on the operator
        String operator = node.getOperator();
        String mipsInstruction;
        switch (operator) {
            case "==": mipsInstruction = "seq"; break;
            case "!=": mipsInstruction = "sne"; break;
            case "<": mipsInstruction = "slt"; break;
            case "<=": mipsInstruction = "sle"; break;
            case ">": mipsInstruction = "sgt"; break;
            case ">=": mipsInstruction = "sge"; break;
            default: throw new IllegalArgumentException("Unsupported relational operator: " + operator);
        }

        // Allocate a register for the result
        String resultRegister = registerAllocator.allocateRegister();

        // Emit the MIPS code for the operation
        emit(mipsInstruction + " " + resultRegister + ", " + leftRegister + ", " + rightRegister);

        // Free the registers used for the left and right operands (they are no longer needed)
        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);

        return resultRegister; // Return the register where the result is stored
    }

    @Override
    public String visit(ReturnNode node) {
        // Visit the return expression (if it exists)
        if (node.getReturnValue() != null) {
            String resultRegister = node.getReturnValue().accept(this);
            emit("move $v0, " + resultRegister); // Move the result into $v0
            registerAllocator.freeRegister(resultRegister);
        }

        // Jump to the function epilogue
        emit("j " + generateLabel("function_epilogue"));

        return null;
    }

    @Override
    public String visit(VarDeclNode node) {
        // Get the variable name and type
        String varName = node.getVarName().toString();
        String varType = node.getVarType().toString();

        // Allocate space for the variable in the .data section
        emit(".data");
        emit(varName + ": .space 4"); // Allocate 4 bytes for the variable
        emit(".text");

        return null;
    }

    @Override
    public String visit(WhileNode node) {
        // Generate labels for the loop
        String startLabel = generateLabel("start_loop");
        String endLabel = generateLabel("end_loop");

        // Emit the start label
        emit(startLabel + ":");

        // Visit the condition expression
        String conditionRegister = node.getCondition().accept(this);
        emit("beqz " + conditionRegister + ", " + endLabel); // Branch to end if condition is false
        registerAllocator.freeRegister(conditionRegister);

        // Visit the body of the loop
        visit((StatementsNode) node.getBody());

        // Jump back to the start of the loop
        emit("j " + startLabel);

        // Emit the end label
        emit(endLabel + ":");

        return null;
    }

    @Override
    public String visit(ArgListNode node) {
        // Visit each argument in the list
        for (ASTNode arg : node.getArguments()) {
            arg.accept(this);
        }
        return null;
    }

    @Override
    public String visit(ElementListNode node) {
        // Visit each element in the list
        for (ASTNode element : node.getElements()) {
            element.accept(this);
        }
        return null;
    }

    @Override
    public String visit(PrintNode node) {
        // Visit the expression to be printed
        String resultRegister = node.getExpression().accept(this);

        // Emit the MIPS code for printing
        emit("li $v0, 1"); // syscall for printing an integer
        emit("move $a0, " + resultRegister); // Move the result into $a0
        emit("syscall");

        // Free the result register
        registerAllocator.freeRegister(resultRegister);

        return null;
    }

    @Override
    public String visit(ReadNode node) {
        // Get the variable name
        String varName = node.getIdentifier().toString();

        // Emit the MIPS code for reading an integer
        emit("li $v0, 5"); // syscall for reading an integer
        emit("syscall");
        emit("sw $v0, " + varName); // Store the result in the variable

        return null;
    }

    @Override
    public void write(String code) {
        emit(code); // Use the emit method to write code to the output file
    }
}