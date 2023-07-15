package modelos.cenas;
import algebra.*;
import modelos.*;
import modelos.fontes.*;
import modelos.objetos.*;
import modelos.reflexoes.*;

public class Teste2 extends Cena{

  public Teste2(){
    
    Objetos(

            new MalhaOBJ("mesa.obj")
            .construir()
            .setKe(0.5,0.8,0.2)
            .setKa(0.5,0.8,0.2)
            .setKd(0.5,0.8,0.2)
            .aplicar(
                new Translacao(-20,-150,-185)
            )
            ,

            // new Plano(2000,-1500,-4000)
            // .setNormal(0,0,1)
            // .setKd(0, 0, 1)
            // .setKa(0, 0, 1)
            // .setKe(0, 0, 1),

            // new Plano(2000,-1500,0)
            // .setNormal(-1,0,0)
            // .setKd(0, 1, 0)
            // .setKa(0, 1, 0)
            // .setKe(0, 1, 0),

            // new Plano(-2000,-1500,0)
            // .setNormal(1,0,0)
            // .setKd(1, 0, 0)
            // .setKa(1, 0, 0)
            // .setKe(1, 0, 0),

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

    FontesExtensas(

      new Extensa(
            new Esfera(0, 100, 50)
            .setRaio(50)
      )

    );
  }
}
