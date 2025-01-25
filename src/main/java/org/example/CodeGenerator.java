package org.example;

import org.example.node.*;

public interface CodeGenerator {

    // Método para generar código para una declaración de variable
    String varDeclCode(VarDeclNode node, RegisterAllocator regAllocator);

    // Método para generar código para una operación aritmética (suma, resta, etc.)
    String arithmeticExprCode(ArithmeticExprNode node, RegisterAllocator regAllocator);

    // Método para generar código para una asignación
    String assignmentCode(AssignNode node, RegisterAllocator regAllocator);

    // Método para generar código para una expresión relacional (comparaciones)
    String relationalExprCode(RelationalExprNode node, RegisterAllocator regAllocator);

    // Método para generar código para una expresión lógica (AND, OR, NOT)
    String logicalExprCode(LogicalExprNode node, RegisterAllocator regAllocator);

    // Método para generar código para un bloque if
    String ifStatementCode(IfNode node, RegisterAllocator regAllocator);

    // Método para generar código para un bloque while
    String whileStatementCode(WhileNode node, RegisterAllocator regAllocator);

    // Método para generar código para un bloque for
    String forStatementCode(ForNode node, RegisterAllocator regAllocator);

    // Método para generar código para un switch
    String switchStatementCode(SwitchNode node, RegisterAllocator regAllocator);

    // Método para generar código para una llamada a función
    String functionCallCode(FunctionCallNode node, RegisterAllocator regAllocator);

    // Método para generar código para una declaración de arreglo
    String arrayDeclCode(ArrayDeclNode node, RegisterAllocator regAllocator);

    // Método para generar código para acceder a un arreglo
    String arrayAccessCode(ArrayAccessNode node, RegisterAllocator regAllocator);

    // Método para generar código para un literal (entero, flotante, cadena, etc.)
    String literalCode(LiteralNode node, RegisterAllocator regAllocator);

    // Método para generar código para la declaración de funciones
    String funcDeclCode(FuncDeclNode node, RegisterAllocator regAllocator);

    // Método para generar código para la ejecución de una función
    String funcExecutionCode(FuncExecNode node, RegisterAllocator regAllocator);

    // Método para generar código de retorno (return)
    String returnCode(ReturnNode node, RegisterAllocator regAllocator);

    // Método para manejar el código en caso de error
    String errorCode(ErrorNode node);
}
