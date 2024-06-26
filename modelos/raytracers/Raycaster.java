package modelos.raytracers;
import java.util.LinkedList;
import java.util.List;

import algebra.*;
import modelos.*;

public class Raycaster extends Raytracer{

  public void render() {

    gerarRaios();

    calcularRaios();  
  }

  public void calcularRaios(){

    while(!raios.isEmpty()){
      
      Raio raio = raios.removeFirst();

      int l = raio.linha; int c = raio.coluna;
      
      buffer[c][l] = buffer[c][l].mais(buscarCor(raio, raios));
    }
  }

  public Vetor buscarCor(Raio raio, List<Raio> pilha){

    Ponto ponto = cena.objetos.colisao(raio.origem, raio.direcao);
    
    if(ponto == null) return cena.background.mult(raio.intensidade);

    Vetor luz = iluminar(ponto, raio.direcao);

    return luz.mult(raio.intensidade);
  }

  public void gerarRaios(){

    raios = new LinkedList<Raio>();

    double w = camera.wJanela;
    double h = camera.hJanela;

    double deltax = w / linhas;
    double deltay = h / colunas;

    for (int l = 0; l < linhas; l++) {
      double y = h / 2 - deltay / 2 - deltay * l;
      for (int c = 0; c < colunas; c++) {
        double x = w / 2 - deltax / 2 - deltax * c;
        
        raios.add(gerarRaio(x,y,c,l));
      }
    }

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