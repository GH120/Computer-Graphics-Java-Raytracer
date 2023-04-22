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
import modelos.raytracers.*;

class Main {

  static int CAMERA_PERSPECTIVA = 1;
  static int CAMERA_ORTOGRAFICA = 2;
  
  public static void main(String[] args) {
    
      rodarPrograma(
                    new NatalOtimizado(),            //Cena escolhida
                    new ConcurrentRaytracer(10),        //Raytracer escolhido
                    750, 750,  //Resolução
                    CAMERA_PERSPECTIVA      //Câmera da cena escolhida
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
    
    
    new Programa().setCena(
                            cena
                            .luzAmbiente(0.1,0.1,0.1)
                  )
                  .setTracer(tracer)
                  .getCamera(index)
                  .setResolution(width, height)
                  .renderizar();
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