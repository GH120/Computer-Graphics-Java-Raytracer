package modelos.objetos;
import algebra.*;
import modelos.Objeto;
import modelos.Ponto;

public abstract class ObjetoSimples extends Objeto{
  
  //CALCULA POSIÇÃO NAS COORDENADAS RELATIVAS DO OBJETO
  abstract Ponto colisaoObjeto(Vetor p0, Vetor dr);

  //TRANSFORMA PARA COORDENADAS DO OBJETO, CALCULA COLISÃO 
  //RETORNA O PONTO TRANSFORMADO PARA COORDENADAS DE MUNDO
  public Ponto colisao(Vetor p0, Vetor dr){

    if(inalterado()) return colisaoObjeto(p0,dr);

    Vetor posicaoRelativa = toViewer(new Posicao(p0)).tresD();

    Vetor direcaoRelativa = toViewer(dr.quatroD()).tresD();
    
    Ponto ponto = colisaoObjeto( posicaoRelativa, direcaoRelativa);
    
    if(ponto == null) return null;

    return ponto.toWorld();
  }

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
  
}