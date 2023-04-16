package algebra;

public class RotacaoY extends Rotacao {
    public RotacaoY(double O){
      super(
        new Vetor(cos(O) ,  0,  sin(O),  0),
        new Vetor(  0    ,  1,    0   ,  0),
        new Vetor(-sin(O),  0,  cos(O),  0),
        new Vetor(  0    ,  0,    0   ,  1)
      );
    }
  }