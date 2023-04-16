package algebra;

public class RotacaoZ extends Rotacao {
    public RotacaoZ(double O){
      super(
        new Vetor(cos(O) , -sin(O), 0,  0),
        new Vetor(sin(O) ,  cos(O), 0,  0),
        new Vetor(  0    ,    0   , 1,  0),
        new Vetor(  0    ,    0   , 0,  1)
      );
    }
  }