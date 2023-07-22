package modelos.fontes;
import algebra.*;
import modelos.*;

public class Spot extends Direcional{

  double angulo = 45;

  public Spot(double ...eixos){
    super(eixos);
  }

  public Vetor luz(Ponto ponto, Vetor v){

    Vetor l = ponto.pos.menos(posicao).unitario();

    double abertura = Math.cos(Math.toRadians(angulo));
    
    if(direcao.unitario().escalar(l) < abertura) return new Vetor(0,0,0);

    Vetor n = ponto.normal.unitario();
    
    if(l.escalar(n) > 0) return new Vetor(0,0,0);

    Vetor Ieye = difusa(n,l,ponto.getKd()).mais(especular(n,v,l,ponto.getKe()));

    return Ieye;
  }

  public Spot setAngulo(double angulo){
    this.angulo = angulo;
    return this;
  }

  public void printar(){
    super.printar();
    System.out.println("Direção");
    direcao.printar();
  }
}