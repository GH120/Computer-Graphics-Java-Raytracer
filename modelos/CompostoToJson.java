package modelos;
import java.io.*;
import java.util.*;
import modelos.objetos.*;

public class CompostoToJson {
    
    public CompostoToJson(Objeto objeto){

        var linhas = getString(objeto);

        linhas.remove(linhas.size() -1);

        linhas.add("}");

        for(String linha : linhas){
            System.out.println(linha);
        }

        writeString(linhas, "objeto.json");
    }

    ArrayList<String> getString(Objeto objeto){

        var string = new ArrayList<String>();

        string.add("{");

        if(objeto instanceof Esfera){

            Esfera esfera = (Esfera) objeto;

            string.add("\"centro\":" + Arrays.toString(esfera.centro.valores) + ",");
            string.add("\"raio\":" + esfera.raio);

        }

        else if(objeto instanceof Triangulo){

            Triangulo triangulo = (Triangulo) objeto;
            
            string.add("\"p1\":" + Arrays.toString(triangulo.P1.valores) +",");
            string.add("\"p2\":" + Arrays.toString(triangulo.P2.valores) +",");
            string.add("\"p3\":" + Arrays.toString(triangulo.P3.valores));
        }

        else if(objeto instanceof Conjunto){

            Conjunto conjunto = (Conjunto) objeto;

            Objeto fronteira = conjunto.fronteira;

            if(fronteira != null){

                string.add("\"fronteira\":");

                string.addAll(getString(fronteira));
            }
            
            string.add("\"componentes\": [");

            for(Objeto componente : conjunto.componentes){

                string.addAll(getString(componente));

            }

            string.remove(string.size() -1);

            string.add("}");

            string.add("]");
        }

        string.add("},");

        return string;
    }

    void writeString(ArrayList<String> linhas, String filePath){
        
        try {
            // Open a file writer stream using BufferedWriter for efficient writing
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            // Iterate through the array and write each string to the file
            for (String data : linhas) {
                writer.write(data);
                writer.newLine(); // Add a new line after each string (optional)
            }

            // Close the writer stream to release resources
            writer.close();

            System.out.println("Array of strings has been written to the file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
}