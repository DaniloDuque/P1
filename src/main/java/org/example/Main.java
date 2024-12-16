package org.example;

import java_cup.Lexer;
import java_cup.runtime.*;
import java_cup.sym;

public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lexer = new Lexer(System.in);
        Symbol token;

        while ((token = lexer.next_token()).sym != sym.EOF) {
            System.out.println("Token: " + token.toString());
        }
    }
}
