package modelos.objetos;
import java.util.ArrayList;

import algebra.*;
import modelos.*;
import modelos.reflexoes.Reflexao;

public abstract class Composto extends Objeto{
  
  public ArrayList<Objeto> componentes = new ArrayList<>();

  public Ponto colisao(Vetor p0, Vetor dr){
    return maisPerto(p0,dr,getColisoes(p0, dr));   
  }

  Ponto[] getColisoes(Vetor p0, Vetor dr){
    
    Ponto[] colisoes = new Ponto[componentes.size()];

    int i = 0;
    for(Objeto objeto : componentes){
      colisoes[i++] = objeto.colisao(p0,dr);
    }

    return colisoes;
  }

  
  Ponto maisPerto(Vetor p0, Vetor dr, Ponto ...colisoes){
    
    Ponto maisProxima = null;
    for(Ponto colisao : colisoes){
      
      if(colisao == null) {
        continue;
      }
      else if(maisProxima == null) {
        maisProxima = colisao;
      }
      else if(maisProxima.pos.menos(p0).escalar(dr) > colisao.pos.menos(p0).escalar(dr)){
        maisProxima = colisao;
      }
    }

    return maisProxima;
  }

  Ponto colisaoObjeto(Vetor p0, Vetor dr){
    return null;
  }
  
  @Override
  public Composto setKe(double ...rgb){
    
    super.setKe(rgb);
    
    for(Objeto componente : componentes){
      componente.setKe(rgb);
    }
    
    return this;
  }
  
  @Override
  public Composto setKd(double ...rgb){

    super.setKd(rgb);
    
    for(Objeto componente : componentes){
      componente.setKd(rgb);
    }
    
    return this;
  }
  
  @Override
  public Composto setKa(double ...rgb){

    super.setKa(rgb);
    
    for(Objeto componente : componentes){
      componente.setKa(rgb);
    }
    
    return this;
  }

  @Override
  public Composto aplicar(Matriz... matrizes){

    super.aplicar(matrizes);
    
    for(Objeto componente : componentes){
      componente.aplicar(matrizes);
    }
    
    return this;
  }

  @Override
  public Composto addReflection(Reflexao reflexao){

    for(Objeto componente : componentes){
      componente.addReflection(reflexao);
    }

    return this;
  }

  public ArrayList<Objeto> getComponentes(){
    return componentes;
  }
  
}