package algebra;

public class Posicao extends Vetor{
  public Posicao(double ...eixos){
    super(eixos[0],eixos[1],eixos[2],1.0);
  }

  public Posicao(Vetor eixos){
    super(eixos.get(0),eixos.get(1),eixos.get(2),1.0);
  }
}