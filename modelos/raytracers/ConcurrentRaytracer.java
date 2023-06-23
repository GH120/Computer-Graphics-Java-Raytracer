package modelos.raytracers;

import algebra.*;
import modelos.*;
import modelos.cameras.Camera;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentRaytracer extends Raytracer{

  private Raytracer raytracer;
  private static final int  NUM_THREADS = 10;
  private LinkedList<Callable<Void>> threadsDeRenderizarLinhas;

  public ConcurrentRaytracer(Raytracer raytracer){
    this.raytracer = raytracer;
  }

  public void render() {

    gerarRaios();

    calcularRaios();
    
  }

  public Vetor buscarCor(Raio raio, List<Raio> pilha) {

    return raytracer.buscarCor(raio, pilha);
  }

  public void gerarRaios() {

    raytracer.linhas = linhas;

    raytracer.colunas = colunas;

    raytracer.gerarRaios();

    raios = raytracer.raios;
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
  //Thread 1 => linhas 1 a 100
  //Thread 2 => linhas 100 a 200
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
              
              linhasBuffer[l][c].add(cor);

            }
          }
          catch(Exception e){
            e.printStackTrace();
          }

          for(int j=0;j<threadLinhas;j++){
            for(int k=0;k<colunas;k++){
              buffer[k][j + linhaInicial].add(linhasBuffer[j][k]);
            }
          }


          return null;
      };

      //Adiciona o processo a lista...
      threadsDeRenderizarLinhas.add(processo);
    }
  }

  public Raytracer gerarBuffer(){
        super.gerarBuffer();
        raytracer.buffer = this.buffer;
        return this;
    }

    public Raytracer setCena(Cena cena){
        this.cena = cena;
        this.raytracer.setCena(cena);
        return this;
    }
  
    public Raytracer setCamera(Camera camera){
        this.camera = camera;
        this.raytracer.setCamera(camera);
        return this;
    }
  
}
