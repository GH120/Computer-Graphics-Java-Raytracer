package modelos.objetos;
import algebra.*;
import modelos.Ponto;

public class Plano extends ObjetoSimples {
  public Vetor P1;
  public Vetor n;
  
  public Plano (double...eixos){
    P1 = new Vetor(eixos); //Transformar de volta em centro
  }

  public Plano setNormal(double ...eixos){
    n = new Vetor(eixos);
    return this;
  } 
  
  Ponto colisaoObjeto(Vetor p0, Vetor dr) {
    
    double denominador = dr.escalar(n);

    //Verificar se não causa nenhum erro, backface culling
    if(denominador == 0) return null;

    double tint = (P1.menos(p0).escalar(n)/denominador);

    if(tint < 0) return null;

    Vetor ponto = p0.mais(dr.vezes(tint));
    
    return getPonto(ponto, n);
  }

  public boolean inside(Vetor centro, double tamanho){
    return P1.menos(centro).escalar(n) < tamanho;
  }

  public Esfera BoundingBox(){
    return null;
  }
}