package modelos;

import algebra.*;

import java.util.List;

//Preciso criar Umaa maneira de mudar a reflexão do raio baseado no Reflexão...
//Todo objeto vai ter Uma Reflexão que define Umaa reflexão para determinado ponto de colisão
//Dado Uma raio r ele vai gerar n raios ri
//Reflexão especular gera Uma raio refletido
//Criar Umaa classe Reflexão, todo objeto tem Uma Reflexão
//Para determinado ponto, Uma Reflexão retorna Umaa série de raios
//Uma Reflexão especular teria Uma índice de especularidade, ou seja, quanto ele reflete de luz
//Uma Reflexão glossy similarmente teria outro coeficiente
//Uma Reflexão translucido também
//Uma Reflexão de difusão monte-carlo teria também Uma coeficiente para cada raio refletido
//esse ultimo teria Uma calculo inicial com n raios para determinar a distribuição de probabilidades
//Melhor deixar para implementar como Uma raytracer o montecarlo

//Objeto => n materiais cada qual com Umaa razão para sua reflexão

//Logo, Uma Reflexão tem Umaa razão de eficiência para geração de raios

public abstract class Reflexao {

    int    pixelCount;
    double eficiencia = 1.0;

    abstract void refletir(Ponto ponto, Raio raio, List<Raio> raios);
}

class Especular extends Reflexao{

    void refletir(Ponto ponto, Raio raio, List<Raio> raios){

        Raio refletido = reflexao(ponto, raio);

        refletido.intensidade = refletido.intensidade.vezes(eficiencia);

        raios.add(refletido);
    }

    public Raio reflexao(Ponto ponto, Raio raio){

        Raio refletido = raio.refletido();

        if(raio.interno){ 
            ponto = new Ponto(ponto.objeto, 
                              ponto.pos,
                              ponto.normal.vezes(-1));
        }
        
    
        //Direção muda para o raio refletido no ponto
        refletido.direcao =  raio.direcao
                                  .menos(
                                      ponto.normal
                                      .vezes(
                                        2*raio.direcao
                                        .escalar(ponto.normal)
                                      )
                                  )
                                  .unitario();
    
        //O raio refletido tem origem no ponto de colisão
        refletido.origem = ponto.pos.mais(refletido.direcao);
    
        //Intensidade diminui pelo fator de reflexão especular
        refletido.intensidade = raio.intensidade.mult(ponto.getKe());
    
        return refletido;
      }
}

class Glossy extends Reflexao{

    double roughness;

    void refletir(Ponto ponto, Raio raio, List<Raio> raios){

        eficiencia = eficiencia/pixelCount;

        for(int i=0; i < pixelCount;i++){

            Raio refletido = reflexao(ponto, raio);

            refletido.intensidade = refletido.intensidade.vezes(eficiencia);
            
            raios.add(refletido);
        }
    }

    public Raio reflexao(Ponto ponto, Raio raio) {

        Raio refletido = raio.refletido();

        if(raio.interno){
            ponto = new Ponto(ponto.objeto, 
                              ponto.pos,
                              ponto.normal.vezes(-1));
        }
    
        // Direction of ideal reflection
        Vetor reflexaoIdeal = raio.direcao.menos(
            ponto.normal.vezes(2 * raio.direcao.escalar(ponto.normal))
        ).unitario();
    
        double theta = Math.acos(Math.pow(Math.random(), 1.0 / (roughness + 1)));

        double phi = 2 * Math.PI * Math.random();

        Vetor reflexaoPerturbada = new Vetor(
            Math.sin(theta) * Math.cos(phi),
            Math.sin(theta) * Math.sin(phi),
            Math.cos(theta)
        );

        Vetor reflexaoFinal = reflexaoPerturbada.mais(reflexaoIdeal).unitario();
    
        // Reflected ray has origin at collision point
        refletido.origem = ponto.pos.mais(reflexaoFinal);
    
        // Reflected ray has direction of reflected vector
        refletido.direcao = reflexaoFinal;
    
        // Intensity is reduced by the specular reflection factor
        refletido.intensidade = raio.intensidade.mult(ponto.getKe());
    
        return refletido;
    }
    
}

class Refracao extends Especular{

    double indiceRefracao;

    public void refletir(Ponto ponto, Raio raio, List<Raio> raios){  
        raios.add(refracao(ponto, raio));
    }

    public Raio refracao(Ponto ponto, Raio raio) {
    
        Vetor normal = ponto.normal;
        double cosI = -raio.direcao.escalar(normal);
    
        double n1, n2;

        if (raio.interno) {
            // Ray is exiting the object, so swap refractive indices
            n1 = indiceRefracao;
            n2 = 1.0;
            ponto = new Ponto(ponto.objeto, ponto.pos, ponto.normal.vezes(-1.0));
        } else {
            // Ray is entering the object
            n1 = 1.0;
            n2 = indiceRefracao;
        }
    
        double eta = n1 / n2;
        double k = 1.0 - eta * eta * (1.0 - cosI * cosI);
    
        if (k < 0.0) {
            Raio refletido = super.reflexao(ponto, raio);

            refletido.interno = true;

            return refletido;
        }
    
        Vetor direcaoRefratada = raio.direcao.vezes(eta)
                .mais(normal.vezes(eta * cosI - Math.sqrt(k)))
                .unitario();
        
        
        Raio refratado = raio.refletido();
    
        // Refracted ray has origin at collision point
        refratado.origem = ponto.pos.mais(direcaoRefratada);
    
        // Refracted ray has direction of refracted vector
        refratado.direcao = direcaoRefratada;
    
        // Intensity is reduced by the transmission factor
        refratado.intensidade = raio.intensidade.vezes(eficiencia);

        // Raio muda de meio físico
        refratado.interno = !raio.interno;
    
        return refratado;
    }
}