package algebra;

public class Identidade extends Matriz{
  
  public Identidade(){
    super(
      new Vetor(1,0,0,0),
      new Vetor(0,1,0,0),
      new Vetor(0,0,1,0),
      new Vetor(0,0,0,1)
    );
  }

  public boolean isIdentity(){
    return true;
  }

  public Vetor vezes(Vetor vetor){
    return new Vetor(vetor.valores);
  }

  public Matriz vezes(Matriz matriz){
    return new Matriz(matriz.vetores);
  }

  public Matriz transposta(){
    return new Identidade();
  }
  
}