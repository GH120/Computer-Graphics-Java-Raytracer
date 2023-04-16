package algebra;

public class RotacaoX extends Rotacao {
    RotacaoX(double O){
      super(
        new Vetor(1,    0   ,  0    , 0),
        new Vetor(0,  cos(O),-sin(O), 0),
        new Vetor(0,  sin(O), cos(O), 0),
        new Vetor(0,    0   ,  0    , 1)
      );
    }
  }