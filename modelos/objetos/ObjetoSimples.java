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
  
}