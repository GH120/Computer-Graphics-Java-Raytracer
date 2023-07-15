package modelos.objetos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MalhaOBJ extends Malha {

    public MalhaOBJ(String filePath) {
        try {
            readObjFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readObjFile(String filePath) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;

        while ((line = reader.readLine()) != null) {

            String[] parts = line.trim().split("\\s+");

            boolean isVertice = parts[0].equals("v");
            boolean isFace    = parts[0].equals("f");

            if (isVertice) {

                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);

                addVertice(x, y, z);
            } 
            else if (isFace) {

                //Pega os índices dos vértices usados
                int v1 = parseIndex(parts[1].split("/")[0]);
                int v2 = parseIndex(parts[2].split("/")[0]);
                int v3 = parseIndex(parts[3].split("/")[0]);
                
                int index = LA.size();
                
                //Adiciona arestas com esses vértices
                addAresta(v1,v2).addAresta(v2,v3).addAresta(v3,v1);
                
                //Adiciona a face relacionada as últimas arestas adicionadas
                addFace(index, index+1, index+2);

                System.out.println("v1: " + v1 + " v2: " + v2 + " v3: " + v3);
            }
        }
        reader.close();
    }

    private int parseIndex(String indexString) {
        int index = Integer.parseInt(indexString);
        return (index >= 0) ? index - 1 : LV.size() + index;
    }
}
