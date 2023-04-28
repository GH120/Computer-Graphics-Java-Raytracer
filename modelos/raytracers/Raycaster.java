package modelos.raytracers;
import algebra.*;
import modelos.*;

public class Raycaster extends Raytracer{

  public void render() {

    double w = camera.wJanela;
    double h = camera.hJanela;

    double deltax = w / linhas;
    double deltay = h / colunas;

    for (int l = 0; l < linhas; l++) {
      double y = h / 2 - deltay / 2 - deltay * l;
      for (int c = 0; c < colunas; c++) {
        double x = w / 2 - deltax / 2 - deltax * c;
        
        calcularRaio(x,y,c,l);
      }
    }
  }

  Vetor buscaCor(Vetor p0, Vetor dr){

    Ponto ponto = cena.objetos.colisao(p0, dr);

    // if(!ponto.objeto.inalterado()){
    //   if(ponto != null)
    //     ponto = ponto.toWorld();
    // }
    
    if(ponto == null) return cena.background;

    Vetor luz = iluminar(ponto, dr);

    return luz;
  }
  
  void calcularRaio(double x, double y, int c, int l){
    
    //posicao x,y da camera transformada para as coordenadas de mundo
    Vetor p = camera.toWorld((new Posicao(x, y, 0)));
    
    //raio que incide sobre o pixel
    Vetor direcao = camera.projecao.getDirecao(p);

    //Guarda a cor no pixel do buffer
    buffer[c][l] = buscaCor(p.tresD(), direcao);
    
  }
}