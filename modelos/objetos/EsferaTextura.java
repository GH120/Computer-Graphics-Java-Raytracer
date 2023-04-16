package modelos.objetos;
import algebra.*;

public class EsferaTextura extends Textura<Esfera>{

  public EsferaTextura(Esfera esfera, String path){
    super(esfera, path);
  }

  Vetor mapear(Vetor pi){

    Vetor raio = pi.menos(objeto.centro).unitario();

    Vetor eixoY = objeto.toWorld.getColuna(1).tresD().unitario();
    Vetor eixoX = objeto.toWorld.getColuna(0).tresD().unitario();

    double revolucaoX = (1 + eixoX.escalar(raio)) * (imagem.getWidth()-1)/2;

    double revolucaoY = (1 + eixoY.escalar(raio)) * (imagem.getHeight()-1)/2;

    int x = Math.abs((int)revolucaoX);
    int y = Math.abs((int)revolucaoY);    

    Vetor pixel = Vetor.pixel(imagem.getRGB(x,y));

    return pixel;
  }
}