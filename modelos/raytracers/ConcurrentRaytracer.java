package modelos.raytracers;
import algebra.*;
import modelos.*;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentRaytracer extends Raytracer{

  private static final int                 NUM_THREADS = Runtime.getRuntime().availableProcessors();
  private static final BlockingQueue<Raio> raios = new LinkedBlockingQueue<>();

  public void dispararRaios() {

    ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    System.out.println(NUM_THREADS);

    gerarRaios();

    for(int i=0; i < NUM_THREADS;i++){

        Callable<Void> task = () -> {

            while(!raios.isEmpty()){

                // Raio[] listaDeRaios = new Raio[10];

                // synchronized(raios){
                //     for(int k=0; k< 10 && !raios.isEmpty(); k++){
                //         listaDeRaios[k] = raios.poll();
                //     }
                // }
                //Concurrency not working, maybe need to change approach
                //Make temporary buffer to hold ray information
                //When depth increases, sum buffers
                synchronized(this){

                    Raio raio = raios.poll();

                    // for(Raio raio : raios){

                        int l = raio.linha; int c = raio.coluna;

                        Vetor cor = buscaCor(raio);

                        buffer[c][l] = buffer[c][l].mais(cor);
                    // }
                }
            }

            return null;
        };

        executorService.submit(task);

    }

    executorService.shutdown();

    try{
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
    catch(InterruptedException e){
        e.printStackTrace();
    }
  }

  Vetor buscaCor(Raio raio) {

    Ponto ponto = cena.objetos.colisao(raio.origem,
        raio.direcao);

    if (ponto == null)
      return new Vetor(0,0,0);

    //Reflexão: adiciona a pilha o novo raio
    if(raio.profundidade < 1){
      
      Raio reflexao = raio.reflexao(ponto);
      
      raios.offer(reflexao);
    }
    
    Vetor luz = iluminar(ponto, raio.direcao);
    
    return luz.mult(raio.intensidade);
  }

  void gerarRaios() {

    double w = camera.wJanela;
    double h = camera.hJanela;

    double deltax = w / linhas;
    double deltay = h / colunas;

    for (int l = 0; l < linhas; l++) {
      double y = h / 2 - deltay / 2 - deltay * l;
      for (int c = 0; c < colunas; c++) {
        double x = w / 2 - deltax / 2 - deltax * c;
        
        gerarRaio(x, y, c, l);
      }
    }
  }

  void gerarRaio(double x, double y, int c, int l) {

    Raio raio = new Raio();

    raio.linha = l;

    raio.coluna = c;

    // posicao x,y da camera transformada para as coordenadas de mundo
    raio.origem = camera.toWorld((new Posicao(x, y, 0)));

    // raio que incide sobre o pixel[c][l]
    raio.direcao = camera.projecao.getDirecao(raio.origem);

    // O coeficiente de reflexão de multiplas colisões
    raio.intensidade = new Vetor(1, 1, 1);

    raio.origem = raio.origem.tresD();
    raio.direcao = raio.direcao.tresD();

    raios.add(raio);

  }
}
