package org.example.generator;

import org.example.node.*;
import org.example.register.MIPSRegisterAllocator;
import org.example.register.RegisterAllocator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MIPSCodeGenerator implements ASTVisitor {

    private RegisterAllocator registerAllocator = new MIPSRegisterAllocator();
    private int labelCounter = 0;
    private Writer codeWriter;

    public MIPSCodeGenerator(String outputName) {
        codeWriter = new org.example.generator.FileWriter(outputName);
    }

    private String generateUniqueLabel(String base) {
        return base + "_" + (labelCounter++);
    }

    @Override
    public void visit(ArithmeticExprNode node) {
        // Obtener los operandos izquierdo y derecho
        node.getLeft().accept(this);
        String leftRegister = registerAllocator.allocateRegister();  // Asignar un registro para el operando izquierdo

        node.getRight().accept(this);
        String rightRegister = registerAllocator.allocateRegister();  // Asignar un registro para el operando derecho

        // Asignar un registro para el resultado
        String resultRegister = registerAllocator.allocateRegister();

        // Realizar la operación aritmética basada en el operador
        String operator = node.getOperator();

        switch (operator) {
            case "+":
                // Sumar: left + right -> result
                codeWriter.write("add " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;

            case "-":
                // Restar: left - right -> result
                codeWriter.write("sub " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;

            case "*":
                // Multiplicar: left * right -> result
                codeWriter.write("mul " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;

            case "/":
                // Dividir: left / right -> result
                codeWriter.write("div " + leftRegister + ", " + rightRegister);
                codeWriter.write("mflo " + resultRegister);  // Guardar el cociente en result
                break;

            case "%":
                // Módulo: left % right -> result
                codeWriter.write("div " + leftRegister + ", " + rightRegister);
                codeWriter.write("mfhi " + resultRegister);  // Guardar el residuo en result
                break;

            default:
                throw new UnsupportedOperationException("Operador aritmético no soportado: " + operator);
        }

        // Liberar los registros usados para los operandos
        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);
    }

    @Override
    public void visit(ArrayAccessNode node) {
        // Visitar el nodo que representa la variable de arreglo
        node.getArray().accept(this);
        String arrayBaseRegister = registerAllocator.allocateRegister();  // Asignar un registro para la base del arreglo

        // Visitar el nodo que representa el índice
        node.getIndex().accept(this);
        String indexRegister = registerAllocator.allocateRegister();  // Asignar un registro para el índice

        // Multiplicar el índice por el tamaño de cada elemento (asumimos 4 bytes por elemento, si es un int)
        String tempRegister = registerAllocator.allocateRegister();  // Registro temporal para almacenar el desplazamiento
        codeWriter.write("sll " + tempRegister + ", " + indexRegister + ", 2");  // index * 4

        // Sumar el desplazamiento al registro base del arreglo
        String addressRegister = registerAllocator.allocateRegister();  // Registro para la dirección del elemento
        codeWriter.write("add " + addressRegister + ", " + arrayBaseRegister + ", " + tempRegister);

        // Cargar el valor del arreglo en la dirección calculada
        String valueRegister = registerAllocator.allocateRegister();  // Registro para el valor del elemento
        codeWriter.write("lw " + valueRegister + ", 0(" + addressRegister + ")");

        // Liberar los registros usados para la base del arreglo, el índice y el temporal
        registerAllocator.freeRegister(arrayBaseRegister);
        registerAllocator.freeRegister(indexRegister);
        registerAllocator.freeRegister(tempRegister);
    }

    @Override
    public void visit(ArrayDeclNode node) {
        String arrayName = node.getArrayName();   // Obtener el nombre del arreglo
        int arraySize = node.getArraySize();      // Obtener el tamaño del arreglo
        String arrayType = node.getArrayType();   // Obtener el tipo del arreglo (para determinar tamaño)

        // Determinar el tamaño de los elementos basados en el tipo
        int elementSize;
        switch (arrayType) {
            case "ENTERO":
                elementSize = 4;  // Un entero ocupa 4 bytes en MIPS
                break;
            case "FLOTANTE":
                elementSize = 4;  // Un flotante ocupa 4 bytes en MIPS
                break;
            case "CARACTER":
                elementSize = 1;  // Un carácter ocupa 1 byte
                break;
            case "BOOLEANO":
                elementSize = 1;  // Un booleano ocupa 1 byte
                break;
            default:
                throw new UnsupportedOperationException("Tipo de dato no soportado: " + arrayType);
        }

        // Calcular el tamaño total del arreglo
        int totalSize = arraySize * elementSize;

        // Asumimos que el arreglo es una variable global o local (dependiendo de tu implementación)
        // Por ejemplo, aquí asumimos que es una variable local y reservamos espacio en la pila.
        // Si es global, lo declararías en la sección de datos.

        // Reservar espacio en la pila para el arreglo local
        codeWriter.write("subu $sp, $sp, " + totalSize + "   # Reservar espacio para arreglo " + arrayName);

        // Guardar la dirección base del arreglo en un registro (por ejemplo, $t0)
        codeWriter.write("move $t0, $sp   # Guardar dirección base del arreglo " + arrayName);

        // Si hay inicialización de valores, puedes recorrer e inicializarlos aquí (opcional)
        // Por ejemplo, si el arreglo tiene valores predefinidos, puedes cargarlos en los índices
        // e.g., inicializar con 0
        for (int i = 0; i < arraySize; i++) {
            codeWriter.write("li $t1, 0   # Inicializar elemento " + i + " del arreglo con 0");
            codeWriter.write("sw $t1, " + (i * elementSize) + "($t0)   # Guardar valor en el arreglo");
        }

        // Si el arreglo es global, debemos generar código en la sección de datos.
        /*
        .data
        arrayName: .space totalSize
        */
    }

    @Override
    public void visit(AssignNode node) {
        // Evaluar la expresión del lado derecho
        node.getRight().accept(this);  // Visitar el nodo derecho para obtener el valor que se va a asignar
        String valueRegister = registerAllocator.allocateRegister();  // Asignamos un registro para el valor

        // Evaluar el lado izquierdo (puede ser una variable o un acceso a arreglo)
        if (node.getLeft() instanceof VarDeclNode) {
            VarDeclNode varNode = (VarDeclNode) node.getLeft();
            String varName = varNode.getVarName();  // Obtener el nombre de la variable (ajusta el método según sea necesario)

            // Guardar el valor en la variable
            codeWriter.write("sw " + valueRegister + ", " + varName + "   # Guardar valor en la variable " + varName);

        } else if (node.getLeft() instanceof ArrayAccessNode) {
            // Si es un acceso a arreglo, necesitamos calcular la dirección del índice
            ArrayAccessNode arrayAccess = (ArrayAccessNode) node.getLeft();
            arrayAccess.accept(this);  // Visitar el nodo del acceso al arreglo para obtener su dirección

            // Asumimos que la dirección del índice se encuentra en un registro temporal (e.g., $t0)
            String arrayBaseRegister = "$t0";  // Suponiendo que la dirección base del arreglo está en $t0
            codeWriter.write("sw " + valueRegister + ", 0(" + arrayBaseRegister + ")   # Guardar valor en el índice del arreglo");

        } else {
            throw new UnsupportedOperationException("Asignación no soportada para este tipo de nodo.");
        }

        // Liberar el registro utilizado para almacenar el valor
        registerAllocator.freeRegister(valueRegister);
    }

    @Override
    public void visit(ErrorNode node) {
        // Obtener el mensaje de error asociado con este nodo, si existe
        String errorMessage = node.getErrorMessage();  // Este método debería devolver el mensaje de error

        // Generar un comentario en el código de salida indicando un error
        codeWriter.write("# ERROR: " + errorMessage);

        // También podemos imprimir un error en la consola para que el usuario lo vea
        System.err.println("Error en el nodo de tipo: " + node.getClass().getSimpleName() + " - " + errorMessage);

        // O lanzar una excepción si deseas que la generación de código se detenga
        throw new RuntimeException("Error en el AST: " + errorMessage);
    }

    @Override
    public void visit(ForNode node) {
        // Generar código para la inicialización del bucle (e.g., int i = 0)
        node.getInitialization().accept(this);

        // Crear etiquetas únicas para el inicio del bucle y la salida
        String loopStartLabel = generateUniqueLabel("for_loop_start");
        String loopEndLabel = generateUniqueLabel("for_loop_end");

        // Etiqueta para el inicio del bucle
        codeWriter.write(loopStartLabel + ":");

        // Generar código para la condición del bucle (e.g., i < 10)
        node.getCondition().accept(this);
        String conditionRegister = registerAllocator.allocateRegister();  // Registro donde se almacena el resultado de la condición
        codeWriter.write("beqz " + conditionRegister + ", " + loopEndLabel);  // Saltar al final si la condición es falsa (0)

        // Generar código para el cuerpo del bucle
        node.getBody().accept(this);

        // Generar código para el incremento (e.g., i++)
        node.getUpdate().accept(this);

        // Saltar al inicio del bucle para la siguiente iteración
        codeWriter.write("j " + loopStartLabel);

        // Etiqueta para el final del bucle
        codeWriter.write(loopEndLabel + ":");

        // Liberar los registros utilizados
        registerAllocator.freeRegister(conditionRegister);
    }

    @Override
    public void visit(FuncDeclNode node) {
        // Obtener el nombre de la función
        String functionName = node.getFunctionName();

        // Escribir la etiqueta de la función
        codeWriter.write(functionName + ":");

        // Guardar los registros que se usan
        codeWriter.write("sw $ra, 0($sp)");  // Guardar el registro de retorno
        codeWriter.write("addi $sp, $sp, -4"); // Ajustar el stack pointer

        // Asignar los parámetros a los registros correspondientes
        List<String> parameters = node.getParameters();
        for (int i = 0; i < parameters.size(); i++) {
            String register = registerAllocator.allocateRegister();
            codeWriter.write("sw " + register + ", " + (i * 4) + "($sp)"); // Guardar el parámetro en el stack
        }

        // Generar el código del cuerpo de la función
        node.getBody().accept(this);

        // Restaurar el stack pointer y los registros
        codeWriter.write("addi $sp, $sp, " + (parameters.size() * 4));
        codeWriter.write("lw $ra, 0($sp)");  // Restaurar el registro de retorno

        // Generar el retorno de la función
        codeWriter.write("jr $ra");
    }

    @Override
    public void visit(FuncExecNode node) {
        // Obtener el nombre de la función a la que se va a llamar
        String functionName = node.getFunctionName();

        // Obtener los argumentos para la función
        List<ASTNode> arguments = node.getArguments();

        // Asignar los primeros 4 argumentos a los registros $a0, $a1, $a2, $a3
        int argIndex = 0;
        for (; argIndex < Math.min(4, arguments.size()); argIndex++) {
            arguments.get(argIndex).accept(this);  // Visitar el nodo del argumento
            String argRegister = "$a" + argIndex;
            // Asumimos que el valor del argumento está en un registro temporal ($t0, $t1, $t2, etc.)
            codeWriter.write("move " + argRegister + ", " + "$t" + argIndex);  // Mover el valor al registro de argumento correspondiente
        }

        // Si hay más de 4 argumentos, pasarlos en el stack
        if (arguments.size() > 4) {
            // Reservamos espacio en el stack para los argumentos adicionales
            int extraArgsCount = arguments.size() - 4;
            codeWriter.write("sub $sp, $sp, " + (extraArgsCount * 4));  // Reducir el puntero del stack

            // Guardamos los argumentos adicionales en el stack
            for (int i = 4; i < arguments.size(); i++) {
                arguments.get(i).accept(this);  // Visitar el argumento
                String tempRegister = "$t" + i;  // Suponemos que el valor está en un registro temporal
                codeWriter.write("sw " + tempRegister + ", " + (i - 4) * 4 + "($sp)");  // Guardamos el valor en el stack
            }
        }

        // Realizar la llamada a la función
        codeWriter.write("jal " + functionName);  // Llamada a la función usando "jal" (Jump and Link)

        // Restaurar el puntero de stack si modificamos el stack
        if (arguments.size() > 4) {
            codeWriter.write("add $sp, $sp, " + (arguments.size() - 4) * 4);  // Restaurar el puntero del stack
        }
    }

    @Override
    public void visit(FunctionCallNode node) {
        // Obtener el nombre de la función a la que se va a llamar
        String functionName = node.getFunctionName();

        // Obtener los argumentos para la función
        List<ASTNode> arguments = node.getArguments();

        // Asignar los primeros 4 argumentos a los registros $a0, $a1, $a2, $a3
        int argIndex = 0;
        for (; argIndex < Math.min(4, arguments.size()); argIndex++) {
            arguments.get(argIndex).accept(this);  // Visitar el nodo del argumento
            String argRegister = "$a" + argIndex;
            // Asumimos que el valor del argumento está en un registro temporal ($t0, $t1, $t2, etc.)
            codeWriter.write("move " + argRegister + ", " + "$t" + argIndex);  // Mover el valor al registro de argumento correspondiente
        }

        // Si hay más de 4 argumentos, pasarlos en el stack
        if (arguments.size() > 4) {
            // Reservamos espacio en el stack para los argumentos adicionales
            int extraArgsCount = arguments.size() - 4;
            codeWriter.write("sub $sp, $sp, " + (extraArgsCount * 4));  // Reducir el puntero del stack

            // Guardamos los argumentos adicionales en el stack
            for (int i = 4; i < arguments.size(); i++) {
                arguments.get(i).accept(this);  // Visitar el argumento
                String tempRegister = "$t" + i;  // Suponemos que el valor está en un registro temporal
                codeWriter.write("sw " + tempRegister + ", " + (i - 4) * 4 + "($sp)");  // Guardamos el valor en el stack
            }
        }

        // Llamada a la función
        codeWriter.write("jal " + functionName);  // Llamada a la función usando "jal" (Jump and Link)

        // Recuperar el valor de retorno si la función lo tiene
        // Suponemos que el valor de retorno está en el registro $v0
        String returnValueRegister = "$v0";  // El valor de retorno se encuentra en $v0
        String resultRegister = "$t0";  // Usamos un registro temporal para almacenar el valor de retorno
        codeWriter.write("move " + resultRegister + ", " + returnValueRegister);  // Mover el valor de retorno al registro temporal

        // Restaurar el puntero de stack si modificamos el stack
        if (arguments.size() > 4) {
            codeWriter.write("add $sp, $sp, " + (arguments.size() - 4) * 4);  // Restaurar el puntero del stack
        }
    }

    @Override
    public void visit(IfNode node) {
        // 1. Evaluar la expresión condicional
        node.getCondition().accept(this);  // Evaluamos la condición

        // Supongamos que la condición resultante está en $t0
        String conditionRegister = "$t0";  // El registro que contiene la condición evaluada

        // 2. Crear etiquetas para los saltos
        String elseLabel = "else_label_" + node.hashCode();  // Etiqueta para el bloque 'else'
        String endLabel = "end_label_" + node.hashCode();    // Etiqueta para el final del 'if'

        // 3. Realizar salto condicional
        // Dependiendo del tipo de comparación en la condición, usamos "beq" (igual) o "bne" (no igual)
        // En este caso, suponemos que la condición es una comparación de igualdad
        codeWriter.write("beq " + conditionRegister + ", $zero, " + elseLabel);  // Si la condición es falsa, saltamos al 'else'

        // 4. Código para el bloque 'then'
        node.getThenBlock().accept(this);  // Evaluamos el bloque 'then'

        // 5. Salto al final para evitar ejecutar el bloque 'else' si la condición es verdadera
        codeWriter.write("b " + endLabel);  // Salto al final del 'if'

        // 6. Código para el bloque 'else' (si existe)
        codeWriter.write(elseLabel + ":");  // Definimos la etiqueta 'else'
        if (node.getElseBlock() != null) {
            node.getElseBlock().accept(this);  // Evaluamos el bloque 'else'
        }

        // 7. Finalizar el 'if' con la etiqueta de fin
        codeWriter.write(endLabel + ":");  // Definimos la etiqueta 'end'
    }

    @Override
    public void visit(LiteralNode node) {
        // Obtener el valor del literal del nodo
        String literalValue = node.getValue();  // Suponemos que el valor es un String representando el número

        // Asignar un registro para cargar el valor
        String register = registerAllocator.allocateRegister();  // Obtener un registro libre

        // Generar código para cargar el literal en el registro
        codeWriter.write("li " + register + ", " + literalValue);  // Instrucción MIPS para cargar el literal en el registro

        // Devolver el registro para que se pueda liberar más tarde
        registerAllocator.freeRegister(register);
    }

    @Override
    public void visit(LogicalExprNode node) {
        // Obtener los operandos izquierdo y derecho
        node.getLeft().accept(this);  // Visitar el operando izquierdo
        String leftRegister = "$t0";   // Suponemos que el resultado del operando izquierdo se guarda en $t0

        node.getRight().accept(this);  // Visitar el operando derecho
        String rightRegister = "$t1";  // Suponemos que el resultado del operando derecho se guarda en $t1

        // Obtener el operador lógico (AND, OR, NOT, etc.)
        String operator = node.getOperator();
        String resultRegister = "$t2";  // El resultado se guardará en $t2

        switch (operator) {
            case "&&":  // AND lógico
                codeWriter.write("and " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;

            case "||":  // OR lógico
                codeWriter.write("or " + resultRegister + ", " + leftRegister + ", " + rightRegister);
                break;

            case "!":   // NOT lógico (unaria)
                // NOT lógico: se realiza con una operación NOR (OR de un valor con su complemento)
                codeWriter.write("nor " + resultRegister + ", " + leftRegister + ", " + leftRegister);
                break;

            default:
                throw new UnsupportedOperationException("Operador lógico no soportado: " + operator);
        }

        // Liberar los registros usados para los operandos
        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);
    }

    @Override
    public void visit(RelationalExprNode node) {
        // Obtener los operandos izquierdo y derecho
        node.getLeft().accept(this);  // Visitar el operando izquierdo
        String leftRegister = "$t0";   // Suponemos que el resultado del operando izquierdo se guarda en $t0

        node.getRight().accept(this);  // Visitar el operando derecho
        String rightRegister = "$t1";  // Suponemos que el resultado del operando derecho se guarda en $t1

        // Obtener el operador relacional (==, !=, <, <=, >, >=)
        String operator = node.getOperator();
        String resultLabelTrue = "label_true";   // Etiqueta para el salto condicional si la comparación es verdadera
        String resultLabelFalse = "label_false"; // Etiqueta para el salto condicional si la comparación es falsa

        switch (operator) {
            case "==":  // Igualdad
                codeWriter.write("beq " + leftRegister + ", " + rightRegister + ", " + resultLabelTrue);
                break;

            case "!=":  // Desigualdad
                codeWriter.write("bne " + leftRegister + ", " + rightRegister + ", " + resultLabelTrue);
                break;

            case "<":  // Menor que
                codeWriter.write("slt " + "$t2" + ", " + leftRegister + ", " + rightRegister);  // $t2 = 1 si left < right
                codeWriter.write("bnez " + "$t2" + ", " + resultLabelTrue);  // Salta a la etiqueta si $t2 no es 0
                break;

            case "<=":  // Menor o igual que
                codeWriter.write("slt " + "$t2" + ", " + rightRegister + ", " + leftRegister);  // $t2 = 1 si right < left
                codeWriter.write("bnez " + "$t2" + ", " + resultLabelTrue);  // Salta a la etiqueta si $t2 no es 0
                break;

            case ">":  // Mayor que
                codeWriter.write("slt " + "$t2" + ", " + rightRegister + ", " + leftRegister);  // $t2 = 1 si right < left
                codeWriter.write("bnez " + "$t2" + ", " + resultLabelTrue);  // Salta a la etiqueta si $t2 no es 0
                break;

            case ">=":  // Mayor o igual que
                codeWriter.write("slt " + "$t2" + ", " + leftRegister + ", " + rightRegister);  // $t2 = 1 si left < right
                codeWriter.write("beqz " + "$t2" + ", " + resultLabelTrue);  // Salta a la etiqueta si $t2 es 0
                break;

            default:
                throw new UnsupportedOperationException("Operador relacional no soportado: " + operator);
        }

        // Generar el código para la etiqueta de salto verdadero (si la condición es verdadera)
        codeWriter.write(resultLabelTrue + ":");
        codeWriter.write("li " + "$t2" + ", 1");  // Asignar 1 a $t2 si la comparación es verdadera

        // Generar el código para la etiqueta de salto falso (si la condición es falsa)
        codeWriter.write(resultLabelFalse + ":");
        codeWriter.write("li " + "$t2" + ", 0");  // Asignar 0 a $t2 si la comparación es falsa

        // Liberar los registros usados para los operandos
        registerAllocator.freeRegister(leftRegister);
        registerAllocator.freeRegister(rightRegister);
    }

    @Override
    public void visit(ReturnNode node) {
        // Verificar si hay una expresión de retorno
        if (node.getReturnValue() != null) {
            // Evaluar la expresión de retorno
            node.getReturnValue().accept(this);  // Visitar el nodo de retorno (la expresión)
            String returnValueRegister = "$t0";  // Supongamos que el valor de retorno está en $t0

            // Colocar el valor de retorno en $v0 (registro para valor de retorno en MIPS)
            codeWriter.write("move $v0, " + returnValueRegister);
        }

        // Realizar el salto a la dirección de retorno (el valor está en $ra)
        codeWriter.write("jr $ra");

        // Liberar los registros usados
        registerAllocator.freeRegister("$t0");
    }

    @Override
    public void visit(SwitchNode node) {
        // Evaluar la expresión del switch (colocarla en un registro temporal)
        node.getExpression().accept(this);  // Evaluamos la expresión del switch
        String switchValueRegister = "$t0"; // Suponemos que el valor del switch está en $t0

        // Crear etiquetas para cada caso
        String endSwitchLabel = "end_switch_" + node.hashCode();
        String defaultLabel = "default_case_" + node.hashCode();

        // Comenzar la evaluación de los casos
        boolean hasDefault = false;

        // Iterar sobre todos los casos del switch
        for (SwitchCaseNode caseNode : node.getCases()) {
            String caseLabel = "case_" + caseNode.getCaseValue() + "_" + node.hashCode();
            String caseValueRegister = "$t1";  // Usamos otro registro temporal

            // Comparar el valor de la expresión del switch con el valor del caso
            codeWriter.write("li " + caseValueRegister + ", " + caseNode.getCaseValue()); // Cargar el valor del caso en un registro
            codeWriter.write("beq " + switchValueRegister + ", " + caseValueRegister + ", " + caseLabel);  // Si son iguales, saltamos al caso

            // Generar el código para el caso (lo que se ejecutará si este caso es seleccionado)
            codeWriter.write(caseLabel + ":");
            caseNode.getStatements().forEach(statement -> {
                statement.accept(this);  // Generar el código para las instrucciones dentro del caso
            });

            // Saltar al final del switch después de un caso exitoso
            codeWriter.write("j " + endSwitchLabel);
        }

        // Manejar el caso "default" si existe
        for (SwitchCaseNode caseNode : node.getCases()) {
            if (caseNode.isDefault()) {
                hasDefault = true;
                codeWriter.write(defaultLabel + ":");
                caseNode.getStatements().forEach(statement -> {
                    statement.accept(this);  // Generar el código para las instrucciones dentro del default
                });
            }
        }

        // Etiqueta final del switch
        codeWriter.write(endSwitchLabel + ":");

        // Liberar los registros temporales utilizados
        registerAllocator.freeRegister("$t0");
        registerAllocator.freeRegister("$t1");
    }

    @Override
    public void visit(SwitchCaseNode node) {

    }

    @Override
    public void visit(VarDeclNode node) {
        String varName = node.getVarName();  // Nombre de la variable
        String varType = node.getVarType();  // Tipo de la variable (por ejemplo, "int")
        ASTNode initValueNode = node.getInitValue();  // Valor inicial de la variable (puede ser null si no hay inicialización)

        // Si la variable es de tipo entero, asignamos espacio en la pila o segmento de datos.
        String reg = registerAllocator.allocateRegister();  // Asignamos un registro temporal para la inicialización

        // Si la variable tiene un valor inicial, lo procesamos
        if (initValueNode != null) {
            initValueNode.accept(this);  // Evaluar el valor inicial
            // Después de la evaluación, el valor estará en un registro (por ejemplo, $t0)
            codeWriter.write("sw " + reg + ", " + varName + "($sp)");  // Guardamos el valor en la variable
        } else {
            // Si no tiene valor inicial, simplemente reservamos espacio en la pila
            codeWriter.write("sw $zero, " + varName + "($sp)");  // Inicializamos a 0
        }

        // Liberamos el registro temporal después de usarlo
        registerAllocator.freeRegister(reg);
    }

    @Override
    public void visit(WhileNode node) {
        // Generamos una etiqueta para el inicio del bucle
        String startLabel = "while_start_" + node.hashCode();  // Etiqueta única para el inicio del bucle
        String endLabel = "while_end_" + node.hashCode();  // Etiqueta única para el fin del bucle

        // Escribimos la etiqueta de inicio del bucle
        codeWriter.write(startLabel + ":");

        // Evaluamos la condición del while
        node.getCondition().accept(this);  // Evaluar la expresión booleana del while
        // Asumimos que el resultado de la evaluación está en $t0 (puedes modificar esto según tu implementación)
        codeWriter.write("beq $t0, $zero, " + endLabel);  // Si la condición es falsa, saltamos al final del bucle

        // Si la condición es verdadera, generamos el código para el cuerpo del while
        node.getBody().accept(this);  // Ejecutamos el cuerpo del while

        // Al final del cuerpo del while, saltamos de nuevo a la evaluación de la condición
        codeWriter.write("j " + startLabel);  // Volver al inicio del bucle para evaluar la condición nuevamente

        // Escribimos la etiqueta de fin del bucle
        codeWriter.write(endLabel + ":");
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
