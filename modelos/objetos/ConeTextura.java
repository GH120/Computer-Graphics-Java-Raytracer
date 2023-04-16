package modelos.objetos;
import algebra.*;

public class ConeTextura extends Textura<Cone>{

  public ConeTextura(Cone cone, String path){
    super(cone, path);
  }

  Vetor mapear(Vetor pi){

    Vetor vertice = objeto.vertice;

    Vetor dir = objeto.dir;

    double ratio = vertice.menos(pi).escalar(dir)/objeto.altura;

    Vetor e = new Vetor(0,0,1);

    if(dir.igual(e)) e = new Vetor(1,0,0);

    Vetor eixoX = dir.vetorial(e).unitario();

    Vetor height = dir.vezes(vertice.menos(pi).escalar(dir));

    Vetor raio = pi.menos(height).unitario();

    double revolucaoX = (1 + eixoX.escalar(raio)) * (imagem.getWidth()-1)/2;

    double revolucaoY = (ratio) * (imagem.getHeight()-1)/2;

    int x = Math.abs((int)revolucaoX);
    int y = Math.abs((int)revolucaoY);    

    Vetor pixel = Vetor.pixel(imagem.getRGB(x,y));

    return pixel;
  }
}