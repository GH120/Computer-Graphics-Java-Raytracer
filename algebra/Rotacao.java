package algebra;

public abstract class Rotacao extends Matriz {

  public Rotacao(Vetor ...vetores){
    super(vetores);
  }

  public Matriz inversa(){
    return this.transposta();
  }
  
  public static double cos(double theta) {
    return Math.cos(Math.toRadians(theta));
  }

  public static double sin(double theta) {
    return Math.sin(Math.toRadians(theta));
  }
}