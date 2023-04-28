package modelos;
// import java.util.ArrayList;
import algebra.*;
import modelos.reflexoes.Superficie;

public abstract class Objeto extends Movable{ 

  private    Vetor   Ke, Ka, Kd;
  public  Superficie superficie = null;


  public abstract Ponto colisao(Vetor p0, Vetor dr);


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

  public Objeto addReflection(Superficie refletora){

    this.superficie = refletora;

    return this;
  }
}