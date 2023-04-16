package modelos.fontes;
import algebra.*;
import modelos.*;

public class Direcional extends Fonte{

  public Vetor direcao;

  public Direcional(double... coordenadas){
    super(coordenadas);
  }

  public Vetor luz(Ponto ponto, Vetor v){

    Vetor n = ponto.normal.unitario();

    Vetor l = direcao;

    if(l.escalar(n) < 0) 
      return new Vetor(0,0,0);

    Vetor Ieye = difusa(n,v,l,ponto.getKd()).mais(especular(n,v,l,ponto.getKe()));

    return Ieye;
  }

  public Fonte setDirecao(double... eixos){
    direcao = new Vetor(eixos).unitario();
    return this;
  }
}