package org.example;

import org.example.Lexer;
import org.example.SymbolInfo;
import org.example.SymbolTable;
import org.example.sym;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            String filename = "test.txt";
            String path = Paths.get(Objects.requireNonNull(Main.class.getClassLoader().getResource(filename)).toURI()).toString();
            FileReader reader = new FileReader(path);

            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer);
            parser.parse();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
