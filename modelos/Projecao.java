package modelos;
import algebra.*;

//Feito na base do improviso, refatorar depois...
//Lembrar de modificar a ortografica para colocar a incidencia relativa a coordenada z(toWorld?)
public abstract class Projecao {

  Matriz coordenadas;
  Matriz projecao;

  public abstract Vetor getDirecao(Vetor quadrante);

  Projecao setCoordenadas(Matriz toWorld){
    coordenadas = toWorld;
    return this;
  }
}
//Lanterna, sol e lampada