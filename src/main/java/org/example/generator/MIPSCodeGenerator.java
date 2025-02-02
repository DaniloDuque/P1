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
        try {
            writer = new BufferedWriter(new FileWriter("output.asm"));
        } catch (IOException e) {
            System.err.println("Error: Could not open output file.");
            e.printStackTrace();
            return null;
        }

        emit(".data");
        emit("newline: .asciiz \"\\n\"");

        if (dataSegment != null) {
            emit(dataSegment.toString());
        }

        emit(".text");
        emit(".globl main");

        if (node.getGlobalDeclarations() != null) {
            visit((FuncDeclsNode) node.getGlobalDeclarations());
        }

        emit("main:");

        if (node.getMainFunction() != null) {
            visit((StatementsNode) node.getMainFunction());
        }

        // Exit program
        emit("li $v0, 10");
        emit("syscall");

        try {
            writer.flush();  // Ensure all data is written before closing
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

        // Function prologue: Save $ra, $fp, and adjust stack for alignment (if necessary)
        emit("addi $sp, $sp, -8");  // Make space for $ra and $fp
        emit("sw $ra, 4($sp)");      // Save return address
        emit("sw $fp, 0($sp)");      // Save frame pointer
        emit("move $fp, $sp");       // Set frame pointer to current stack pointer

        visit((StatementsNode) node.getBody());  // Visit the body of the function

        // Function epilogue: Restore registers and return
        emit("lw $fp, 0($sp)");      // Restore frame pointer
        emit("lw $ra, 4($sp)");      // Restore return address
        emit("addi $sp, $sp, 8");    // Adjust stack pointer (clean up space for $ra and $fp)
        emit("jr $ra");              // Jump to return address

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
            try {
                visit((FuncDeclsNode) node.getNextFuncDecls());
            } catch (Exception e) {
                visit((FuncDeclNode) node.getNextFuncDecls());
            }
        }

        return null;
    }

    @Override
    public String visit(FuncCallNode node) {
        String functionName = node.getFunctionName().toString();
        List<ASTNode> args = ((ArgListNode) node.getArguments()).getArguments();

        // Pass arguments into $a0-$a3 or stack
        for (int i = 0; i < args.size(); i++) {
            String argRegister = args.get(i).accept(this);

            if (i < 4) {
                // Move the argument into $a0-$a3
                emit("move $a" + i + ", " + argRegister);
            } else {
                // Store the argument on the stack
                emit("sw " + argRegister + ", " + ((i - 4) * 4) + "($sp)");
            }

            // Free register after usage
            registerAllocator.freeRegister(argRegister);
        }

        // Make the function call
        emit("jal " + functionName);

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
                emit("li " + resultReg + ", " + value);  // Load integer or boolean
                break;
            case "float":
                String floatLabel = generateLabel("float_literal");
                // Emit the float literal in the data section
                emit(".data");
                emit(floatLabel + ": .float " + value);
                emit(".text");
                // Load the floating-point value from memory into a floating-point register
                emit("lwc1 $f0, " + floatLabel);
                // If necessary, move it to a general-purpose register
                emit("mfc1 " + resultReg + ", $f0");
                break;
            case "char":
                emit("li " + resultReg + ", " + (int) value.charAt(0));  // Load char as int (ASCII value)
                break;
            case "string":
                String stringLabel = generateLabel("string_literal");
                emit(".data");
                emit(stringLabel + ": .asciiz \"" + value + "\"");
                emit(".text");
                // Load the address of the string literal
                emit("la " + resultReg + ", " + stringLabel);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported literal type: " + type);
        }

        return resultReg;
    }

    @Override
    public String visit(ParamListNode node) {
        int paramIndex = 0;

        for (ASTNode n : node.getParams()) {
            ParamNode paramNode = (ParamNode) n;

            // Handle parameter-specific actions (e.g., allocating registers, managing stack)
            String paramReg = visit(paramNode);

            if (paramIndex < 4) {
                // If there are 4 or fewer parameters, pass them via $a0-$a3 registers
                emit("move $a" + paramIndex + ", " + paramReg);
            } else {
                // If there are more than 4 parameters, pass them on the stack
                emit("sw " + paramReg + ", " + ((paramIndex - 4) * 4) + "($sp)");
            }

            registerAllocator.freeRegister(paramReg); // Free the register after use
            paramIndex++;
        }

        return null;
    }

    @Override
    public String visit(ParamNode node) {
        String paramName = " ";
        String paramType = "node.getParamType()";

        // Emit a comment with the parameter name and type
        emit("# Parameter: " + paramName + " (" + paramType + ")");

        // Allocate a register for the parameter if needed
        String paramReg = registerAllocator.allocateRegister();

        // For simplicity, you could use $a0-$a3 for the first 4 parameters
        // The handling of this could be done in ParamListNode, but you can add logic here if needed
        emit("move " + paramReg + ", $a0");  // Example: move the value to the allocated register (if it's within the first 4 parameters)

        // You could handle stack-based allocation here if necessary for more than 4 parameters

        // Return the allocated register
        return paramReg;
    }


    @Override
    public String visit(TypeNode node) {
        return null;
    }

    @Override
    public String visit(IdentifierNode node) {
        String identifierName = node.getName();
        String resultReg = registerAllocator.allocateRegister();

        // If it's a global variable, we can use the address directly
        emit("la $t0, " + identifierName);  // Load the address of the identifier into $t0
        emit("lw " + resultReg + ", 0($t0)");  // Load the value from the address into resultReg

        // If the identifier is a local variable, you may want to adjust this with $fp (stack frame pointer)
        // For example, if the identifier is on the stack, you would do something like:
        // emit("lw " + resultReg + ", " + offset + "($fp)");

        return resultReg;
    }


    @Override
    public String visit(ArithmeticExprNode node) {
        String leftRegister = node.getLeft().accept(this);
        String rightRegister = node.getRight().accept(this);
        String resultRegister = registerAllocator.allocateRegister();

        switch (node.getOperator()) {
            case "+":
                emit("add " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            case "-":
                emit("sub " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            case "*":
                emit("mul " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;
            case "/":
                // Check for division by zero
                emit("beq " + rightRegister + ", $zero, division_by_zero_error");
                emit("div " + leftRegister + ", " + rightRegister);
                emit("mflo " + resultRegister);
                emit("j end_of_division");
                emit("division_by_zero_error:");
                emit("li $v0, 4");  // Print error message
                emit("la $a0, division_by_zero_msg");
                emit("syscall");
                emit("li $v0, 10");  // Exit program
                emit("syscall");
                emit("end_of_division:");
                break;
        }

        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);
        return resultRegister;
    }


    @Override
    public String visit(LogicalExprNode node) {
        String leftRegister = node.getLeft().accept(this);
        String rightRegister = node.getRight().accept(this);
        String resultRegister = registerAllocator.allocateRegister();

        switch (node.getOperator()) {
            case "&&":
                // Short-circuit: if left is false (zero), result is false (zero)
                emit("beq " + leftRegister + ", $zero, left_is_false");
                emit("move " + resultRegister + ", $zero"); // Set result to false
                emit("b end_of_logical_op");
                emit("left_is_false:");
                emit("move " + resultRegister + ", $zero"); // Set result to false
                break;

            case "||":
                // Short-circuit: if left is true (non-zero), result is true (non-zero)
                emit("bnez " + leftRegister + ", left_is_true");
                emit("move " + resultRegister + ", $zero"); // Set result to false
                emit("b end_of_logical_op");
                emit("left_is_true:");
                emit("move " + resultRegister + ", $zero"); // Set result to true
                break;

            default:
                throw new IllegalArgumentException("Unsupported logical operator: " + node.getOperator());
        }

        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);

        emit("end_of_logical_op:"); // End label for logical operations

        return resultRegister;
    }


    @Override
    public String visit(RelationalExprNode node) {
        String leftRegister = node.getLeft().accept(this);
        String rightRegister = node.getRight().accept(this);
        String resultRegister = registerAllocator.allocateRegister();

        // Handle relational operator
        switch (node.getOperator()) {
            case "==":
                emit("seq " + resultRegister + ", " + leftRegister + ", " + rightRegister);  // equal
                break;
            case "!=":
                emit("sne " + resultRegister + ", " + leftRegister + ", " + rightRegister);  // not equal
                break;
            case "<":
                emit("slt " + resultRegister + ", " + leftRegister + ", " + rightRegister);  // less than
                break;
            case "<=":
                emit("sle " + resultRegister + ", " + leftRegister + ", " + rightRegister);  // less than or equal
                break;
            case ">":
                emit("sgt " + resultRegister + ", " + leftRegister + ", " + rightRegister);  // greater than
                break;
            case ">=":
                emit("sge " + resultRegister + ", " + leftRegister + ", " + rightRegister);  // greater than or equal
                break;
            default:
                throw new IllegalArgumentException("Unsupported relational operator: " + node.getOperator());
        }

        // Free the registers used for comparison
        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);

        return resultRegister;
    }

    @Override
    public String visit(ArrayAccessNode node) {
        String arrayName = node.getArray().toString();
        String indexRegister = node.getIndex().accept(this);
        String resultRegister = registerAllocator.allocateRegister();

        // Multiply the index by 4 (for int) to calculate the byte offset
        emit("sll $t0, " + indexRegister + ", 2");

        // Load the base address of the array
        emit("la $t1, " + arrayName);

        // Compute the address of the element
        emit("add $t0, $t1, $t0");

        // Load the value of the element into the result register
        emit("lw " + resultRegister + ", 0($t0)");

        // Free the register used for the index
        registerAllocator.freeRegister(indexRegister);

        return resultRegister;
    }

    @Override
    public String visit(ArrayDeclNode node) {
        String arrayName = node.getArrayName().toString();
        int size = 100; // Default size for simplicity, could be dynamic depending on the node

        // Declare the array in the .data section with space for 'size' elements
        emit(arrayName + ": .space " + (size * 4));  // Each element is 4 bytes (assuming int array)

        return null;
    }


    @Override
    public String visit(AssignNode node) {
        String lhs = node.getLeft().accept(this);
        String rhs = node.getRight().accept(this);

        // Ensure LHS is the address where we want to store the RHS value
        emit("sw " + rhs + ", 0(" + lhs + ")");

        // Free the registers after use
        registerAllocator.freeRegister(lhs);
        registerAllocator.freeRegister(rhs);

        return null;
    }


    @Override
    public String visit(BreakNode node) {
        // Generate a unique label for breaking out of the loop
        String endLabel = generateLabel("end_loop");

        // Emit the jump instruction to the end label, which will exit the loop
        emit("j " + endLabel);

        return null;
    }


    @Override
    public String visit(ElementListNode node) {
        // Visit each element in the list and generate MIPS code for it
        for (ASTNode element : node.getElements()) {
            element.accept(this);
        }
        return null;
    }


    @Override
    public String visit(ErrorNode node) {
        // Emit the error message in the data section
        emit(".data");
        emit("error_msg: .asciiz \"" + node.getErrorMessage() + "\"");

        // Emit the MIPS code to print the error message
        emit(".text");
        emit("li $v0, 4");            // Load syscall code for printing string
        emit("la $a0, error_msg");    // Load address of error message into $a0
        emit("syscall");              // Print the error message

        // Exit the program
        emit("li $v0, 10");           // Load syscall code for program exit
        emit("syscall");              // Exit the program

        return null;
    }


    @Override
    public String visit(ForNode node) {
        String startLabel = generateLabel("start_loop");
        String endLabel = generateLabel("end_loop");

        // Handle initialization if provided
        if (node.getInitialization() != null) {
            try{
                visit((AssignNode) node.getInitialization());
            } catch (Exception e){
                visit((VarDeclNode) node.getInitialization());
            }
        }

        emit(startLabel + ":");

        // Handle condition if provided
        if (node.getCondition() != null) {
            String conditionRegister = node.getCondition().accept(this);
            emit("beqz " + conditionRegister + ", " + endLabel);
            registerAllocator.freeRegister(conditionRegister);
        }

        // Visit the body of the loop
        visit((StatementsNode) node.getBody());

        // Handle update expression if provided
        if (node.getUpdate() != null) {
            try {
                visit((ArithmeticExprNode) node.getUpdate());
            } catch (Exception e) {
                visit((AssignNode) node.getUpdate());
            }
        }

        emit("j " + startLabel);  // Jump to start of the loop
        emit(endLabel + ":");     // End of the loop

        return null;
    }


    @Override
    public String visit(IfNode node) {
        // Generate unique labels for the else and end sections
        String elseLabel = generateLabel("else");
        String endLabel = generateLabel("end_if");

        // Evaluate the condition
        String conditionRegister = node.getCondition().accept(this);
        emit("beqz " + conditionRegister + ", " + elseLabel);  // Branch to else if condition is false
        registerAllocator.freeRegister(conditionRegister);  // Free register after use

        // Visit the then block (if condition is true)
        visit((StatementsNode) node.getThenBlock());
        emit("j " + endLabel);  // Jump to the end to skip else block if condition was true

        // Else block label and body
        emit(elseLabel + ":");
        if (node.getElseBlock() != null) {
            visit((StatementsNode) node.getElseBlock());  // Visit the else block if it exists
        }

        // End label for the if-else statement
        emit(endLabel + ":");
        return null;
    }


    @Override
    public String visit(ReturnNode node) {
        if (node.getReturnValue() != null) {
            // Evaluate the return value and move it to $v0
            String resultRegister = node.getReturnValue().accept(this);
            emit("move $v0, " + resultRegister);
            registerAllocator.freeRegister(resultRegister);  // Free the register used for the return value
        } else {
            // If there's no return value, make sure $v0 is zeroed out (standard for no return)
            emit("move $v0, $zero");
        }

        // Jump to the function epilogue
        emit("j " + generateLabel("function_epilogue"));
        return null;
    }

    //TODO: add string case
    @Override
    public String visit(VarDeclNode node) {
        String varName = node.getVarType().toString();
        String varType = node.getVarName().toString();

        emit(".data");

        // Handle different types
        switch (varType) {
            case "int":
            case "bool":
                emit(varName + ": .space 4");  // 4 bytes for integers and booleans
                break;
            case "float":
                emit(varName + ": .float 0.0");  // Initializing float to 0.0
                break;
            case "char":
                emit(varName + ": .byte 0");  // 1 byte for characters
                break;
            default:
                throw new UnsupportedOperationException("Unsupported variable type: " + varType);
        }

        emit(".text");
        return null;
    }


    @Override
    public String visit(WhileNode node) {
        String startLabel = generateLabel("start_loop");
        String endLabel = generateLabel("end_loop");

        emit(startLabel + ":");
        String conditionRegister = node.getCondition().accept(this);

        // Branch to end if condition is false
        emit("beqz " + conditionRegister + ", " + endLabel);
        registerAllocator.freeRegister(conditionRegister);

        // Visit the body of the loop
        visit((StatementsNode) node.getBody());

        // Jump back to the start of the loop
        emit("j " + startLabel);

        emit(endLabel + ":");
        return null;
    }


    @Override
    public String visit(ArgListNode node) {
        List<ASTNode> args = node.getArguments();
        int argCount = 0;

        for (ASTNode arg : args) {
            String argRegister = arg.accept(this);

            // Handle up to four arguments in registers $a0 to $a3
            if (argCount < 4) {
                emit("move $a" + argCount + ", " + argRegister);
            } else {
                // For more than four arguments, push them onto the stack
                emit("sw " + argRegister + ", " + ((argCount - 4) * 4) + "($sp)");
            }

            registerAllocator.freeRegister(argRegister);
            argCount++;
        }

        return null;
    }


    @Override
    public String visit(PrintNode node) {
        // Evaluate the expression and get the result in a register
        String resultRegister = node.getExpression().accept(this);

        // Set up for the print integer syscall (system call 1)
        emit("li $v0, 1");              // Load the system call code for print integer
        emit("move $a0, " + resultRegister); // Move the result into $a0 (argument register)

        // Perform the syscall to print the integer
        emit("syscall");

        // Free the register after use
        registerAllocator.freeRegister(resultRegister);

        return null;
    }


    @Override
    public String visit(ReadNode node) {
        // Get the variable name where the input will be stored
        String varName = node.getIdentifier().toString();

        // Prepare for the read integer system call (syscall 5)
        emit("li $v0, 5");    // Load the system call code for reading an integer

        // Perform the syscall to read the integer from the user input
        emit("syscall");

        // Store the result (in $v0) into the variable
        emit("sw $v0, " + varName);  // Store the input value into the variable's address

        return null;
    }

    @Override
    public String visit(StatementsNode node) {
        // Iterate over the list of statements and process each one
        for (ASTNode stmt : node.getStatements()) {
            if (stmt != null) {
                // Visit the statement to generate the corresponding MIPS code
                stmt.accept(this);
            }
        }
        return null;
    }

    @Override
    public void write(String code) {
        emit(code);
    }
}