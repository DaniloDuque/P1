package org.example;

import java.io.InputStreamReader;
import java.io.IOException;
import java_cup.Lexer;
import org.example.Parser;
    
public class Main {
    public static void main(String[] args) {
        try {
            InputStreamReader fileReader = new InputStreamReader(
                    Main.class.getClassLoader().getResourceAsStream("test.txt"));
            if (fileReader == null) {
                System.err.println("Error: No se pudo encontrar el archivo test.txt en los recursos.");
                return;
            }
            Lexer lexer = new Lexer(fileReader);
            Parser parser = new Parser(lexer);
            parser.parse();
            System.out.println("Parsing completed successfully.");
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Parsing error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
