package modelos.raytracers;

import algebra.*;
import modelos.*;
import modelos.reflexoes.*;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentRaytracer extends Raytracer{

  private static final          int  NUM_THREADS = 10;
  private                       int  depth;
  private LinkedList<Callable<Void>> threadsDeRenderizarLinhas;

  public ConcurrentRaytracer(int depth){
    this.depth = depth;
  }

  public void render() {

    gerarRaios();

    calcularRaios();
    
  }

  //Executa as threads
  public void calcularRaios(){

    ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    criarThreads(raios);

    for(Callable<Void> thread : threadsDeRenderizarLinhas) {

        executorService.submit(thread);
    }

    executorService.shutdown();

    try{
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
    catch(InterruptedException e){
        e.printStackTrace();
    }

  }

  //Cria as threads para renderizar as linhas em paralelo
  //Thread 1 => linhas 1,11,21,...
  //Thread 2 => linhas 2,12,22,...
  //...
  public void criarThreads(LinkedList<Raio> pilha){

    //Lista que guarda as threads a serem executadas
    threadsDeRenderizarLinhas = new LinkedList<>();

    for(int i=0; i < NUM_THREADS; i++){

      //Informações para separar os raios em partes a serem executadas
      //Buffer para guardar as cores e subpilha de execução dos raios rodada na thread
      final int threadLinhas  = linhas/NUM_THREADS;
      final int threadRaios   = pilha.size()/NUM_THREADS;
      final int threadIndex   = i;
      final int linhaInicial  = threadLinhas*i;
      final int comeco        = threadIndex*threadRaios;
      final int fim           = comeco + threadRaios;

      Vetor[][] linhasBuffer = new Vetor[threadLinhas][colunas];

      for(int j=0;j<threadLinhas;j++)
        for(int k=0;k<colunas;k++)
          linhasBuffer[j][k] = new Vetor(0,0,0);

      final LinkedList<Raio> linhasDoProcesso = new LinkedList<>(pilha.subList(comeco, fim));

      //Processo rodado na thread, calcula os raios das linhas do processo e guarda no buffer das linhas
      Callable<Void> processo = () -> {

          try{
          while(!linhasDoProcesso.isEmpty()){

            Raio raio = linhasDoProcesso.poll();

            int l = raio.linha - linhaInicial; int c = raio.coluna;
            
            Vetor cor = buscarCor(raio, linhasDoProcesso);
            
            linhasBuffer[l][c] = linhasBuffer[l][c].mais(cor);

          }
          }
          catch(Exception e){
            e.printStackTrace();
          }

          for(int j=0;j<threadLinhas;j++){
            for(int k=0;k<colunas;k++){
              buffer[k][j + linhaInicial] = buffer[k][j+linhaInicial].mais(linhasBuffer[j][k]);
            }
          }


          return null;
      };

      //Adiciona o processo a lista...
      threadsDeRenderizarLinhas.add(processo);
    }
  }
  

  public Vetor buscarCor(Raio raio, List<Raio> linhas) {

    Ponto ponto = cena.objetos.colisao(raio.origem, raio.direcao);

    if (ponto == null)
      return cena.background;

    if( raio.interno) 
      ponto.normal = ponto.normal.vezes(-1);

    if(raio.profundidade < depth){

      Superficie superficie = ponto.objeto.superficie;

      if(superficie != null) superficie.refletir(ponto, raio, linhas);

    }
    
    Vetor luz = iluminar(ponto, raio.direcao);
    
    return luz.mult(raio.intensidade);
  }

   public void gerarRaios() {

    raios = new LinkedList<>();

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
