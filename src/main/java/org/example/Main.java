import org.example.Lexer;
import org.example.SymbolInfo;
import org.example.SymbolTable;
import org.example.sym;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Cargar el archivo desde la carpeta resources
            String path = Paths.get(Main.class.getClassLoader().getResource("test.txt").toURI()).toString();
            FileReader reader = new FileReader(path);

            // Crea una instancia del lexer generado por JFlex (tu clase Scanner)
            Lexer lexer = new Lexer(reader);

            // Llama al método que inicia el análisis léxico
            while (lexer.next_token().sym != sym.EOF) {
                // Procesa los tokens generados (si es necesario)
            }

            // Una vez finalizado el análisis, accede a la tabla de símbolos
            SymbolTable symbolTable = lexer.getSymbolTable();

            // Imprime el contenido de la tabla de símbolos
            System.out.println("Tabla de símbolos:");
            for (String entry : symbolTable.getSymbols()) {
                System.out.println(entry);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
