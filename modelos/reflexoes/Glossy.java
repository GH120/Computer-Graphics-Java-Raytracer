package modelos.reflexoes;
import java.util.List;

import algebra.*;
import modelos.*;

//Falta adicionar o construtor...
public class Glossy extends Superficie{

    double roughness;

    public Glossy(double roughness, int pixelCount, double eficiencia){
        this.roughness  = roughness;
        this.eficiencia = eficiencia/pixelCount;
        this.pixelCount = pixelCount;
    }

    public void refletir(Ponto ponto, Raio raio, List<Raio> raios){

        for(int i=0; i < pixelCount;i++){

            Raio refletido = reflexao(ponto, raio);

            if(refletido.intensidade.escalar(refletido.intensidade) < 0.001) continue;

            refletido.intensidade = refletido.intensidade.vezes(eficiencia);

            // if(refletido.intensidade.modulo() > 0) {
            //     refletido.intensidade.printar();
            //     System.out.println(refletido.profundidade);
            // }
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
                                            ponto.normal.vezes(2*raio.direcao
                                            .escalar(ponto.normal)
                                            )
                                        )
                                        .unitario();

        //Cria um plano perpendicular a reflexão ideal
        Vetor eixoX = reflexaoIdeal.ortogonal();

        Vetor eixoY = eixoX.vetorial(reflexaoIdeal);

        //A reflexão perturbada vai ser um vetor ortogonal a ela nesse plano
        Vetor reflexaoPerturbada = eixoX.vezes(Math.random())
                                        .mais(eixoY.vezes(Math.random()))
                                        .vezes(0.1);

        Vetor reflexaoFinal = reflexaoIdeal.mais(reflexaoPerturbada).unitario();
    
        // Reflected ray has origin at collision point
        refletido.origem = ponto.pos.mais(reflexaoFinal);
    
        // Reflected ray has direction of reflected vector
        refletido.direcao = reflexaoFinal;
    
        // Intensity is reduced by the specular reflection factor
        refletido.intensidade = raio.intensidade.mult(ponto.getKe());
    
        return refletido;
    }
    
}