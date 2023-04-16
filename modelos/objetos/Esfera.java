package modelos.objetos;
import algebra.*;
import modelos.*;

public class Esfera extends ObjetoSimples{
  
  Vetor centro;
  double raio;
  

  public Esfera(double... eixos){
    centro = new Vetor(eixos);
  }

  //Verifica se raio intersecta esfera
  Ponto colisaoObjeto(Vetor p0, Vetor dr){
    
    Vetor w = p0.menos(centro);
    
    double a = dr.escalar(dr);
    double b = w.escalar(dr) * 2;
    double c = w.escalar(w) - raio*raio;

    Vetor pi = intersecaoRaio(a,b,c,p0,dr);

    if(pi == null) return null;

    return getPonto(pi, normal(pi));
  }

  public Esfera setRaio(double raio){
    this.raio = raio;
    return this;
  }

  Vetor normal(Vetor pi){
    return pi.menos(centro).unitario();
  }

  public boolean inside(Vetor centroCubo, double tamanho){
    return centroCubo.menos(this.centro).modulo() < 1.73*(tamanho + raio);
  }
}