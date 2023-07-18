package modelos.objetos;
import algebra.*;
import modelos.*;

//É um cone com um contorno diferente e uma tampa adicional
public class Cilindro extends Cone{

  Circulo superior;

  public Cilindro(double ...eixos){

    // Kd = new Vetor(0f,0.5f,0.2f);
    // Ke = new Vetor(0.1f,0.1f,0.1f);
    
    this.base = new Vetor(eixos);
  }

  //Ele é um objeto composto de círculos inferior e superior, além do contorno do cilíndro
  public Cilindro construir(){
    
    inferior = new Circulo(base.valores);
    inferior.setNormal(dir.vezes(-1).valores);
    inferior.setRaio(raio);
    
    superior = new Circulo(vertice.valores);
    superior.setNormal(dir.valores);
    superior.setRaio(raio);

    componentes.add(inferior);
    componentes.add(superior);
    componentes.add(getContorno());
    
    return this;
  }

  //Contorno do Cilindro, um objeto simples que exclui os círculos inferior e superior
  public Objeto getContorno(){
    
    return new ObjetoSimples(){ 
    
      Ponto colisaoObjeto(Vetor p0, Vetor dr){
        
        Vetor BP0 = p0.menos(base); 
        Vetor BA = dir.vezes(BP0.escalar(dir));
        
        Vetor v = BP0.menos(BA);
        Vetor w = dr.menos(dir.vezes(dr.escalar(dir)));
        
        double a = w.escalar(w);
        double b = 2*v.escalar(w);
        double c = v.escalar(v) - raio*raio;
    
        Vetor pi = intersecaoRaio(a,b,c,p0,dr);
    
        if(pi == null) return null;
    
        double height = pi.menos(base).escalar(dir);
        
        if(height > altura || height < 0) return null;
    
        return getPonto(pi , normal(pi)); 
      }

      public Esfera BoundingBox(){
        //Raio da esfera que contém o cone

        double raioEsfera = (altura*altura+raio*raio)/(2*altura);

        Vetor R = dir.vezes(raio);

        Vetor centroEsfera = vertice.menos(R);

        return new Esfera(centroEsfera.valores).setRaio(raioEsfera);
      }
    };
  }

  Vetor normal(Vetor pi){
    
    Vetor BPI = pi.menos(base);
    Vetor BA = dir.vezes(BPI.escalar(dir));
    Vetor n = BPI.menos(BA).unitario();
    
    return n;
  }
}