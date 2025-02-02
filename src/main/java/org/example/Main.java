package org.example;

import org.example.cup.Parser;
import org.example.lexer.*;
import org.example.node.ProgramNode;
import org.example.generator.MIPSCodeGenerator;
import org.example.table.SymbolInfo;
import java_cup.runtime.Symbol;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        try {
            // Read the input file
            String filename = args[0];
            String path = Paths.get(Objects.requireNonNull(Main.class.getClassLoader().getResource(filename)).toURI()).toString();
            FileReader reader = new FileReader(path);

            // Initialize the lexer and parser
            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer, lexer.getSymbolTable());

            // Parse the input file and generate the AST
            Symbol parseResult = parser.parse(); // Parse returns a Symbol object
            ProgramNode astRoot = (ProgramNode) parseResult.value; // Extract the ProgramNode from the Symbol

            // Write tokens to Tokens.txt
            FileWriter writer = new FileWriter("Tokens.txt");
            writer.write("Tokens encontrados\n");
            for (Map.Entry<String, List<SymbolInfo>> entry : lexer.getSymbolTable().getSymbols().entrySet()) {
                for (SymbolInfo symbolInfo : entry.getValue()) {
                    writer.write("Lexema: " + symbolInfo.getLexema() + "\n");
                }
            }
            writer.close();

            // Write the symbol table to SymbolTable.txt
            writer = new FileWriter("SymbolTable.txt");
            writer.write("Tabla de símbolos\n");
            for (Map.Entry<String, List<SymbolInfo>> entry : lexer.getSymbolTable().getSymbols().entrySet()) {
                for (SymbolInfo symbolInfo : entry.getValue()) {
                    System.out.println(symbolInfo + "\n");
                    writer.write(symbolInfo + "\n");
                }
            }
            writer.close();

            // Generate MIPS code from the AST
            MIPSCodeGenerator codeGenerator = new MIPSCodeGenerator(lexer.getDataSegment()); // Initialize the code generator
            astRoot.accept(codeGenerator); // Traverse the AST and generate MIPS code
            System.out.println("MIPS code generated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}