package modelos.raytracers;

import java.util.List;

import algebra.*;
import modelos.*;
import modelos.cameras.Camera;
import modelos.fontes.*;

public abstract class Raytracer{

  public int linhas, colunas;
  
  public Vetor[][] buffer;

  public Camera camera;

  public Cena cena;

  public abstract void  render();

  public abstract void  gerarRaios();

  public abstract void  calcularRaios();

  public abstract Vetor buscarCor(Raio raio, List<Raio> pilha);

  Vetor iluminar(Ponto ponto, Vetor viewer){

    Vetor luz = new Vetor(0,0,0);

    //Por algum motivo dá erro quando alterado
    luz = luz.mais(ponto.objeto.luzAmbiente(cena.L_ambiente));

    //Adiciona as contribuições das luzes ao ponto colido
    for(Fonte fonte : cena.fontes){
      
      //if(!sombra(fonte, ponto))
        
        luz = luz.mais(fonte.luz(ponto, viewer));
    }

    return luz;
  }

  boolean sombra(Fonte fonte, Ponto ponto){
    
    Vetor L = ponto.pos.menos(fonte.posicao);

    Ponto maisPerto = cena.objetos.colisao(fonte.posicao, L.unitario());

    if(maisPerto == null) return false;

    boolean mesmoObjeto = maisPerto.objeto == ponto.objeto;

    boolean maisLonge = maisPerto.pos.menos(fonte.posicao).modulo() < L.modulo();

    return (!mesmoObjeto && maisLonge);
  }

  //Inicializa o buffer para armazenar as cores dos pixels
  public Raytracer gerarBuffer() {
    buffer = new Vetor[linhas][colunas];
    for (int i = 0; i < linhas; i++)
      for (int j = 0; j < linhas; j++)
        buffer[i][j] = new Vetor(0,0,0);
    return this;
  }

}