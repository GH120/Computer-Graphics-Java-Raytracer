package modelos.objetos;
import algebra.*;
import modelos.*;
import java.util.ArrayList;

//Contém vários objetos em uma região delimitada por um objeto chamado fronteira
//Além de ser a implementação concreta do composto,
//Visa economizar processamento ignorando regiões onde não haveria colisões usando a fronteira como fácil medidor
public class Conjunto extends Composto{

  public Objeto fronteira;
  Objeto disjuncao;

  public Conjunto(Objeto ...objetos){

    fronteira = null;
    disjuncao = null;
    
    Componentes(objetos);
    
  }

  public Ponto colisao(Vetor p0, Vetor dr){

    if(fronteira != null)
      if(fronteira.colisao(p0,dr) ==  null)
        return null;

    return super.colisao(p0,dr);
  }

  public Conjunto setFronteira(Objeto fronteira){
    
    this.fronteira = fronteira;
    
    return this;
  }

  public Conjunto setDisjuncao(Objeto disjuncao){

    this.disjuncao = disjuncao;

    return this;
  }

  public Composto Componentes(Objeto ...objetos){

    componentes = new ArrayList<>();
    
    for(Objeto objeto : objetos){
      componentes.add(objeto);
    }
    
    return this;
  }

  public Composto Componentes(ArrayList<Objeto> objetos){

    componentes = new ArrayList<>();
    
    for(Objeto objeto : objetos){
      componentes.add(objeto);
    }
    
    return this;
  }

  public Conjunto aplicar(Matriz... matrizes){
    
    super.aplicar(matrizes);

    if(fronteira != null) fronteira.aplicar(matrizes);

    return this;
  }
}

//Supondo a segmentação em octrees
//vamos ter cubos com um centro e um raio
//cada objeto vai ter uma equação para decidir se está dentro do cubo
//esfera é simples: basta transformar para world o centro e raio e ver se a distancia para o centro do cubo é menor que a soma dos raios.
//Para um cone, basta transformar ele numa esfera e repetir os passos acima
//Para um plano, basta ver se o raio é menor que a distancia do centro para o cubo
//Para um objeto composto, decompõe ele em seus constituintes e verifica para cada um se eles pertencem ao cubo
//Se o cubo tiver mais que x objetos, repetir a divisão em mais cubos
//Com isso, seria garantida uma eficácia log(n) na colisão de objetos, com pouco custo computacional.