package org.example.register;

import java.util.Stack;

public class MIPSRegisterAllocator implements RegisterAllocator {
    private Stack<String> availableRegisters = new Stack<>();

    public MIPSRegisterAllocator() {
        // Inicializar con los registros de propósito general en MIPS
        availableRegisters.push("$t9");
        availableRegisters.push("$t8");
        availableRegisters.push("$t7");
        availableRegisters.push("$t6");
        availableRegisters.push("$t5");
        availableRegisters.push("$t4");
        availableRegisters.push("$t3");
        availableRegisters.push("$t2");
        availableRegisters.push("$t1");
        availableRegisters.push("$t0");
    }

    // Asigna un registro libre
    public String allocateRegister() {
        if (availableRegisters.isEmpty()) {
            throw new RuntimeException("No hay registros disponibles.");
        }
        return availableRegisters.pop();
    }

    // Libera un registro cuando ya no se necesita
    public void freeRegister(String register) {
        availableRegisters.push(register);
    }
}
