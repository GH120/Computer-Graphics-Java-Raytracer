package modelos.objetos;
import algebra.*;

public class Piso extends Textura<Plano>{

  double escala = 1;
  
  Vetor eixoX, eixoY;

  public Piso(Plano plano, String path){
    super(plano, path);

    getEixos();
  }

  public Piso setEscala(double escala){
    this.escala = escala;
    return this;
  }

  void getEixos(){

    Vetor n = objeto.n;
    
    Vetor e = new Vetor(1,0,0);

    if(n.igual(e)) e = new Vetor(0,0,1);

    eixoY = n.vetorial(e).unitario();

    eixoX = n.vetorial(eixoY).unitario();
    
  }

  Vetor mapear(Vetor pi){

    double valorx = (escala*eixoX.escalar(pi)/escala) % (imagem.getWidth()-1);

    double valory = (escala*eixoY.escalar(pi)/escala) % (imagem.getHeight()-1);
    
    int x = Math.abs((int)valorx);
    int y = Math.abs((int)valory);
    
    Vetor pixel = Vetor.pixel(imagem.getRGB(x,y));

    return pixel;
  }
}