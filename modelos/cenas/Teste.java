package modelos.cenas;
import algebra.*;
import modelos.*;
import modelos.fontes.*;
import modelos.objetos.*;
import modelos.reflexoes.Especular;
import modelos.reflexoes.Glossy;
import modelos.reflexoes.Refracao;

public class Teste extends Cena{

  public Teste(){
    
    Objetos(

            new Esfera(0, 0, -200)
            .setRaio(100)
            .setKe(0,0,0)
            .setKd(0,0,0)
            .setKa(0,0,0)
                    .addReflection(new Refracao(2, 1))
            ,

            new Plano(2000,-1500,-4000)
            .setNormal(0,0,1)
            .setKd(0, 0, 1)
            .setKa(0, 0, 1)
            .setKe(0, 0, 1),

            new Plano(2000,-1500,0)
            .setNormal(-1,0,0)
            .setKd(0, 1, 0)
            .setKa(0, 1, 0)
            .setKe(0, 1, 0),

            new Plano(-2000,-1500,0)
            .setNormal(1,0,0)
            .setKd(1, 0, 0)
            .setKa(1, 0, 0)
            .setKe(1, 0, 0),

            new Piso(
                    (Plano) new Plano(0,-1500,0)
                           .setNormal(0,1,0)
                           .setKd(1,1,1)
                           .setKa(1,1,1)
                           .setKe(1.0, 1.0, 1.0),
                    "images.jpeg")
            .setEscala(10)
            .addReflection(new Especular())
    );

    background = new Vetor(0.1,0.1,0.1);

    Fontes(
      new Fonte(-100, 140, -20).setCor(0.5f,0.5f,0.5f)
    );
  }
}