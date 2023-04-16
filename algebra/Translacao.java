package algebra;

public class Translacao extends Matriz{

  double x,y,z;

  public Translacao(double x, double y, double z){
    super(
      new Vetor(1,0,0,x),
      new Vetor(0,1,0,y),
      new Vetor(0,0,1,z),
      new Vetor(0,0,0,1)
    );

    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Matriz inversa(){
    return new Translacao(-x,-y,-z);
  }
}