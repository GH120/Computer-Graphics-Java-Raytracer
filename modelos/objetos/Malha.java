package modelos.objetos;
import java.util.ArrayList;

import algebra.*;
import modelos.*;

public abstract class Malha extends Conjunto{
  
  ArrayList<Vetor>   LV = new ArrayList<>();
  ArrayList<int[]>   LA = new ArrayList<>();
  ArrayList<int[]>   LF = new ArrayList<>();

  public Malha construir(){
    for(int[] face : LF){
      componentes.add(gerarTriangulo(face));
    }
    return this;
  }

  Malha addVertice(double ...eixos){
    LV.add(new Posicao(eixos));
    return this;
  }

  Malha addAresta(int comeco, int fim){
    int[] aresta = new int[2];
    aresta[0] = comeco;
    aresta[1] = fim;
    LA.add(aresta);
    return this;
  }

  void addNormal(Triangulo triangulo){
    Objeto tronco = new Cilindro(triangulo.P1.valores)
                          .setRaio(5)
                          .setTopo(triangulo.n.vezes(20).valores)
                          .construir()
                          .setKe(0.824, 0.706, 0.549)
                          .setKd(0.824, 0.706, 0.549)
                          .setKa(0.824, 0.706, 0.549);
    componentes.add(tronco);
  }

  Malha addFace(int A1, int A2, int A3){
    int[] face = new int[3];
    face[0] = A1;
    face[1] = A2;
    face[2] = A3;
    LF.add(face);
    return this;
  }

  Triangulo gerarTriangulo(int[] face){

    int v1,v2,v3;
    
    int idAresta1 = face[0];
    int idAresta2 = face[1];

    int idVertice11 = LA.get(idAresta1)[0]+1;
    int idVertice12 = LA.get(idAresta1)[1]+1;

    int idVertice21 = LA.get(idAresta2)[0]+1;
    int idVertice22 = LA.get(idAresta2)[1]+1;

    int n1 = idVertice11*idVertice12;
    int n = n1/idVertice21;

    if(n == idVertice11 || n == idVertice12){
      v1 = idVertice21;
      v2 = idVertice22;
      v3 = n;
    }
    else{
      v1 = idVertice22;
      v2 = idVertice21;
      v3 = n1/v1;
    }

    double[] p1 = LV.get(v1-1).eixos();
    double[] p2 = LV.get(v2-1).eixos();
    double[] p3 = LV.get(v3-1).eixos();

    return new Triangulo(p1).setP2(p2).setP3(p3);
  }


  public Malha setTextura(String path){

    ArrayList<Objeto> triangulos = new ArrayList<>();
    
    for(Objeto triangulo : componentes){
      triangulos.add(
        new Piso( (Triangulo)triangulo, path)
      );
    }

    this.componentes = triangulos;

    setKe(1,1,1);
    setKd(1,1,1);
    setKa(1,1,1);

    return this;
  }

}