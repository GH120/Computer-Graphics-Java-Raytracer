package modelos.cenas;
import algebra.*;
import modelos.*;
import modelos.fontes.*;
import modelos.objetos.*;
import modelos.reflexoes.Especular;
import modelos.reflexoes.Glossy;

public class NatalOtimizado extends Cena{

  public NatalOtimizado(){
    
    Objetos(
            new Plano(200,-150,-400)
            .setNormal(0,0,1)
            .setKd(0.686, 0.933, 0.933)
            .setKa(0.686, 0.933, 0.933)
            .setKe(0.686, 0.933, 0.933),

            new Plano(200,-150,0)
            .setNormal(-1,0,0)
            .setKd(0.686, 0.933, 0.933)
            .setKa(0.686, 0.933, 0.933)
            .setKe(0.686, 0.933, 0.933),

            new Plano(-200,-150,0)
            .setNormal(1,0,0)
            .setKd(0.686, 0.933, 0.933)
            .setKa(0.686, 0.933, 0.933)
            .setKe(0.686, 0.933, 0.933),

            new Plano(0,150,0)
            .setNormal(0,-1,0)
            .setKd(0.933, 0.933, 0.933)
            .setKa(0.933, 0.933, 0.933)
            .setKe(0.933, 0.933, 0.933)
            .addReflection(new Glossy(0.1,4,1)),

            new Piso(
                    (Plano) new Plano(0,-150,0)
                           .setNormal(0,1,0)
                           .setKd(1,1,1)
                           .setKa(1,1,1)
                           .setKe(1.0, 1.0, 1.0),
                    "images.jpeg")
            .setEscala(3)
            .addReflection(new Glossy(0.05, 4, 1)),

            (Conjunto)
            new Conjunto
            (     
                  new Cilindro(0, -150, -200)
                  .setTopo(0, -60, -200)
                  .setRaio(5)
                  .construir()
                  .setKe(0.824, 0.706, 0.549)
                  .setKd(0.824, 0.706, 0.549)
                  .setKa(0.824, 0.706, 0.549),

                  new Cubo(40)
                  .construir()
                  .setKe(1.0, 0.078, 0.576)
                  .setKd(1.0, 0.078, 0.576)
                  .setKa(1., 0.078, 0.576)
                  .aplicar(
                    new Translacao(-20,-150,-185),
                    new RotacaoZ(40),
                    new RotacaoY(30),
                    new RotacaoX(27),
                    new Translacao(0,20,0)
                  ),
      
                  new Cone(0,-50,-200)
                  .setTopo(0,90,-200)
                  .setRaio(90)
                  .construir()
                  .setKe(0.0, 1.0, 0.498)
                  .setKd(0.0, 1.0, 0.498)
                  .setKa(0.0, 1.0, 0.498),

                  new Esfera(0, 95, -200)
                  .setRaio(5)
                  .setKe(0.854, 0.647, 0.125)
                  .setKd(0.854, 0.647, 0.125)
                  .setKa(0.854, 0.647, 0.125)
            )
            .setFronteira(
              new Esfera(0,-25,-200)
              .setRaio(135)
            )
            .addReflection(new Especular())
            // .aplicar(
            //   new RotacaoY(45)
            // ) 
            //Testar para todos os objetos centrados na origem, 
            //Com todos eles em 0,0,0 aplicando matrizes de translação
            //O que acontece quando rotaciona o conjunto é que alguns rotacionam e outros não
    );

    // objetos.addReflection(new Especular());

    Fontes(
      new Fonte(-100, 140, -20).setCor(0.3f,0.3f,0.3f)
    );

    background = new Vetor(0.2,0.2,0.2);
  }
}