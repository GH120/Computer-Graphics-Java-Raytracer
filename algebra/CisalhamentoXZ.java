package algebra;

class CisalhamentoXZ extends Matriz{

    double lambda;
    
    public CisalhamentoXZ(double lambda){
      super(
        new Vetor(  1,    0   ,  0  ,  0),
        new Vetor(0, 1   ,  0  ,  0),
        new Vetor(  lambda,    0   ,  1  ,  0),
        new Vetor(  0,    0   ,  0  ,  1)
      );
      this.lambda = lambda;
    }
  
    public Matriz inversa(){
      return new CisalhamentoXY(-lambda);
    }
}