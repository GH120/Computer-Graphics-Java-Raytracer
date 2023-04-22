package modelos.raytracers;

import java.util.LinkedList;

import algebra.*;
import modelos.*;

public class Pathtracer extends Raytracer {

  LinkedList<Raio>  raios;
  int               depth;

  public Pathtracer(int depth){
    this.depth = depth;
  }

  public void dispararRaios() {

    gerarRaios();

    while(!raios.isEmpty()){
      
      Raio raio = raios.removeFirst();

      int l = raio.linha; int c = raio.coluna;
      
      buffer[c][l] = buffer[c][l].mais(buscaCor(raio));
    }
  }

  Vetor buscaCor(Raio raio) {

    Ponto ponto = cena.objetos.colisao(raio.origem,
        raio.direcao);

    if (ponto == null)
      return new Vetor(0,0,0);

    //Reflexão: adiciona a pilha o novo raio
    if(raio.profundidade < depth){
      
      Raio reflexao = raio.reflexao(ponto);
      
      raios.add(reflexao);
    }
    
    Vetor luz = iluminar(ponto, raio.direcao);
    
    return luz.mult(raio.intensidade);
  }

  void gerarRaios() {

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