package modelos.objetos;
import algebra.*;
import modelos.*;

public class Circulo extends Plano{

  double raio;
  Vetor centro;

  public Circulo(double ... eixos){
    super(eixos);
    centro = P1;
  }

  public Ponto colisaoObjeto(Vetor p0, Vetor dr){
    
    Ponto ponto = super.colisaoObjeto(p0,dr);
    
    if(ponto != null)
      if(centro.menos(ponto.pos).modulo() > raio) 
        return null;

    return ponto;
  }

  public Circulo setRaio(double raio){
    this.raio = raio;
    return this;
  }

  public boolean inside(Vetor centro, double tamanho){

    if(!super.inside(centro, tamanho)) return false;

    Vetor d = P1.menos(centro);

    double distanciaX = d.escalar(d) - d.escalar(n);

    return distanciaX < raio*raio;
  }
}