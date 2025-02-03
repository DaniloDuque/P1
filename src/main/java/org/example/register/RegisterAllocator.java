package org.example.register;

public interface RegisterAllocator {
    String allocateRegister();
    void freeRegister(String registerName);
}
