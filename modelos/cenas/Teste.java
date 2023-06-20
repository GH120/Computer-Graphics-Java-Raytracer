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
            .setKe(0.5,0.5,0)
            .setKd(0.5,0.5,0)
            .setKa(0.5,0.5,0)
                    .addReflection(new Refracao(1, 0.5))
            ,

            new Piso(
            (Plano) new Plano(0,0,-1000)
                    .setNormal(0,0,1)
                    .setKd(1,1,1)
                    .setKa(1,1,1)
                    .setKe(1.0, 1.0, 1.0),
            "images.jpeg")
            .setEscala(3)
    );

    background = new Vetor(0.1,0.1,0.1);

    Fontes(
      new Fonte(-100, 140, -20).setCor(0.5f,0.5f,0.5f)
    );
  }
}