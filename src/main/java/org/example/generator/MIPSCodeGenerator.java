package org.example.generator;

import org.example.node.*;
import org.example.register.MIPSRegisterAllocator;
import org.example.register.RegisterAllocator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MIPSCodeGenerator implements ASTVisitor {

    private RegisterAllocator registerAllocator = new MIPSRegisterAllocator();

    private Writer codeWriter;

    public MIPSCodeGenerator(String outputName) {
        codeWriter = new org.example.generator.FileWriter(outputName);
    }

    @Override
    public void visit(ArithmeticExprNode node) {
        // Obtener los operandos izquierdo y derecho
        node.getLeft().accept(this);  // Visitar el operando izquierdo
        String leftRegister = "$t0";  // Supongamos que guardamos el resultado en $t0

        node.getRight().accept(this); // Visitar el operando derecho
        String rightRegister = "$t1";  // Supongamos que guardamos el resultado en $t1

        // Realizar la operación aritmética basada en el operador
        String operator = node.getOperator();
        String resultRegister = "$t2";  // Supongamos que el resultado se guarda en $t2

        switch (operator) {
            case "+":
                // Sumar: $t0 + $t1 -> $t2
                codeWriter.write("add " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;

            case "-":
                // Restar: $t0 - $t1 -> $t2
                codeWriter.write("sub " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;

            case "*":
                // Multiplicar: $t0 * $t1 -> $t2
                codeWriter.write("mul " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;

            case "/":
                // Dividir: $t0 / $t1 -> $t2
                codeWriter.write("div " + leftRegister + ", " + rightRegister);
                codeWriter.write("mflo " + resultRegister);  // Guardar el resultado de la división en $t2
                break;

            case "%":
                // Modulo: $t0 % $t1 -> $t2
                codeWriter.write("div " + leftRegister + ", " + rightRegister);
                codeWriter.write("mfhi " + resultRegister);  // Guardar el residuo en $t2
                break;

            case "^":
                // Potencia (implementación simple): $t0 ^ $t1 -> $t2
                // Este es solo un ejemplo, MIPS no tiene una instrucción de potencia nativa
                codeWriter.write("pow " + leftRegister + ", " + rightRegister);  // Puede que necesites implementar esto
                break;

            default:
                throw new UnsupportedOperationException("Operador aritmético no soportado: " + operator);
        }
    }

    @Override
    public void visit(ArrayAccessNode node) {

    }

    @Override
    public void visit(ArrayDeclNode node) {

    }

    @Override
    public void visit(AssignNode node) {

    }

    @Override
    public void visit(ErrorNode node) {

    }

    @Override
    public void visit(ForNode node) {

    }

    @Override
    public void visit(FuncDeclNode node) {

    }

    @Override
    public void visit(FuncExecNode node) {

    }

    @Override
    public void visit(FunctionCallNode node) {

    }

    @Override
    public void visit(IfNode node) {

    }

    @Override
    public void visit(LiteralNode node) {

    }

    @Override
    public void visit(LogicalExprNode node) {

    }

    @Override
    public void visit(RelationalExprNode node) {

    }

    @Override
    public void vist(ReturnNode node) {

    }

    @Override
    public void visit(SwitchNode node) {

    }

    @Override
    public void visit(VarDeclNode node) {

    }

    @Override
    public void visit(WhileNode node) {

    }

    @Override
    public void write(File file, String code) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(code);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
