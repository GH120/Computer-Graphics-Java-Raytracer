package algebra;

public class CisalhamentoYX extends Matriz{

  double lambda;
  
  public CisalhamentoYX(double lambda){
    super(
      new Vetor(1,  lambda,  0  ,  0),
      new Vetor(0,    1   ,  0  ,  0),
      new Vetor(0,    0   ,  1  ,  0),
      new Vetor(0,    0   ,  0  ,  1)
    );
    this.lambda = lambda;
  }

  public Matriz inversa(){
    return new CisalhamentoYX(-lambda);
  }
}