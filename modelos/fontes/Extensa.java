package modelos.fontes;

import algebra.*;
import modelos.*;

public class Extensa extends Fonte {
    
    Objeto geometria;

    public Extensa(Objeto geometria){
        this.geometria = geometria;
    }

    //Ao invés da luz lançar um raio para o ponto visualizado
    //O raytracer vai lançar varios raios e ver qual acerta a luz
    //Ele vai pegar o ponto que o raio acerta essa fonte luminosa extensa,
    //Levar em conta o desvio que custa eficiencia da luz, além da intensidade do raio
    //A intensidade resultante é a contribuida para o resultado final
    public Vetor luz(Ponto colidido, Raio raio){

        Vetor normal = colidido.normal;

        double desvio = Math.abs(normal.escalar(raio.direcao));

        Vetor eficiencia = raio.intensidade.vezes(desvio);

        //A luminosidade original de uma fonte pontual 
        // Vetor l = raio.direcao.vezes(-1).unitario();
        // Vetor n = origem.normal;
        // Vetor luminosidade = super.luz(origem, viewer);

        return raio.intensidade.mult(eficiencia);
    }

    public Ponto colisao(Raio raio){
        return geometria.colisao(raio.origem, raio.direcao);
    }
}
