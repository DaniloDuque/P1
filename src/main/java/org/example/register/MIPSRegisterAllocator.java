package org.example.register;

import java.util.Stack;

public class MIPSRegisterAllocator implements RegisterAllocator {
    private Stack<String> availableRegisters = new Stack<>();
    private Stack<String> availableFloatRegisters = new Stack<>();

    public MIPSRegisterAllocator() {
        // Initialize with all general-purpose temporary and saved registers
        for (int i = 9; i >= 0; i--) {
            availableRegisters.push("$t" + i); // $t0 to $t9
        }
        for (int i = 7; i >= 0; i--) {
            availableRegisters.push("$s" + i); // $s0 to $s7
        }

        // Initialize with all floating-point registers
        for (int i = 31; i >= 0; i--) {
            availableFloatRegisters.push("$f" + i); // $f0 to $f31
        }
    }

    // Allocate a general-purpose register
    public String allocateRegister() {
        if (availableRegisters.isEmpty()) {
            throw new RuntimeException("No general-purpose registers available.");
        }
        return availableRegisters.pop();
    }

    // Allocate a floating-point register
    public String allocateFloatRegister() {
        if (availableFloatRegisters.isEmpty()) {
            throw new RuntimeException("No floating-point registers available.");
        }
        return availableFloatRegisters.pop();
    }

    // Free a general-purpose register
    public void freeRegister(String register) {
        if(register==null) return;
        if (register.startsWith("$t") || register.startsWith("$s")) {
            availableRegisters.push(register);
        } else {
            throw new IllegalArgumentException("Invalid general-purpose register: " + register);
        }
    }

    // Free a floating-point register
    public void freeFloatRegister(String register) {
        if (register.startsWith("$f")) {
            availableFloatRegisters.push(register);
        } else {
            throw new IllegalArgumentException("Invalid floating-point register: " + register);
        }
    }
}