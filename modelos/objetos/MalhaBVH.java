package modelos.objetos;

import java.util.*;

import algebra.Vetor;
import miniball.Miniball;
import miniball.PointSet;

public class MalhaBVH extends Malha{
    

     public Conjunto BoundingVolume(){

        //Consegue a miniball que contém todos os vértices
        List<Vetor> vertices = getVerticesUsados();

        Esfera esfera = boundingSphere(vertices);
        
        // Se forem poucas faces, retorna essa malha construida com a fronteira da miniball
        if(LF.size() < 4) 
            return this.construir().setFronteira(esfera); 

        //Se não, organiza os vértices pela mediana do maior eixo

        sortMedian(vertices);

        List<Vetor> menor = vertices.subList(0, vertices.size()/2);
        
        List<Vetor> maior = vertices.subList(vertices.size()/2 + 1,  vertices.size());
        
        //Divide em duas malhas pelo eixo da mediana, e coloca as faces entre os dois conjuntos em uma malha adicional

        HashSet<int[]> facesRestantes = new HashSet<>();

        Malha subMalhaMenor = getSubMalha(menor, facesRestantes);
        
        Malha subMalhaMaior = getSubMalha(maior, facesRestantes);

        if(subMalhaMenor.LF.size() == 0) return this.construir().setFronteira(esfera);
        if(subMalhaMaior.LF.size() == 0) return this.construir().setFronteira(esfera);
        
        System.out.println("malha menor: " + subMalhaMenor.LF.size());

        
        System.out.println("malha maior: " + subMalhaMaior.LF.size());

        Malha subMalhaRestante = new Malha(){};

        subMalhaRestante.LV = LV;
        subMalhaRestante.LF = new ArrayList<>(facesRestantes);

        // System.out.println("malha restante: " + subMalhaRestante.LF.size());

        //Retorna o conjunto que contém essas três malhas
        return new Conjunto(subMalhaRestante.BoundingVolume()).setFronteira(esfera);
        
     }

     Malha getSubMalha(List<Vetor> vertices, Set<int[]> facesRestantes){

        HashSet<Vetor> set = new HashSet(vertices);

        var submalha = new MalhaBVH();

        submalha.LV = LV;

        for(int[] face : LF){
            if(set.contains(LV.get(face[0])) && set.contains(LV.get(face[1])) && set.contains(LV.get(face[2]))){
                submalha.LF.add(face);
            }
            else{
                facesRestantes.add(face);
            }
        }
        return submalha;
     }

     List<Vetor> getVerticesUsados(){

        HashSet<Integer> v = new HashSet<>();

        for(int[] face : LF){
          v.add(face[0]);
          v.add(face[1]);
          v.add(face[2]);
        }
    
        ArrayList<Vetor> novo = new ArrayList<>();
    
        for(int i : v){
          novo.add(LV.get(i));
        }

        return novo;
     }

     void sortMedian(List<Vetor> pontos){

        Collections.sort(pontos, Comparator.comparingDouble(Vetor::getX));

        double x      = pontos.get(pontos.size()/2).getX();
        double deltax = Math.max(x - pontos.get(0).getX(), pontos.get(pontos.size()-1).getX() - x);

        Collections.sort(pontos, Comparator.comparingDouble(Vetor::getY));

        double y = pontos.get(pontos.size()/2).getX();
        double deltay = Math.max(y - pontos.get(0).getY(), pontos.get(pontos.size()-1).getY() - y);

        Collections.sort(pontos, Comparator.comparingDouble(Vetor::getZ));

        double z = pontos.get(pontos.size()/2).getX();
        double deltaz = Math.max(z - pontos.get(0).getZ(), pontos.get(pontos.size()-1).getZ() - z);
     }

     Esfera boundingSphere(List<Vetor> vertices){

        var pontos = new PointSet(){

            public int dimension(){
              return 3;
            }
      
            public int size(){
              return vertices.size();
            }
      
            public double coord(int i, int j) {
              return vertices.get(i).get(j);
            }
          };
      
          Miniball BoundingSphere = new Miniball(pontos);
      
          Esfera esfera = new Esfera(BoundingSphere.center())
                            .setRaio(BoundingSphere.radius());

          return esfera;
     }

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
