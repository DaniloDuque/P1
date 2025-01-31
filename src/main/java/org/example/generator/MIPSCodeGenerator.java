package org.example.generator;

import org.example.node.*;
import org.example.register.MIPSRegisterAllocator;
import org.example.register.RegisterAllocator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MIPSCodeGenerator implements ASTVisitor {

    private RegisterAllocator registerAllocator = new MIPSRegisterAllocator();
    private int labelCounter = 0;
    private FileWriter mipsFile;

    private String generateUniqueLabel(String base) {
        return base + "_" + (labelCounter++);
    }

    @Override
    public String visit(ArithmeticExprNode node) {
        StringBuilder code = new StringBuilder();

        // Evaluate left and right operands
        String leftCode = node.getLeft().accept(this);
        String leftRegister = registerAllocator.allocateRegister();  // Allocate register for left operand
        code.append(leftCode).append("\n");

        String rightCode = node.getRight().accept(this);
        String rightRegister = registerAllocator.allocateRegister();  // Allocate register for right operand
        code.append(rightCode).append("\n");

        // Allocate register for the result
        String resultRegister = registerAllocator.allocateRegister();

        // Perform the arithmetic operation based on the operator
        String operator = node.getOperator();

        switch (operator) {
            case "+":
                code.append("add ").append(resultRegister).append(", ").append(leftRegister).append(", ").append(rightRegister).append("\n");
                break;

            case "-":
                code.append("sub ").append(resultRegister).append(", ").append(leftRegister).append(", ").append(rightRegister).append("\n");
                break;

            case "*":
                code.append("mul ").append(resultRegister).append(", ").append(leftRegister).append(", ").append(rightRegister).append("\n");
                break;

            case "/":
                code.append("div ").append(leftRegister).append(", ").append(rightRegister).append("\n");
                code.append("mflo ").append(resultRegister).append("\n");  // Store quotient in result
                break;

            case "%":
                code.append("div ").append(leftRegister).append(", ").append(rightRegister).append("\n");
                code.append("mfhi ").append(resultRegister).append("\n");  // Store remainder in result
                break;

            default:
                throw new UnsupportedOperationException("Unsupported arithmetic operator: " + operator);
        }

        // Free the registers used for operands
        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);

        return code.toString();
    }

    @Override
    public String visit(ArrayAccessNode node) {
        StringBuilder code = new StringBuilder();

        // Evaluate the array base
        String arrayBaseCode = node.getArray().accept(this);
        String arrayBaseRegister = registerAllocator.allocateRegister();  // Allocate register for array base
        code.append(arrayBaseCode).append("\n");

        // Evaluate the index
        String indexCode = node.getIndex().accept(this);
        String indexRegister = registerAllocator.allocateRegister();  // Allocate register for index
        code.append(indexCode).append("\n");

        // Multiply index by element size (assuming 4 bytes per element for int)
        String tempRegister = registerAllocator.allocateRegister();  // Temporary register for offset
        code.append("sll ").append(tempRegister).append(", ").append(indexRegister).append(", 2").append("\n");  // index * 4

        // Add offset to array base register
        String addressRegister = registerAllocator.allocateRegister();  // Register for element address
        code.append("add ").append(addressRegister).append(", ").append(arrayBaseRegister).append(", ").append(tempRegister).append("\n");

        // Load the value from the array at the calculated address
        String valueRegister = registerAllocator.allocateRegister();  // Register for element value
        code.append("lw ").append(valueRegister).append(", 0(").append(addressRegister).append(")").append("\n");

        // Free the registers used for array base, index, and temporary
        registerAllocator.freeRegister(arrayBaseRegister);
        registerAllocator.freeRegister(indexRegister);
        registerAllocator.freeRegister(tempRegister);

        return code.toString();
    }

    @Override
    public String visit(ArrayDeclNode node) {
        StringBuilder code = new StringBuilder();

        String arrayName = node.getArrayName();   // Get array name
        int arraySize = node.getArraySize();      // Get array size
        String arrayType = node.getArrayType();   // Get array type (to determine element size)

        // Determine element size based on type
        int elementSize;
        switch (arrayType) {
            case "ENTERO":
                elementSize = 4;  // int is 4 bytes in MIPS
                break;
            case "FLOTANTE":
                elementSize = 4;  // float is 4 bytes in MIPS
                break;
            case "CARACTER":
                elementSize = 1;  // char is 1 byte
                break;
            case "BOOLEANO":
                elementSize = 1;  // boolean is 1 byte
                break;
            default:
                throw new UnsupportedOperationException("Unsupported data type: " + arrayType);
        }

        // Calculate total array size
        int totalSize = arraySize * elementSize;

        // Reserve space on the stack for the local array
        code.append("subu $sp, $sp, ").append(totalSize).append("   # Reserve space for array ").append(arrayName).append("\n");

        // Save the base address of the array in a register (e.g., $t0)
        code.append("move $t0, $sp   # Save base address of array ").append(arrayName).append("\n");

        // If there is initialization, initialize the array elements (optional)
        for (int i = 0; i < arraySize; i++) {
            code.append("li $t1, 0   # Initialize element ").append(i).append(" of array with 0").append("\n");
            code.append("sw $t1, ").append(i * elementSize).append("($t0)   # Store value in array").append("\n");
        }

        return code.toString();
    }

    @Override
    public String visit(AssignNode node) {
        StringBuilder code = new StringBuilder();

        // Evaluate the right-hand side expression
        String rightCode = node.getRight().accept(this);
        String valueRegister = registerAllocator.allocateRegister();  // Allocate register for the value
        code.append(rightCode).append("\n");

        // Evaluate the left-hand side (could be a variable or array access)
        if (node.getLeft() instanceof VarDeclNode) {
            VarDeclNode varNode = (VarDeclNode) node.getLeft();
            String varName = varNode.getVarName();  // Get variable name

            // Store the value in the variable
            code.append("sw ").append(valueRegister).append(", ").append(varName).append("   # Store value in variable ").append(varName).append("\n");

        } else if (node.getLeft() instanceof ArrayAccessNode) {
            // If it's an array access, calculate the address of the index
            ArrayAccessNode arrayAccess = (ArrayAccessNode) node.getLeft();
            String arrayAccessCode = arrayAccess.accept(this);  // Evaluate array access
            code.append(arrayAccessCode).append("\n");

            // Assume the index address is in a temporary register (e.g., $t0)
            String arrayBaseRegister = "$t0";  // Assume array base address is in $t0
            code.append("sw ").append(valueRegister).append(", 0(").append(arrayBaseRegister).append(")   # Store value in array index").append("\n");

        } else {
            throw new UnsupportedOperationException("Unsupported assignment type.");
        }

        // Free the register used for the value
        registerAllocator.freeRegister(valueRegister);

        return code.toString();
    }

    @Override
    public String visit(ErrorNode node) {
        String errorMessage = node.getErrorMessage();  // Get the error message

        // Generate a comment in the output code indicating an error
        return "# ERROR: " + errorMessage + "\n";
    }

    @Override
    public String visit(ForNode node) {
        StringBuilder code = new StringBuilder();

        // Generate code for loop initialization (e.g., int i = 0)
        String initCode = node.getInitialization().accept(this);
        code.append(initCode).append("\n");

        // Create unique labels for loop start and end
        String loopStartLabel = generateUniqueLabel("for_loop_start");
        String loopEndLabel = generateUniqueLabel("for_loop_end");

        // Label for loop start
        code.append(loopStartLabel).append(":").append("\n");

        // Generate code for loop condition (e.g., i < 10)
        String conditionCode = node.getCondition().accept(this);
        String conditionRegister = registerAllocator.allocateRegister();  // Register for condition result
        code.append(conditionCode).append("\n");
        code.append("beqz ").append(conditionRegister).append(", ").append(loopEndLabel).append("   # Jump to end if condition is false").append("\n");

        // Generate code for loop body
        String bodyCode = node.getBody().accept(this);
        code.append(bodyCode).append("\n");

        // Generate code for loop update (e.g., i++)
        String updateCode = node.getUpdate().accept(this);
        code.append(updateCode).append("\n");

        // Jump back to loop start for next iteration
        code.append("j ").append(loopStartLabel).append("\n");

        // Label for loop end
        code.append(loopEndLabel).append(":").append("\n");

        // Free the register used for the condition
        registerAllocator.freeRegister(conditionRegister);

        return code.toString();
    }

    @Override
    public String visit(FuncDeclNode node) {
        StringBuilder code = new StringBuilder();
        code.append(node.getFunctionName()).append(":").append("\n");

        // Prologue
        code.append("sw $ra, 0($sp)\n");
        code.append("sw $fp, -4($sp)\n");
        code.append("move $fp, $sp\n");
        code.append("addi $sp, $sp, -8\n");

        // Parámetros
        if (node.getParameters() != null) {
            code.append(node.getParameters().accept(this)).append("\n");
        }

        // Cuerpo de la función
        code.append(node.getBody().accept(this)).append("\n");

        // Epilogue
        code.append("lw $fp, -4($fp)\n");
        code.append("lw $ra, 0($fp)\n");
        code.append("addi $sp, $sp, 8\n");
        code.append("jr $ra\n");

        return code.toString();
    }

    @Override
    public String visit(ParamListNode node) {
        StringBuilder code = new StringBuilder();
        int paramOffset = 8; // Offset para los parámetros

        for (int i = 0; i < node.getParams().size(); i++) {
            ASTNode paramNode = node.getParams().get(i);
            String paramRegister = paramNode.accept(this); // Obtiene el registro del parámetro
            code.append("sw ").append(paramRegister).append(", ").append(paramOffset + i * 4).append("($fp)\n");
        }

        return code.toString();
    }

    @Override
    public String visit(ParamNode node) {
        // Asigna un registro para el parámetro (ej: $a0, $a1, etc.)
        String register = registerAllocator.allocateRegister();
        String argRegisterIndex = registerAllocator.allocateRegister();
        return "move " + register + ", $a" + argRegisterIndex;
    }

    @Override
    public String visit(FuncExecNode node) {
        StringBuilder code = new StringBuilder();

        // Get function name
        String functionName = node.getFunctionName();

        // Get function arguments
        List<ASTNode> arguments = node.getArguments();

        // Assign first 4 arguments to $a0, $a1, $a2, $a3
        int argIndex = 0;
        for (; argIndex < Math.min(4, arguments.size()); argIndex++) {
            String argCode = arguments.get(argIndex).accept(this);  // Evaluate argument
            String argRegister = "$a" + argIndex;
            code.append(argCode).append("\n");
            code.append("move ").append(argRegister).append(", ").append("$t" + argIndex).append("\n");  // Move value to argument register
        }

        // If there are more than 4 arguments, pass them on the stack
        if (arguments.size() > 4) {
            int extraArgsCount = arguments.size() - 4;
            code.append("sub $sp, $sp, ").append(extraArgsCount * 4).append("\n");  // Reserve stack space

            for (int i = 4; i < arguments.size(); i++) {
                String argCode = arguments.get(i).accept(this);  // Evaluate argument
                String tempRegister = "$t" + i;
                code.append(argCode).append("\n");
                code.append("sw ").append(tempRegister).append(", ").append((i - 4) * 4).append("($sp)").append("\n");  // Save argument on stack
            }
        }

        // Call the function
        code.append("jal ").append(functionName).append("\n");

        // Restore stack pointer if it was modified
        if (arguments.size() > 4) {
            code.append("add $sp, $sp, ").append((arguments.size() - 4) * 4).append("\n");
        }

        return code.toString();
    }

    @Override
    public String visit(FunctionCallNode node) {
        StringBuilder code = new StringBuilder();

        // Get function name
        String functionName = node.getFunctionName();

        // Get function arguments
        List<ASTNode> arguments = node.getArguments();

        // Assign first 4 arguments to $a0, $a1, $a2, $a3
        int argIndex = 0;
        for (; argIndex < Math.min(4, arguments.size()); argIndex++) {
            String argCode = arguments.get(argIndex).accept(this);  // Evaluate argument
            String argRegister = "$a" + argIndex;
            code.append(argCode).append("\n");
            code.append("move ").append(argRegister).append(", ").append("$t" + argIndex).append("\n");  // Move value to argument register
        }

        // If there are more than 4 arguments, pass them on the stack
        if (arguments.size() > 4) {
            int extraArgsCount = arguments.size() - 4;
            code.append("sub $sp, $sp, ").append(extraArgsCount * 4).append("\n");  // Reserve stack space

            for (int i = 4; i < arguments.size(); i++) {
                String argCode = arguments.get(i).accept(this);  // Evaluate argument
                String tempRegister = "$t" + i;
                code.append(argCode).append("\n");
                code.append("sw ").append(tempRegister).append(", ").append((i - 4) * 4).append("($sp)").append("\n");  // Save argument on stack
            }
        }

        // Call the function
        code.append("jal ").append(functionName).append("\n");

        // Restore stack pointer if it was modified
        if (arguments.size() > 4) {
            code.append("add $sp, $sp, ").append((arguments.size() - 4) * 4).append("\n");
        }

        return code.toString();
    }

    @Override
    public String visit(IfNode node) {
        StringBuilder code = new StringBuilder();

        // Evaluate the condition
        String conditionCode = node.getCondition().accept(this);
        String conditionRegister = "$t0";  // Assume condition result is in $t0
        code.append(conditionCode).append("\n");

        // Create labels for jumps
        String elseLabel = "else_label_" + node.hashCode();
        String endLabel = "end_label_" + node.hashCode();

        // Perform conditional jump
        code.append("beq ").append(conditionRegister).append(", $zero, ").append(elseLabel).append("   # Jump to else if condition is false").append("\n");

        // Generate code for the 'then' block
        String thenCode = node.getThenBlock().accept(this);
        code.append(thenCode).append("\n");

        // Jump to end to avoid executing the 'else' block
        code.append("b ").append(endLabel).append("\n");

        // Generate code for the 'else' block (if it exists)
        code.append(elseLabel).append(":").append("\n");
        if (node.getElseBlock() != null) {
            String elseCode = node.getElseBlock().accept(this);
            code.append(elseCode).append("\n");
        }

        // End of the 'if' statement
        code.append(endLabel).append(":").append("\n");

        return code.toString();
    }

    @Override
    public String visit(LiteralNode node) {
        StringBuilder code = new StringBuilder();

        // Get the literal value
        String literalValue = node.getValue();

        // Allocate a register for the value
        String register = registerAllocator.allocateRegister();

        // Generate code to load the literal into the register
        code.append("li ").append(register).append(", ").append(literalValue).append("\n");

        // Free the register after use
        registerAllocator.freeRegister(register);

        return code.toString();
    }

    @Override
    public String visit(LogicalExprNode node) {
        StringBuilder code = new StringBuilder();

        // Evaluate left and right operands
        String leftCode = node.getLeft().accept(this);
        String leftRegister = "$t0";  // Assume left result is in $t0
        code.append(leftCode).append("\n");

        String rightCode = node.getRight().accept(this);
        String rightRegister = "$t1";  // Assume right result is in $t1
        code.append(rightCode).append("\n");

        // Get the logical operator
        String operator = node.getOperator();
        String resultRegister = "$t2";  // Result will be stored in $t2

        switch (operator) {
            case "&&":
                code.append("and ").append(resultRegister).append(", ").append(leftRegister).append(", ").append(rightRegister).append("\n");
                break;

            case "||":
                code.append("or ").append(resultRegister).append(", ").append(leftRegister).append(", ").append(rightRegister).append("\n");
                break;

            case "!":
                code.append("nor ").append(resultRegister).append(", ").append(leftRegister).append(", ").append(leftRegister).append("\n");
                break;

            default:
                throw new UnsupportedOperationException("Unsupported logical operator: " + operator);
        }

        // Free the registers used for operands
        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);

        return code.toString();
    }

    @Override
    public String visit(RelationalExprNode node) {
        StringBuilder code = new StringBuilder();

        // Evaluate left and right operands
        String leftCode = node.getLeft().accept(this);
        String leftRegister = "$t0";  // Assume left result is in $t0
        code.append(leftCode).append("\n");

        String rightCode = node.getRight().accept(this);
        String rightRegister = "$t1";  // Assume right result is in $t1
        code.append(rightCode).append("\n");

        // Get the relational operator
        String operator = node.getOperator();
        String resultLabelTrue = "label_true_" + node.hashCode();
        String resultLabelFalse = "label_false_" + node.hashCode();

        switch (operator) {
            case "==":
                code.append("beq ").append(leftRegister).append(", ").append(rightRegister).append(", ").append(resultLabelTrue).append("\n");
                break;

            case "!=":
                code.append("bne ").append(leftRegister).append(", ").append(rightRegister).append(", ").append(resultLabelTrue).append("\n");
                break;

            case "<":
                code.append("slt ").append("$t2").append(", ").append(leftRegister).append(", ").append(rightRegister).append("\n");
                code.append("bnez ").append("$t2").append(", ").append(resultLabelTrue).append("\n");
                break;

            case "<=":
                code.append("slt ").append("$t2").append(", ").append(rightRegister).append(", ").append(leftRegister).append("\n");
                code.append("bnez ").append("$t2").append(", ").append(resultLabelTrue).append("\n");
                break;

            case ">":
                code.append("slt ").append("$t2").append(", ").append(rightRegister).append(", ").append(leftRegister).append("\n");
                code.append("bnez ").append("$t2").append(", ").append(resultLabelTrue).append("\n");
                break;

            case ">=":
                code.append("slt ").append("$t2").append(", ").append(leftRegister).append(", ").append(rightRegister).append("\n");
                code.append("beqz ").append("$t2").append(", ").append(resultLabelTrue).append("\n");
                break;

            default:
                throw new UnsupportedOperationException("Unsupported relational operator: " + operator);
        }

        // Generate code for true and false labels
        code.append(resultLabelTrue).append(":").append("\n");
        code.append("li ").append("$t2").append(", 1").append("\n");  // Set result to 1 if true

        code.append(resultLabelFalse).append(":").append("\n");
        code.append("li ").append("$t2").append(", 0").append("\n");  // Set result to 0 if false

        // Free the registers used for operands
        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);

        return code.toString();
    }

    @Override
    public String visit(ReturnNode node) {
        StringBuilder code = new StringBuilder();

        // Check if there is a return value
        if (node.getReturnValue() != null) {
            // Evaluate the return value
            String returnValueCode = node.getReturnValue().accept(this);
            String returnValueRegister = "$t0";  // Assume return value is in $t0
            code.append(returnValueCode).append("\n");

            // Move the return value to $v0
            code.append("move $v0, ").append(returnValueRegister).append("\n");
        }

        // Jump to the return address
        code.append("jr $ra").append("\n");

        // Free the register used for the return value
        registerAllocator.freeRegister("$t0");

        return code.toString();
    }

    @Override
    public String visit(SwitchNode node) {
        StringBuilder code = new StringBuilder();

        // Evaluate the switch expression
        String switchValueCode = node.getExpression().accept(this);
        String switchValueRegister = "$t0";  // Assume switch value is in $t0
        code.append(switchValueCode).append("\n");

        // Create labels for switch cases
        String endSwitchLabel = "end_switch_" + node.hashCode();
        String defaultLabel = "default_case_" + node.hashCode();

        // Iterate over switch cases
        for (SwitchCaseNode caseNode : node.getCases()) {
            String caseLabel = "case_" + caseNode.getCaseValue() + "_" + node.hashCode();
            String caseValueRegister = "$t1";  // Temporary register for case value

            // Compare switch value with case value
            code.append("li ").append(caseValueRegister).append(", ").append(caseNode.getCaseValue()).append("\n");
            code.append("beq ").append(switchValueRegister).append(", ").append(caseValueRegister).append(", ").append(caseLabel).append("\n");

            // Generate code for the case block
            code.append(caseLabel).append(":").append("\n");
            for(ASTNode caseN : caseNode.getStatements()) code.append(caseN.accept(this)).append("\n");

            // Jump to end of switch after case execution
            code.append("j ").append(endSwitchLabel).append("\n");
        }

        // Handle default case (if it exists)
        code.append(defaultLabel).append(":").append("\n");
        if (node.getDefaultCase() != null) {
            String defaultCode = node.getDefaultCase().accept(this);
            code.append(defaultCode).append("\n");
        }

        // End of switch statement
        code.append(endSwitchLabel).append(":").append("\n");

        // Free the registers used for switch value and case value
        registerAllocator.freeRegister("$t0");
        registerAllocator.freeRegister("$t1");

        return code.toString();
    }

    @Override
    public String visit(SwitchCaseNode node) {
        StringBuilder code = new StringBuilder();

        // Generate code for the case statements
        for(ASTNode caseN : node.getStatements()) code.append(caseN.accept(this)).append("\n");
        return code.toString();
    }

    @Override
    public String visit(VarDeclNode node) {
        StringBuilder code = new StringBuilder();

        String varName = node.getVarName();  // Get variable name
        String varType = node.getVarType();  // Get variable type
        ASTNode initValueNode = node.getInitValue();  // Get initial value (if any)

        // Allocate a register for initialization
        String reg = registerAllocator.allocateRegister();

        // If there is an initial value, evaluate it
        if (initValueNode != null) {
            String initValueCode = initValueNode.accept(this);
            code.append(initValueCode).append("\n");

            // Store the value in the variable
            code.append("sw ").append(reg).append(", ").append(varName).append("($sp)").append("\n");
        } else {
            // Initialize variable to 0 if no initial value
            code.append("sw $zero, ").append(varName).append("($sp)").append("\n");
        }

        // Free the register used for initialization
        registerAllocator.freeRegister(reg);

        return code.toString();
    }

    @Override
    public String visit(WhileNode node) {
        StringBuilder code = new StringBuilder();

        // Generate labels for loop start and end
        String startLabel = "while_start_" + node.hashCode();
        String endLabel = "while_end_" + node.hashCode();

        // Label for loop start
        code.append(startLabel).append(":").append("\n");

        // Evaluate the loop condition
        String conditionCode = node.getCondition().accept(this);
        String conditionRegister = "$t0";  // Assume condition result is in $t0
        code.append(conditionCode).append("\n");

        // Jump to end if condition is false
        code.append("beq ").append(conditionRegister).append(", $zero, ").append(endLabel).append("\n");

        // Generate code for loop body
        String bodyCode = node.getBody().accept(this);
        code.append(bodyCode).append("\n");

        // Jump back to loop start
        code.append("j ").append(startLabel).append("\n");

        // Label for loop end
        code.append(endLabel).append(":").append("\n");

        // Free the register used for the condition
        registerAllocator.freeRegister(conditionRegister);

        return code.toString();
    }

    @Override
    public String visit(ProgramNode node) {
        StringBuilder code = new StringBuilder();

        // Generate code for global declarations
        code.append(node.getGlobalDeclarations().accept(this)).append("\n");

        // Generate code for the main function
        code.append(node.getMainFunction().accept(this)).append("\n");

        return code.toString();
    }

    @Override
    public void write(String code) {
        mipsFile.write(code);
    }
}