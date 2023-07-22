package modelos.objetos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MalhaOBJ extends Malha {

    public MalhaOBJ(){

    }

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
                
                //Adiciona a face relacionada as últimas arestas adicionadas
                addFace(v1, v2, v3);

                // System.out.println("v1: " + v1 + " v2: " + v2 + " v3: " + v3);

                //Se for uma malha de quadrilateros
                if(parts.length == 5){

                    int v4 = parseIndex(parts[4].split("/")[0]);
                    
                    addFace(v1, v3, v4);

                }
            }
        }

        System.out.println("tamanho: " + LF.size());
        reader.close();
    }
 
    //Provavelmente o erro está aqui
    private int parseIndex(String indexString) {
        int index = Integer.parseInt(indexString);
        return (index >= 0) ? index - 1 : LV.size() + index ;
    }

    //Nesse caso, construimos a face com os vértices ao invés das arestas
    Triangulo gerarTriangulo(int[] face){

        int v1,v2,v3;

        v1 = face[0];
        v2 = face[1];
        v3 = face[2];
    
        double[] p1 = LV.get(v1).eixos();
        double[] p2 = LV.get(v2).eixos();
        double[] p3 = LV.get(v3).eixos();
    
        return new Triangulo(p1).setP2(p2).setP3(p3);
    }
}

