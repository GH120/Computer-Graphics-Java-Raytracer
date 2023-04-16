package modelos.cenas;
import algebra.*;
import modelos.*;
import modelos.fontes.*;
import modelos.objetos.*;

public class Galpao extends Cena {

  public Galpao() {

    Objetos(
            //Mesa 
            Mesa()
            .aplicar(
              new Translacao(0,0,-500)
            ),
      
            //Arvore
            Arvore()
            .aplicar(
              new Translacao(125,100,-425)
            ),
      
            //Galpão
            Construcao()
            .aplicar(
              new Translacao(-175,-100,-200)
            ),

            Piso()
            .aplicar(
              new Translacao(-100,0,0)
            ),

            Fundo()
            .aplicar(
              new Translacao(0,0,-2000)
            )
      
    );

    Fontes(
        new Fonte(125, 300, -300).setCor(0.4f, 0.4f, 0.4f)
    );
  }


  

  //Abaixo os props

  Conjunto Mesa(){
    
    return new Conjunto(
              
                  new Cubo(1)
                  .renderizar()
                  .setTextura("images.jpeg")
                  .aplicar(
                    new Translacao(0,95,0),
                    new Escala(250,5,150)
                  ),
    
                  new Cubo(1)
                  .renderizar()
                  .setTextura("images.jpeg")
                  .aplicar(
                    new Escala(5,95,150)
                  ),
    
                  new Cubo(1)
                  .renderizar()
                  .setTextura("images.jpeg")
                  .aplicar(
                    new Translacao(245,0,0),
                    new Escala(5,95,150)
                  )
            )
            .setFronteira(
              new Esfera(125,50,75).setRaio(125*1.4143)
            );
  }

  Conjunto Arvore(){
    
    return new Conjunto(

              new Cilindro(0,0,0)
              .setTopo(0,9,0)
              .setRaio(30)
              .renderizar()
              .setKe(0.79215, 0.64313, 0.44705)
              .setKd(0.79215, 0.64313, 0.44705)
              .setKa(0.79215, 0.64313, 0.44705),

              new Cilindro(0,9,0)
              .setTopo(0,49,0)
              .setRaio(6)
              .renderizar()
              .setKe(0.79215, 0.64313, 0.44705)
              .setKd(0.79215, 0.64313, 0.44705)
              .setKa(0.79215, 0.64313, 0.44705),

              new Cone(0,49,0)
              .setTopo(0,199,0)
              .setRaio(60)
              .renderizar()
              .setKe(0.38039, 0.54117, 0.23921)
              .setKd(0.38039, 0.54117, 0.23921)
              .setKa(0.38039, 0.54117, 0.23921),

              new Esfera(0,199,0)
              .setRaio(4.5)
              .setKe(1.0000, 0.87451, 0.0)
              .setKd(1.0000, 0.87451, 0.0)
              .setKa(1.0000, 0.87451, 0.0)
          )
          .setFronteira(
            new Cilindro(0,0,0)
            .setTopo(0,204,0)
            .setRaio(60)
            .renderizar()
          )
          ;
  }

  Conjunto Construcao(){

    
    return new Conjunto(

            //Frontal
            Portico(),

            //Posterior
            Portico()
            .aplicar(
              new Translacao(0,0,-1030)
            ),          

            ParedePosterior()
            .aplicar(
              new Translacao(0,0,-1030)
            ),

            ParedeLateral()
            .aplicar(
              new Translacao(30,0,-1000)
            ),

            ParedeLateral()
            .aplicar(
              new Translacao(650,0,-1000)
            ),

            Teto()
            .aplicar(
              new Translacao(50,500,-1000)
            )
      
        )
        .setFronteira(
          new Cilindro(350,350,0)
          .setTopo(350,350,-1050)
          .setRaio(500)
          .renderizar()
        );

      
  }

  Conjunto Portico(){
    
    return new Conjunto(
      
                  PorticoDireito(),
      
                  PorticoDireito()
                  .aplicar(
                    new RotacaoY(180),
                    new Translacao(700,0,30)
                  )
          );
    
  }

  Conjunto PorticoDireito(){
    
    return new Conjunto(
              
                  new Cubo(1)
                  .renderizar()
                  .setTextura("madeira2.jpg")
                  .aplicar(
                    new Escala(50,500,30)
                  ),
    
                  new Cubo(1)
                  .renderizar()
                  .setTextura("madeira2.jpg")
                  .aplicar(
                    new Translacao(50,450,0),
                    new Escala(300,50,30),
                    new CisalhamentoXY(0.75)
                  )
            );
  }

  Objeto ParedePosterior(){
    
    return new Cubo(1)
           .renderizar()
           .setTextura("aço.jpg")
           .aplicar(
             new Escala(600,500,20)
           );
    
  }

  Objeto ParedeLateral(){

    return new Cubo(1)
           .renderizar()
           .setTextura("aço.jpg")
           .aplicar(
             new Escala(20,500,1000)
           );
            
  }

  Conjunto Teto(){

    return new Conjunto(
                
                new Cubo(1)
                .renderizar()
                .setTextura("telhado.jpg")
                .aplicar(
                  new Escala(300,50,1000),
                  new CisalhamentoXY(0.75)
                ),

                new Cubo(1)
                .renderizar()
                .setTextura("telhado.jpg")
                .aplicar(
                  new Escala(300,50,1000),
                  new CisalhamentoXY(0.75),
                  new RotacaoY(180),
                  new Translacao(600,0,1000)
                )
    );
    
  }

  Objeto Piso(){
    return new Piso(

            (Plano)
             new Plano(0,0,0)
            .setNormal(0,1,0)
            .setKe(1,1,1)
            .setKd(1,1,1)
            .setKa(1,1,1),
            

            "grama.jpeg"
    );
  }

  Objeto Fundo(){
    return new Piso(

              (Plano)
               new Plano(0,0,0)
              .setNormal(0,0,1)
              .setKe(1,1,1)
              .setKd(1,1,1)
              .setKa(1,1,1),
              
  
              "background.jpg"
          )
          .setEscala(12);
  }
}