package modelos.cenas;

import algebra.*;
import modelos.*;
import modelos.fontes.*;
import modelos.objetos.*;

public class Natal extends Cena{

  public Natal(){
    
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
            .setKe(0.933, 0.933, 0.933),

            new Piso(
                    (Plano) new Plano(0,-150,0)
                           .setNormal(0,1,0)
                           .setKd(1,1,1)
                           .setKa(1,1,1)
                           .setKe(0.0, 0.0, 0.0),
                    "images.jpeg"
            ),

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
              new Translacao(-20,-150,-185)
            )
            ,

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
    );

    Fontes(
      new Fonte(-100, 140, -20).setCor(0.1f,0.1f,0.1f)
    );
  }
}