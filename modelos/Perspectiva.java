package modelos;
import algebra.*;

public class Perspectiva extends Projecao{

    double zoom;
  
    public Vetor getDirecao(Vetor quadrante){
  
      Vetor foco = coordenadas.getColuna(2);
      
      return foco.vezes(zoom).mais(quadrante).tresD().unitario();
    }
  
    public Projecao setZoom(double zoom){
      
      this.zoom = zoom;
      
      return this;
    }
  
    Projecao setBoundaries(double n, double f, double t, 
                           double b, double r, double l){
  
      double a11 =    2*n /(r-l);
      double a13 =  (r+l) /(r-l);
      double a22 =    2*n /(t-b);
      double a23 =  (t+b) /(t-b);
      double a33 = -(f+n) /(f-n);
      double a34 = -2*f*n /(f-n);
  
      projecao = new Matriz(
                    new Vetor(a11, 0  , a13, 0  ),
                    new Vetor( 0 , a22, a23, 0  ),
                    new Vetor( 0 , 0  , a33, a34),
                    new Vetor( 0 , 0  , -1 , 0  )
                 );
      
      return this;
    }

    public double getZoom(){
        return zoom;
    }
  }
  