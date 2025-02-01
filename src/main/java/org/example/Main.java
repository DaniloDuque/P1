package org.example;

import org.example.cup.Parser;
import org.example.lexer.*;
import org.example.table.SymbolInfo;

import java.beans.JavaBean;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Main {
    public static void main(String[] args) {
        try {
            String filename = args[0];
            String path = Paths.get(Objects.requireNonNull(Main.class.getClassLoader().getResource(filename)).toURI()).toString();
            FileReader reader = new FileReader(path);

            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer, lexer.getSymbolTable());
            parser.parse();

            FileWriter writer = new FileWriter("Tokens.txt");
            writer.write("Tokens encontrados\n");
            for (Map.Entry<String, List<SymbolInfo>> entry : lexer.getSymbolTable().getSymbols().entrySet()) {
                for (SymbolInfo symbolInfo : entry.getValue()) {
                    writer.write("Lexema: " + symbolInfo.getLexema() + "\n");
                }
            }
            writer.close();

            writer = new FileWriter("SymbolTable.txt");
            writer.write("Tabla de símbolos\n");
            for (Map.Entry<String, List<SymbolInfo>> entry : lexer.getSymbolTable().getSymbols().entrySet()) {
                for (SymbolInfo symbolInfo : entry.getValue()) {
                    System.out.println(symbolInfo + "\n");
                    writer.write(symbolInfo + "\n");
                }
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
