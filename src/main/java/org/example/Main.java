import org.example.Lexer;
import org.example.SymbolInfo;
import org.example.SymbolTable;
import org.example.sym;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            String filename = sc.nextLine();
            String path = Paths.get(Objects.requireNonNull(Main.class.getClassLoader().getResource(filename)).toURI()).toString();
            FileReader reader = new FileReader(path);

            // Crea una instancia del lexer generado por JFlex
            Lexer lexer = new Lexer(reader);

            // Llama al método que inicia el análisis léxico
            while (lexer.next_token().sym != sym.EOF) {
                // Procesa los tokens generados (si es necesario)
            }

            // Imprime el contenido de la tabla de símbolos
            FileWriter writer = new FileWriter("output.txt");
            writer.write("Tabla de símbolos\n");
            for (Map.Entry<String, SymbolInfo> entry : lexer.getSymbolTable().getSymbols().entrySet()) {
                String key = entry.getKey(); // Clave
                SymbolInfo value = entry.getValue(); // Valor
                // Aquí puedes trabajar con key y value
                writer.write("Lexema: " + key + " "+ value + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
