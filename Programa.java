import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import algebra.*;
import modelos.*;
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

    raytracer.cena = cena;

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
    this.raytracer.camera = camera;
    return this;
  }

  public Programa setResolution(int linhas, int colunas){
    this.raytracer.linhas  = linhas;
    this.raytracer.colunas = colunas;
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