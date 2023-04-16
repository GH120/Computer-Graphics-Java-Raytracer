package algebra;
//Matriz de HouseHolder

public class Reflexao extends Matriz{

  Vetor normal;
  
  public Reflexao(double x, double y, double z){

    super(HouseHolder(new Vetor(x,y,z,0)));

    normal = new Vetor(x,y,z,0);
  }

  static Matriz HouseHolder(Vetor n){
    return new Identidade().menos(n.produtoExterno(n).vezes(2));
  }

  public Matriz inversa(){
    return HouseHolder(normal);
  }
}