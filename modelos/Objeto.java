package modelos;
import java.util.ArrayList;
import algebra.*;

public abstract class Objeto extends Movable{ 

  Vetor Ke, Ka, Kd;

  ArrayList<Reflexao> reflexoes = null;
  
  public abstract Ponto colisao(Vetor p0, Vetor dr);


  //Funções úteis abaixo

  //RETORNA UM PONTO DESSE OBJETO
  protected Ponto getPonto(Vetor pi, Vetor normal){
    return new Ponto(this, pi, normal);
  }

  //RESOLVE QUADRÁTICA PARA DETERMINAR PONTOS VÁLIDOS DE INTERSEÇÃO COM RAIO
  protected Vetor intersecaoRaio(double a, double b, double c, Vetor p0, Vetor dr){
    
    double delta = b*b - 4*a*c;
    
    //Raiz imaginária
    if (delta < 0) return null;

    delta = (double)Math.sqrt(delta);
    
    double t1 = (-b-delta)/(2*a);
    
    double t2 = (-b+delta)/(2*a);

    //Pega a menor raiz positiva 
    double t = (t1 < t2)? (t1 > 0)? t1 : t2 : 
                          (t2 > 0)? t2 : t1;
    //Se não existir raiz positiva
    if(t < 0) return null;

    return p0.mais(dr.vezes(t));
  }

  public Objeto setKe(double ...rgb){
    Ke = new Vetor(rgb);
    return this;
  }

  public Objeto setKd(double ...rgb){
    Kd = new Vetor(rgb);
    return this;
  }

  public Objeto setKa(double ...rgb){
    Ka = new Vetor(rgb);
    return this;
  }

  public Vetor getKe(Vetor pi){
    return Ke;
  }

  public Vetor getKd(Vetor pi){
    return Kd;
  }

  public Vetor getKa(Vetor pi){
    return Ka;
  }
  //Por algum motivo não consigo refatorar isso
  public Vetor luzAmbiente(Vetor luz){
    return luz.mult(getKa(new Vetor(0,0,0)));
  }

  public Objeto aplicar(Matriz ...matrizes){
    super.aplicar(matrizes);
    return this;
  }

  public boolean inside(Vetor centro, double tamanho){

    Vetor pos = toWorld.getColuna(3).tresD();

    if(centro.menos(pos).modulo() < tamanho) return true;

    return false;
  }
}