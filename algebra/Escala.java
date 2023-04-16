package algebra;

public class Escala extends Matriz{

  double alpha,beta,gamma;

  public Escala(double a, double b, double c){    
    super(
      new Vetor(a,0,0,0),
      new Vetor(0,b,0,0),
      new Vetor(0,0,c,0),
      new Vetor(0,0,0,1)
    );

    alpha = a; beta = b; gamma = c;
  }

  public Matriz inversa(){
    return new Escala(1/alpha,1/beta,1/gamma);
  }
}