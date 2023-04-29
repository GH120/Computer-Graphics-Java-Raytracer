package programa;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import algebra.*;
import modelos.*;
import modelos.cameras.Camera;
import modelos.raytracers.*;


//Selecionar objeto, adicionar ele ao objeto selecionado do programa -> criar um controler selecionar?
//Controler selecionar é observador de programa, picking manda objeto para programa
//Controler

public class Picking implements MouseListener{

  Programa programa; //Pega a camera atual do programa, faz o feixe sair do pixel clicado

  Picking(Programa programa){
    this.programa = programa;
  }

   public void mouseClicked(MouseEvent e) {  
     
       int x=e.getX();
       int y=e.getY();

       handleClick(x,y);
    }  
  
    public void mouseEntered(MouseEvent e) {}  
    public void mouseExited(MouseEvent e) {}  
    public void mousePressed(MouseEvent e) {}  
    public void mouseReleased(MouseEvent e) {}


    //Corrigir para acertar mesmo com parte da tela em branco
  
    public void handleClick(int xJanela, int yJanela){

      Ponto ponto = getColisao(xJanela, yJanela);

      Objeto objeto = (ponto == null)? null : ponto.objeto;

      ObjetoControler controler = (ObjetoControler) programa.controlers[0];

      controler.selecionar(objeto);

      controler.notificar();

      if(objeto == null) return;

      Graphics g = programa.canvas.getGraphics();

      g.drawString(ponto.objeto.toString(), xJanela, yJanela);

    }

    private Ponto getColisao(int xJanela, int yJanela){
      
      Camera    camera = programa.camera;
      Raytracer tracer = programa.raytracer;

      int windowLength = programa.window.getWidth();
      int windowHeight = programa.window.getHeight();

      //Consegue os indices do quadrante atingido, 
      //corrigindo pelas distorções janela/canvas
      
      int coluna = (xJanela*tracer.colunas)/windowLength;
      int linha =  (yJanela*tracer.linhas)/windowHeight;
      
      double deltax = camera.wJanela / tracer.linhas;
      double deltay = camera.hJanela / tracer.colunas;

      double x = camera.wJanela / 2 - deltax / 2 - deltax * coluna;
      double y = camera.hJanela / 2 - deltay / 2 - deltay * linha;

      Vetor quadrante = camera.toWorld(new Posicao(x,y,0));
      Vetor direcao = camera.projecao.getDirecao(quadrante);

      Ponto ponto = tracer.cena.objetos.colisao(quadrante.tresD(), direcao);

      return ponto;
    }
}