package modelos.cameras;
import algebra.*;

//Consertar...
public class Ortografica extends Projecao {

    public Vetor incidencia;
  
    public Vetor getDirecao(Vetor quadrante){
      return incidencia;
    }
  
    Projecao setBoundaries(double n, double f, double t, 
                           double b, double r, double l){
  
      double a11 =    2   /(r-l);
      double a14 =  (r+l) /(r-l);
      double a22 =    2   /(t-b);
      double a24 =  (t+b) /(t-b);
      double a33 =   -2   /(f-n);
      double a34 = -(f+n) /(f-n);
  
      projecao = new Matriz(
                    new Vetor(a11, 0  , 0  , a14),
                    new Vetor( 0 , a22, 0  , a24),
                    new Vetor( 0 , 0  , a33, a34),
                    new Vetor( 0 , 0  , 0  , 0  )
                 );
      
      return this;
    }
  
    public Projecao setIncidencia(Vetor direcao){
      
      incidencia = direcao.tresD().unitario();
      
      return this;
    }
  
    
  }