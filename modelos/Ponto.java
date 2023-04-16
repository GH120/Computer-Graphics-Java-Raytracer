package modelos;

import algebra.*;

public class Ponto{
  
  public Vetor pos;
  public Vetor normal;
  public Objeto objeto;

  public Ponto(Objeto objeto, Vetor pos, Vetor normal){
    this.pos = pos;
    this.normal = normal;
    this.objeto = objeto;
  }

  public Vetor getKd(){
    return objeto.getKd(pos);
  }

  public Vetor getKe(){
    return objeto.getKe(pos);
  }

  public Vetor getKa(){
    return objeto.getKa(pos);
  }

  //Retorna o ponto transformado para as coordenadas de mundo
  public Ponto toWorld(){
    
    return new Ponto(objeto,
                     objeto.toWorld(new Posicao(pos)).tresD(),
                     objeto.corrigirNormal(normal.quatroD()).tresD());
  }

  public Ponto setNormal(Vetor normal){
    this.normal = normal;
    return this;
  }

  public Ponto setPosition(Vetor posicao){
    this.pos = posicao;
    return this;
  }

  public Ponto setOrigem(Objeto origem){
    this.objeto = origem;
    return this;
  }

  public void printar(){
    pos.printar();
    normal.printar();
    System.out.println(objeto);
  }
}