package org.example.generator;

import org.example.node.*;
import org.example.register.MIPSRegisterAllocator;
import org.example.register.RegisterAllocator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MIPSCodeGenerator implements ASTVisitor {

    private int labelCounter = 0; // For generating unique labels
    private BufferedWriter writer; // For writing MIPS code to a file
    private RegisterAllocator registerAllocator = new MIPSRegisterAllocator(); // For managing registers
    private FileWriter fileWriter;

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
        fileWriter = new FileWriter(new File("output.asm"));

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
        visit((FuncDeclNode )node.getMainFunction());

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
        for(ASTNode n : node.getParams()) {
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




















    // Ocupamos de alguna manera poder visitar los expression nodes de cualquier tipo

    @Override
    public String visit(BreakNode node) {
        return "";
    }

    @Override
    public String visit(StatementsNode node) {
        return "";
    }

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
    public String visit(IfNode node) {
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
    public String visit(ArgListNode node) {
        return "";
    }

    @Override
    public String visit(ElementListNode node) {
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
    public void write(String code) {

    }

}