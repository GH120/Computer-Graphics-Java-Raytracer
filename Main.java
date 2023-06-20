import modelos.*;
import modelos.cenas.*;
import modelos.raytracers.*;
import programa.Programa;
import algebra.*;
import modelos.reflexoes.*;
import java.util.*;
import modelos.objetos.*;

import java.util.ArrayList;

class Main {

  static int CAMERA_PERSPECTIVA = 1;
  static int CAMERA_ORTOGRAFICA = 2;
  
  public static void main(String[] args) {

    rodarPrograma(
              new modelos.cenas.Teste(),                //Cena escolhida
              new Sampletracer(2, 16),//Raytracer escolhido
              700, 700,               //Resolução
              CAMERA_PERSPECTIVA                   //Câmera da cena escolhida
      );

      // new Teste();

  }

  static void rodarPrograma(Cena cena, Raytracer tracer ,int width, int height, int index){

      //Copiar para main depoiS


      new Programa().setCena(
                            cena
                            .luzAmbiente(0.1,0.1,0.1)
                  )
                  .setTracer(tracer)
                  .getCamera(index)
                  .setResolution(width, height)
                  .setSize(width, height)
                  .renderizar();
  }
}


class Teste{

  Cena cena =  new modelos.cenas.Teste();
  Ponto P1 = new Ponto(null, new Vetor(0,50*Math.sqrt(2), -200 + 50*Math.sqrt(2)), null);
  Ponto P2 = new Ponto(null, new Vetor(0,0, -300), null);

  Teste(){

      List<Raio> raios = new ArrayList<Raio>(); //Pilha de raios, pra conseguir usar na reflexão da superficie

      Raio raio    = new Raio();
      raio.origem  = new Vetor(0,50*Math.sqrt(2),0);
      raio.direcao = new Vetor(0,0,-1);
      raio.intensidade = new Vetor(1,1,1);

      raio.origem.printar();
      raio.direcao.printar();

      Ponto colidido = cena.objetos.colisao(raio.origem, raio.direcao);

      colidido.printar(); // Teste do P1, tem que ser igual

      Superficie refratora = colidido.objeto.superficie; //Pego a superficie do objeto

      refratora.refletir(colidido, raio, raios); //Raio refratado adicionado a pilha de raios

      System.out.println(Arrays.toString(raios.toArray()));

      Raio refratado = raios.get(0); //Pega o raio adicionado na pilha pela ultima reflexão

      System.out.println("raio refratado");
      refratado.printar();

      Ponto colididoDentro = cena.objetos.colisao(refratado.origem, refratado.direcao); //Ponto obtido da colisão

      colididoDentro.printar();

      refratora.refletir(colididoDentro, refratado, raios); //Raio refratado adicionado a pilha de raios
      System.out.println("colisão 2");
      Raio refratado2 = raios.get(1); //Pega o raio adicionado na pilha pela ultima reflexão

      refratado2.printar();

      cena.objetos.colisao(refratado2.origem, refratado2.direcao).printar();

  }
}