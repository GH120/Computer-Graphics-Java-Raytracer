package modelos.objetos;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import algebra.*;
import modelos.*;

public abstract class Textura<Prop extends Objeto> extends Objeto{

  public String path;
  Prop objeto;
  BufferedImage imagem;

  abstract Vetor mapear(Vetor pi);

  public Textura(Prop objeto, String path){
    this.objeto = objeto;
    setTextura("Imagens/" + path);
  }

  public void setTextura(String path){
    
    this.path = path;

    try{
      imagem = ImageIO.read(new File(path));
    }
    catch(Exception e){
      System.out.println("Erro na abertura do arquivo: " + path);
    }
  }

  public Ponto colisao(Vetor p0, Vetor dr){

    Ponto ponto = ((Objeto)objeto).colisao(p0,dr);

    if(ponto != null) ponto.setOrigem(this);
    
    return ponto;
  }

  public Vetor getKd(Vetor pi){
    return objeto.getKd(pi).mult(mapear(pi));
  }

  public Vetor getKa(Vetor pi){
    return objeto.getKa(pi).mult(mapear(pi));
  }

  public Vetor getKe(Vetor pi){
    return objeto.getKe(pi).mult(mapear(pi));
  }

  public Objeto setKe(double ...rgb){
    objeto.setKe(rgb);
    return this;
  }

  public Objeto setKd(double ...rgb){
    objeto.setKd(rgb);
    return this;
  }

  public Objeto setKa(double ...rgb){
    objeto.setKa(rgb);
    return this;
  }
}