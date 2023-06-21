package programa;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import algebra.*;
import modelos.*;
import modelos.cameras.*;
import modelos.raytracers.*;

public class Programa {

  //User interface
  JFrame window;
  Tela   canvas;

  //Modelos
  Cena      cena; 
  Camera    camera    = null;
  Raytracer raytracer = null;
 
  //Controlers
  public Controler[] controlers = {
      new ObjetoControler(),
      new FonteControler(),
      new CameraControler()
  };

  public Programa() {
    window = new JFrame();
    window.setSize(new Dimension(500, 500));
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLayout(new BorderLayout());
    window.setFocusable(true);

    canvas = new Tela();
    canvas.setPreferredSize(new Dimension(500, 450));
    canvas.addMouseListener(new Picking(this)); // Adiciona a o controler de picking ao canvas

    window.add(canvas, BorderLayout.NORTH);

    window.setVisible(true);
  }

  //Inicia o programa depois de configurados raytracer e câmera
  public Programa renderizar() {

    if (camera == null)
      defaultCamera();

    controlers[0] = new ObjetoControler();
    controlers[1] = new FonteControler();
    controlers[2] = new CameraControler();

    atualizar();

    window.add(new Barra(this), BorderLayout.CENTER);

    window.revalidate();

    window.repaint();

    return this;
  }

  //Re-renderiza a imagem
  public void atualizar() {

    Contar timer = new Contar();

    timer.start();

    raytracer.setCena(cena);

    canvas.raytracer = raytracer;

    raytracer.gerarBuffer();

    raytracer.render();

    window.revalidate();

    window.repaint();

    timer.end();
  }

  public Programa setCena(Cena cena) {
    this.cena = cena;
    return this;
  }

  public Programa setTracer(Raytracer raytracer){
    this.raytracer = raytracer;
    return this;
  }

  public Programa setCamera(Camera camera) {
    this.camera = camera;
    this.raytracer.setCamera(camera);
    return this;
  }

  public Programa setResolution(int linhas, int colunas){
    this.raytracer.linhas  = linhas;
    this.raytracer.colunas = colunas;
    return this;
  }

  public Programa setSize(int width, int height){
    this.window.setSize(new Dimension(width, height));
    this.canvas.setPreferredSize(new Dimension(width, height*4/5));
    return this;
  }

  public Programa setupControlers() {
    for (Controler controler : controlers) {
      controler.setPrograma(this);
    }
    return this;
  }

  public Programa getCamera(int numero) {

    int quantidade = cena.cameras.size();

    if (quantidade == 0)
      return this;

    int index = (numero < quantidade) ? (numero < 1) ? 1 : numero : quantidade;

    raytracer.camera = cena.cameras.get(index - 1);

    return this;
  }

  public void defaultCamera() {

    setCamera(
        (Camera) new Camera()
        .setDimensoes(80, 80)
        .setCoordenadas(
            new Vetor(0, 0, 0),
            new Vetor(0, 0, 30),
            new Vetor(0, 1, 0)));

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