package algebra;

public class CisalhamentoXY extends Matriz{

    double lambda;
    
    public CisalhamentoXY(double lambda){
      super(
        new Vetor(  1,    0   ,  0  ,  0),
        new Vetor(lambda, 1   ,  0  ,  0),
        new Vetor(  0,    0   ,  1  ,  0),
        new Vetor(  0,    0   ,  0  ,  1)
      );
      this.lambda = lambda;
    }
  
    public Matriz inversa(){
      return new CisalhamentoXY(-lambda);
    }
  }