package modelos;
//Subdivide o espaço em oito cubos
//Tamanho do cubo maior => começar com uma constante 1000
//Obs: acionar limitação para computação de planos, não contar eles como objetos na subdivisão da octree
//Obs: também adicionar limitação para triângulos que compartilham o mesmo vértice
import java.util.ArrayList;
import java.util.LinkedList;

import algebra.*;
import modelos.objetos.*;

class Node{
  
  Vetor              centro;
  double             tamanho;
  int                profundidade;
  int                limite;
  ArrayList<Objeto>  objetos;
  Node[]             nodes;

  Node(Vetor centro, double tamanho){

    this.centro  = centro;
    this.tamanho = tamanho;  
    this.objetos = new ArrayList<>();
    this.nodes   = null;
    this.profundidade = 0;
    this.limite = 5;
  }

  Ponto colisao(Raio raio){

    System.out.println("Profundidade: " + profundidade);
    centro.printar();
    raio.origem.printar();

    Ponto ponto = null;

    Conjunto conjunto = new Conjunto();

    conjunto.Componentes(objetos);

    //Se poucos objetos, calcula colisão entre eles
    //Senão, desce um nível na árvore
    if(objetos.size() < limite) 
      ponto = conjunto.colisao(raio.origem.mais(raio.direcao),raio.direcao);
    else 
      ponto = getNode(raio.origem).colisao(raio);

    //Retorna ponto se ele colidiu em algo
    if(ponto != null) return ponto;

    //Se não houver colisão,
    //O raio continua seu percurso até a próxima Node
    //Se for uma Node fronteiriça, retorna null;
    while(calcularTrajetoria(raio)){
      ponto = colisao(raio);
      if(ponto != null) return ponto;
    }

    return null;
  }

  //Simplificar depois para usar vetores
  //Vai contra todos os princípios do java, pode gerar bugs por alterar a informação do raio
  //Refazer, provavelmente as contas estão erradas
  boolean calcularTrajetoria(Raio raio){

    if(nodes == null) return false;

    // //Deveria ser o getNode do pai?
    // Node atual = getNode(raio.origem);

    //Distancia = centro.menos(raio.origem);
    double alpha = centro.get(0) - raio.origem.get(0);
    double beta  = centro.get(1) - raio.origem.get(1);
    double gamma = centro.get(2) - raio.origem.get(2);

    double a = raio.direcao.get(0);
    double b = raio.direcao.get(1);
    double c = raio.direcao.get(2);
  
    //-20 tamanho 50 direcão negativa => -20 + 50 = -30 => como dr < 0, positivo
    //new Vetor((a<0)?..., (b<0)?...,(c<0)?...).mais(distancia)
    alpha += (a<0)? tamanho : -tamanho;
    beta  += (b<0)? tamanho : -tamanho;
    gamma += (c<0)? tamanho : -tamanho;

    //distancia.vezes(1/a,1/b,1/c)
    alpha /= a;
    beta  /= b;
    gamma /= c;

    Vetor deslocamento = raio.direcao.vezes(Double.min(Double.min(alpha,beta),gamma));

    if(deslocamento.get(0) < -tamanho || deslocamento.get(0) > tamanho) return false;
    if(deslocamento.get(1) < -tamanho || deslocamento.get(1) > tamanho) return false;
    if(deslocamento.get(2) < -tamanho || deslocamento.get(2) > tamanho) return false;
    
    raio.origem = raio.origem.mais(deslocamento);

    return true;
  }

  Node getNode(Vetor posicao){

    int index = 0;
    
    Vetor diferenca = centro.menos(posicao);
    
    index += (diferenca.get(0) < 0)? 4 : 0;
    index += (diferenca.get(1) < 0)? 2 : 0;
    index += (diferenca.get(2) < 0)? 1 : 0;

    return nodes[index];
  }
  
  /////////////////////////////////////////////
  //Abaixo os métodos para criação da árvore///
  /////////////////////////////////////////////
  
  Node processar(ArrayList<Objeto> objetos){

    if(profundidade >= 20) return this;

    if(muitos(objetos)) subdividir();

    return this;
  }

  boolean muitos(ArrayList<Objeto> objetos){
    
    for(Objeto objeto : objetos){

      Vetor relativo = objeto.toViewer(new Posicao(centro)).tresD();
      double raio = objeto.inversa.getLinha(0).get(0)*tamanho;
      
      if(objeto.accpet(new DentroDeCubo()))
        this.objetos.add(objeto);
    }


    return this.objetos.size() > limite;
  }

  void subdividir(){
    
    nodes = new Node[8];

    for(int i=0; i < 8; i++){   
      nodes[i] = getSubdivisao(i);
      nodes[i].profundidade = profundidade + 1;
      nodes[i].processar(objetos);
    }
    
  }

  Node getSubdivisao(int direcao){

    double z = (direcao%2   == 0)? -1 : 1;
    double y = (direcao/2%2 == 0)? -1 : 1;
    double x = (direcao/4%2 == 0)? -1 : 1;

    Vetor centro = this.centro.mais(new Vetor(x,y,z).vezes(tamanho/2));

    return new Node(centro,tamanho/2);
  }

  Node filtrar(){

    if(nodes == null) return this;
    
    for(Node child : nodes){
      
      if(profundidade > 3 && !child.isOptimized()) 
        child.nodes = null;
      
      child.filtrar();
    }
    return this;
  }

  boolean isOptimized(){

    if(nodes == null) return false;

    for(Node node : nodes){
      
      if(node.objetos.size() == 0) 
        continue;
      if(node.objetos.size() < objetos.size() - limite)
        return true;
    }
    
    return false;
  }

  String printar(int profundidade){

    String log = "\n [\n" + 
                  "Objetos:" + objetos.toString() + 
                  "\n Profundidade: " + profundidade +
                  "\n Centro: " + centro.get(0) + "," + centro.get(1) + "," + centro.get(2) + 
                  "\n Nodes: \n {"; 

    if(nodes == null) return log + "\n}\n],";
    
    for(Node node : nodes)
      log = log + node.printar(profundidade + 1);

    log = log + "\n}\n];";

    if(profundidade == 0) System.out.println(log);

    return log;
  }

  void print(){
    if(objetos.size() > 0) System.out.print( " " + objetos.size()+" ");
    else System.out.print(" O ");
  }
}

class Octree extends Node{

  Octree(Vetor centro, double tamanho){
    super(centro, tamanho);
  }


  Node processar(ArrayList<Objeto> objetos){

    for(Objeto objeto : objetos){
      if(objeto instanceof Composto && !(objeto instanceof Cone))
        extrair(objeto);
      else
        this.objetos.add(objeto);
    }

    subdividir();

    return this;
  }

  void extrair(Objeto objeto){

    Composto composto = (Composto) objeto;
    
    for(Objeto componente : composto.componentes){  
      if(componente instanceof Composto) 
          extrair(componente);
      else
          this.objetos.add(componente);
    }
  }

  void print(){
    
    LinkedList<Node> pilha1 = new LinkedList<>();
    LinkedList<Node> pilha2 = new LinkedList<>();

    pilha1.add(this);

    do{
      pilha2 = new LinkedList<Node>();

      while(!pilha1.isEmpty()){
        Node node = pilha1.pop();

        // if(node.profundidade == 7) break;
  
        if(node.nodes != null){
          
          for(Node child : node.nodes){
            child.print();
            pilha2.add(child);
          }
          
          System.out.println(", \n");
        }

      }
      System.out.println("\n..............................................\n");
      
      pilha1 = (LinkedList<Node>)pilha2.clone();
    } while(!pilha2.isEmpty());
  }


  //Agora só falta o algoritmo para lidar com colisões de raios
  //Um raio deve entrar em um Nó, se tiver menos que 3 objetos ele calcula a colisão
  //Se tiver mais que três objetos, ele vai para uma subdivisão
  //Se ele não colidir com nenhum objeto, escolhe a próxima colisão
  //Ele escolhe a próxima colisão vendo se ainda há Nós no seu nível de profundidade, senão sobe um nível
  //Se chegar ao nível 0 e não colidir, então ele retorna null
  //Eficiência:
  //Como se pode colidir no máximo 4 nós em uma octree, percorrem-se no máximo 4^n nós para achar uma solução
  //No caso comum, os raios que colidem primeiro são rapidamente calculados.
  //Os raios que não colidem, geralmente vão acertar 2 a 3 nós dos quais a maioria é vazio

  //Suponha um raio p0, dr
  //Ele entra em uma Node da octree

  // Ponto calcularRaio(Raio raio){

  //   Ponto ponto;

  //   //Se poucos objetos, calcula colisão entre eles
  //   //Senão, desce um nível na árvore
  //   if(objetos.size() < 6) 
  //     ponto = objetos.colisao(raio.p0,raio.dr);
  //   else
  //     ponto = getNode(p0).calcularRaio(raio);

  //   //Retorna ponto se ele colidiu em algo
  //   if(ponto != null) return ponto;

  //   //Se não houver colisão,
  //   //O raio continua seu percurso até a próxima Node
  //   //Se for uma Node fronteiriça, retorna null;
  //   raio = getNodeVisinha(raio);
    
  //   while(raio != null){
  //     ponto = calcularRaio(raio);
  //     if(ponto != null) return ponto;
  //     raio = getNodeVisinha(raio);
  //   }

  //   return null;
  // }

  //getNodeVisinha
  //getPosicao
  //Filtrar Nodes desnecessárias (aquelas em que não há alteração significativa no número de objetos)
}