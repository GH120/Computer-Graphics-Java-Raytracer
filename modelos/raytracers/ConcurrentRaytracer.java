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

  private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
  private              int depth;

  public ConcurrentRaytracer(int depth){
    this.depth = depth;
  }

  public void dispararRaios() {

    ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    for(int i=0; i < NUM_THREADS;i++){

      final int threadLinhas = linhas/NUM_THREADS;
      final int threadIndex  = i;

        Vetor[][] linhasBuffer = new Vetor[linhas][colunas];

        for(int j=0;j<linhas;j++)
          for(int k=0;k<colunas;k++)
            linhasBuffer[j][k] = new Vetor(0,0,0);

        Callable<Void> processo = () -> {

            LinkedList<Raio> linhasDoProcesso = renderLinhas(threadIndex);

            while(!linhasDoProcesso.isEmpty()){

              Raio raio = linhasDoProcesso.poll();

              int l = raio.linha; int c = raio.coluna;

              Vetor cor = buscaCor(raio, linhasDoProcesso);

              linhasBuffer[l][c] = linhasBuffer[l][c].mais(cor);

            }

            for(int j=0;j<threadLinhas;j++)
              for(int k=0;k<colunas;k++){
                buffer[k][j*NUM_THREADS + threadIndex] = buffer[k][j*NUM_THREADS + threadIndex].mais(linhasBuffer[j*NUM_THREADS + threadIndex][k]);
              }

            return null;
        };

        executorService.submit(processo);

    }

    executorService.shutdown();

    try{
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
    catch(InterruptedException e){
        e.printStackTrace();
    }
  }

  Vetor buscaCor(Raio raio, LinkedList<Raio> linhas) {

    Ponto ponto = cena.objetos.colisao(raio.origem,
        raio.direcao);

    if (ponto == null)
      return new Vetor(0,0,0);

    //Reflexão: adiciona a pilha o novo raio
    if(raio.profundidade < depth){
      
      Raio reflexao = raio.reflexao(ponto);
      
      linhas.offer(reflexao);
    }
    
    Vetor luz = iluminar(ponto, raio.direcao);
    
    return luz.mult(raio.intensidade);
  }

  LinkedList<Raio> renderLinhas(int processo) {

    LinkedList<Raio> raios = new LinkedList<>();

    double w = camera.wJanela;
    double h = camera.hJanela;

    double deltax = w / linhas;
    double deltay = h / colunas;

    for (int l = processo; l < linhas; l=l+NUM_THREADS) {
      double y = h / 2 - deltay / 2 - deltay * l;
      for (int c = 0; c < colunas; c++) {
        double x = w / 2 - deltax / 2 - deltax * c;
        
        raios.add(gerarRaio(x, y, c, l));
      }
    }

    return raios;
  }

  Raio gerarRaio(double x, double y, int c, int l) {

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

    return raio;
  }
}
