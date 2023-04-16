package algebra;

public class Matriz{
  
  Vetor[] vetores;
  int linhas, colunas;

  public Matriz(Matriz matriz){
    vetores = new Vetor[matriz.size()];
    linhas = matriz.size();
    colunas = matriz.getLinha(0).size();
    for(int i=0; i<linhas; i++){
      vetores[i] = new Vetor(colunas);
    }
  }

  public Matriz(Vetor ...vetores){
    linhas = vetores.length;
    colunas = vetores[0].size();
    this.vetores = vetores;
  }

  public Matriz mais(Matriz matriz){
    Matriz resultado = new Matriz(matriz.vetores);
    int i = 0;
    for(Vetor linha : matriz.vetores){
      resultado.vetores[i] = getLinha(i++).mais(linha);
    }
    return resultado;
  }

  public Matriz menos(Matriz matriz){
    return mais(matriz.vezes(-1));
  }

  public Matriz vezes(double a){
    Matriz resultado = new Matriz(this);
    for(int i =0; i<linhas;i++){
      resultado.vetores[i] = vetores[i].vezes(a);
    }
    return resultado;
  }

  public Vetor vezes(Vetor vetor){
    Vetor resultado = new Vetor(vetor.valores);
    int i = 0;
    for(Vetor linha : vetores){
      resultado.valores[i++] = vetor.escalar(linha);
    }
    return resultado;
  }

  public Matriz vezes(Matriz matriz){
    Matriz resultado = new Matriz(matriz);
    for(int i = 0;i<linhas;i++){
      resultado.vetores[i] = vezes(matriz.getColuna(i));
    }
    return resultado.transposta();
  }

  public Matriz transposta(){
    Matriz transposta = new Matriz(this);
    for(int c = 0; c < colunas; c++){
      transposta.vetores[c] = getColuna(c);
    }
    return transposta;
  }
  
  // ALGORITMO NÃO IMPLEMENTADO, APENAS PLACEHOLDER 
  public Matriz inversa(){
    return null;
  }

  public Vetor getColuna(int c){
    Vetor coluna = new Vetor(vetores.length);
    int i = 0;
    for(Vetor linha : vetores){
      coluna.valores[i++] = linha.valores[c];
    }
    return coluna;
  }

  public void printar(){
    System.out.println(this);
    for(Vetor linha : vetores){
      linha.printar();
    }
  }

  public Vetor getLinha(int i){
    return vetores[i];
  }

  public int size(){
    return vetores.length;
  }

  public static Matriz Identidade(){
    return 
      new Matriz(new Vetor(1,0,0,0),
                 new Vetor(0,1,0,0),
                 new Vetor(0,0,1,0),
                 new Vetor(0,0,0,1));
  }

  public boolean isIdentity(){
    return false;
  }
}