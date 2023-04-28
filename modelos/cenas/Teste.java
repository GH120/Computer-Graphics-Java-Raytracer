package modelos.cenas;
import algebra.*;
import modelos.*;
import modelos.fontes.*;
import modelos.objetos.*;
import modelos.reflexoes.Especular;
import modelos.reflexoes.Glossy;

public class Teste extends Cena{

  public Teste(){
    
    Objetos(

                  new Cubo(100)
                  .construir()
                  .setKe(1.0, 0.078, 0.576)
                  .setKd(1.0, 0.078, 0.576)
                  .setKa(1., 0.078, 0.576)
                  .aplicar(
                    new Translacao(-20,-50,-185),
                    new RotacaoZ(40),
                    new RotacaoY(30),
                    new RotacaoX(27),
                    new Translacao(0,20,0)
                  )
                  .addReflection(new Glossy(0,3,1)),

                  new Piso(
                    (Plano) new Plano(0,-150,0)
                           .setNormal(0,1,0)
                           .setKd(1,1,1)
                           .setKa(1,1,1)
                           .setKe(1.0, 1.0, 1.0),
                    "images.jpeg")
                .setEscala(3)
                .addReflection(new Especular()),

                new Plano(200,-150,-400)
            .setNormal(0,0,1)
            .setKd(0.2, 0.4, 0.4)
            .setKa(0.2, 0.4, 0.4)
            .setKe(0.2, 0.4, 0.4),

            new Plano(200,-150,0)
            .setNormal(-1,0,0)
            .setKd(0.2, 0.4, 0.4)
            .setKa(0.2, 0.4, 0.4)
            .setKe(0.2, 0.4, 0.4),

            new Plano(-200,-150,0)
            .setNormal(1,0,0)
            .setKd(0.2, 0.4, 0.4)
            .setKa(0.2, 0.4, 0.4)
            .setKe(0.2, 0.4, 0.4),

            new Plano(0,150,0)
            .setNormal(0,-1,0)
            .setKd(0.4, 0.4, 0.4)
            .setKa(0.4, 0.4, 0.4)
            .setKe(0.4, 0.4, 0.4)
            .addReflection(new Especular())
            // .aplicar(
            //   new RotacaoY(45)
            // ) 
            //Testar para todos os objetos centrados na origem, 
            //Com todos eles em 0,0,0 aplicando matrizes de translação
            //O que acontece quando rotaciona o conjunto é que alguns rotacionam e outros não
    );

    background = new Vetor(0.1,0.1,0.1);

    Fontes(
      new Fonte(-100, 140, -20).setCor(0.5f,0.5f,0.5f)
    );
  }
}