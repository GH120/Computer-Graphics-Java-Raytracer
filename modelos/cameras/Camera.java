package modelos.cameras;
import algebra.*;
import modelos.*;

//Camera to world -> processar colisão -> world to camera?

public class Camera extends Movable{
  
  // Tamanho físico da janela na cena
  public double wJanela, hJanela;
  
  //Tipo de projeção
  public Projecao projecao;

  //Posição do olho, direção de visualização e plano sagital
  public Vetor Eye, LookAt, Up;


  public Camera setCoordenadas(Vetor Eye, Vetor LookAt, Vetor Up){

    this.Eye    = Eye;
    this.LookAt = LookAt;
    this.Up     = Up;

    Vetor direcao = Eye.menos(LookAt).unitario().quatroD();
    
    Vetor cima = Up.menos(Eye).unitario().quatroD();
    
    Vetor direita = cima.vetorial(direcao).unitario().quatroD();

    //Matriz de transformação para as coordenadas do mundo
    toWorld = new Matriz(direita,cima,direcao,new Posicao(Eye)).transposta();

    //Projeção perspectiva usada como padrão
    setProjecao(new Perspectiva().setZoom(Eye.menos(LookAt).modulo()));
    
    return this;
  }

  Vetor getFoco(){
    return toWorld.getColuna(2);
  }

  Vetor getEye(){
    return toWorld.getColuna(3);
  }

  public Camera setProjecao(Projecao projecao){
    
    this.projecao = projecao.setCoordenadas(toWorld);
  
    return this;
  }

  public Camera setDimensoes(double wJanela, double hJanela) {
    this.wJanela = wJanela;
    this.hJanela = hJanela;
    return this;
  }

  public Camera aplicar(Matriz ...matrizes){
    super.aplicar(matrizes);
    projecao.setCoordenadas(toWorld);
    return this;
  }
}