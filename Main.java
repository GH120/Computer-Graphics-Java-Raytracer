// Feito por Felipe Vieira Duarte - 509067 e Yasser dos Santos Djalo - 514095

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import modelos.*;
import modelos.cenas.*;
import modelos.objetos.*;
import modelos.raytracers.*;
import modelos.reflexoes.*;
import algebra.*;

class Main {

  static int CAMERA_PERSPECTIVA = 1;
  static int CAMERA_ORTOGRAFICA = 2;
  
  public static void main(String[] args) {
    
      rodarPrograma(
                    new NatalOtimizado(),                  //Cena escolhida
                    new ConcurrentRaytracer(3),    //Raytracer escolhido
                    500, 500,               //Resolução
                    CAMERA_PERSPECTIVA                   //Câmera da cena escolhida
      );
    
  }

  static void rodarPrograma(Cena cena, Raytracer tracer ,int width, int height, int index){

    // Node node =new Octree(new Vetor(0,0,0), 500)
    // .processar(cena.objetos.componentes)
    // .filtrar();

    // // node.print();

    // Raio raio = new Raio();
    // raio.origem = new Vetor(0,0,0);
    // raio.direcao = new Vetor(0,-120,-150).unitario();

    // node.colisao(raio);

    // Conjunto conjunto = (Conjunto)cena.objetos.componentes.get(5);

    // Composto cubo = (Composto) conjunto.componentes.get(1);

    // Objeto triangulo = cubo.componentes.get(0);

    // triangulo.toWorld.printar();
    
    
    new Programa().setCena(
                            cena
                            .luzAmbiente(0.1,0.1,0.1)
                  )
                  .setTracer(tracer)
                  .getCamera(index)
                  .setResolution(width, height)
                  .renderizar();

    // ArrayList<Raio> raios = new ArrayList<>();

    // Objeto plano = new Plano(0,150,0)
    // .setNormal(0,-1,0)
    // .setKd(0.4, 0.4, 0.4)
    // .setKa(0.4, 0.4, 0.4)
    // .setKe(0.4, 0.4, 0.4);

    // // Creating a ray with origin at (0,0,0) and direction (1,1,1)
    // Raio ray = new Raio();
    // ray.origem = new Vetor(0, 0, 0);
    // ray.direcao = new Vetor(1, 1, 1).unitario();
    // ray.intensidade = new Vetor(1,1,1);
    // ray.linha = 1;
    // ray.coluna = 1;

    // // Creating a point with position (2,2,2) and normal vector (0,1,0)
    // Ponto point = new Ponto(plano, new Vetor(2, 2, 2), new Vetor(0, 1, 0));
    // point.pos = new Vetor(2, 2, 2);
    // point.normal = new Vetor(0, 1, 0).unitario();

    // new Glossy(0.99,3,1).refletir(point, ray, raios);

    // for(Raio raio: raios) raio.printar();
  }
}




class Contar{

  Timer timer;
  long startTime, endTime;
  Path file = Paths.get("teste.txt");
  ArrayList<String> linhas = new ArrayList<>();
  double total = 0;

  void start(){
    timer = new Timer();
    
    timer.schedule(new ContarSegundos(), 1000, 1000);

    startTime = System.currentTimeMillis();

    System.out.println("Começando execução...");
  }

  void end(){
    endTime = System.currentTimeMillis();

    timer.cancel();
    
    System.out.println("Levou o total de " + (endTime - startTime) + " milisegundos");

    add(endTime-startTime);
  }

  void gravar(){

    double media = total/linhas.size();

    linhas.add("\n Tempo médio: " + media);
    
    try{
      Files.write(file, linhas, StandardCharsets.UTF_8);
    }
    catch(Exception e){
    }
  }

  class ContarSegundos extends TimerTask {
    
    int segundos;
  
    public void run() {
        System.out.println((segundos+1) + "s se pass"+((segundos > 0)? "aram" : "ou"));
        segundos++;
    }
  }
  
  void add(double segundos){
    linhas.add("Execução: "+ linhas.size() + " - " +segundos+ "ms");

    total += segundos;
  }
  
}