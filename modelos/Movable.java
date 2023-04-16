package modelos;
//MATRIZ DIRETA CONTEM EIXOS I,J,K E POSICAO
//QUALQUER OPERAÇÃO MODIFICA A REFERÊNCIA DOS VETORES
//INVERSA ARMAZENADA PARA ACUMULAR OPERAÇÕES NA DIRETA
import algebra.*;


public class Movable{

  public Matriz toWorld  =  new Identidade();
  public Matriz inversa  =  new Identidade();

  public Movable aplicar(Matriz ...matrizes){

    Translacao Origem_to_Posicao = getPivo();

    //Move o pivô para a origem
    apply(Origem_to_Posicao.inversa());

    //Aplica as matrizes de transformação
    transformar(matrizes);

    //Retorna a posição original
    apply(Origem_to_Posicao);

    //Aplica as translações
    translacionar(matrizes);
    
    return this;
  }

  //Aplica as matrizes de translação
  void translacionar(Matriz ...matrizes){
    
    for(Matriz matriz : matrizes){

      if(!(matriz instanceof Translacao)) continue;
      
      apply(matriz);
    }
    
  }

  //Aplica as matrizes de transformação
  //Isto é, todas menos a translação
  //Já que exigem que o pivô esteja na origem
  void transformar(Matriz ...matrizes){
    
    for(Matriz matriz : matrizes){

      if(matriz instanceof Translacao) continue;
      
      apply(matriz);
    }
    
  }

  //Aplica uma matriz tanto na toWorld quanto na inversa
  void apply(Matriz matriz){

    //matriz.vezes(toWorld) -> ordem invertida
    toWorld = matriz.vezes(toWorld);
      
    inversa = inversa.vezes(matriz.inversa());
  }

  Translacao getPivo(){
    
    double[] posicao = toWorld.getColuna(4-1).valores;

    return new Translacao(posicao[0],posicao[1],posicao[2]);
  }

  //Transforma vetor para coordenadas de mundo
  public Vetor toWorld(Vetor vetor) {
    return toWorld.vezes(vetor);
  }

  //Transforma vetor para coordenadas relativas 
  public Vetor toViewer(Vetor vetor){
    return inversa.vezes(vetor);
  }

  public Vetor corrigirNormal(Vetor normal){
    return inversa.transposta().vezes(normal);
  }

  public boolean inalterado(){
    return toWorld.isIdentity();
  }
}