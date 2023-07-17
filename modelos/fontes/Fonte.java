package modelos.fontes;
import algebra.*;
import modelos.Ponto;

public class Fonte{
  
  public Vetor posicao;
  public Vetor If;

  public Fonte(double... coordenadas){
    posicao = new Vetor(coordenadas);
  }

  public Fonte setCor(double... cores){
    this.If = new Vetor(cores);
    return this;
  }

  public Vetor luz(Ponto ponto, Vetor v){

    Vetor n = ponto.normal.unitario();
    
    Vetor l = ponto.pos.menos(posicao).unitario();

    if(l.escalar(n) > 0) return new Vetor(0,0,0);

    Vetor Ieye = difusa(n,l,ponto.getKd()).mais(especular(n,v,l,ponto.getKe()));

    return Ieye;
  }

  Vetor difusa(Vetor n, Vetor l, Vetor Kd){
    return If.mult(Kd).vezes(-l.escalar(n));
  }


  Vetor especular(Vetor n, Vetor v, Vetor l, Vetor Ke){
    Vetor r = (n.vezes(2*l.escalar(n))).menos(l).unitario();

    double fe = r.escalar(v);

    fe = (fe>0)? fe : 0;
    
    return ((If.mult(Ke)).vezes(fe));
  }
}