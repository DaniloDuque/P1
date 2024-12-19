import org.example.Lexer;
import org.example.SymbolInfo;
import org.example.SymbolTable;
import org.example.sym;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: No file name provided.");
            System.exit(1);
        }

        try {
            // Obtener el nombre del archivo desde los parámetros de la línea de comandos
            String filename = args[0];

            // Acceder al archivo como recurso dentro del JAR
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filename);
            if (inputStream == null) {
                System.out.println("Error: File not found in resources.");
                System.exit(1);
            }

            // Crear un InputStreamReader para leer el archivo
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            // Crea una instancia del lexer generado por JFlex
            Lexer lexer = new Lexer(reader);

            // Llama al método que inicia el análisis léxico
            while (lexer.next_token().sym != sym.EOF) {
                // Procesa los tokens generados (si es necesario)
            }

            // Imprime el contenido de la tabla de símbolos
            FileWriter writer = new FileWriter("output.txt");
            writer.write("Tabla de símbolos\n");
            for (Map.Entry<String, List<SymbolInfo>> entry : lexer.getSymbolTable().getSymbols().entrySet()) {
                for (SymbolInfo symbolInfo : entry.getValue()) {
                    System.out.println(entry.getKey() + " " + symbolInfo + "\n");
                    writer.write(entry.getKey() + " " + symbolInfo + "\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}