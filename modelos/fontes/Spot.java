package modelos.fontes;
import algebra.*;
import modelos.*;

public class Spot extends Direcional{

  double angulo = 45;

  public Spot(double ...eixos){
    super(eixos);
  }

  public Vetor luz(Ponto ponto, Vetor v){

    Vetor d = ponto.pos.menos(posicao).unitario();

    Vetor l = direcao.unitario();

    double abertura = Math.cos(Math.toRadians(angulo));
    
    if(l.escalar(d) < abertura) return new Vetor(0,0,0);

    return super.luz(ponto,v);
  }

  public Spot setAngulo(double angulo){
    this.angulo = angulo;
    return this;
  }
}