package modelos.reflexoes;

import java.util.List;

import algebra.*;
import modelos.*;

public class Refracao extends Especular{

    double indiceRefracao;

    public Refracao(double indiceRefracao, double eficiencia){
        this.eficiencia = eficiencia;
        this.indiceRefracao = indiceRefracao;
    }

    public void refletir(Ponto ponto, Raio raio, List<Raio> raios){  

        raios.add(refracao(ponto, raio));
    }

    public Raio refracao(Ponto ponto, Raio raio) {
    
        double n1, n2;

        if (raio.interno) {
            // Ray is exiting the object, so swap refractive indices
            n1 = indiceRefracao;
            n2 = 1.0;
        } else {
            // Ray is entering the object
            n1 = 1.0;
            n2 = indiceRefracao;
        }

        Vetor normal = ponto.normal;
        double cosI = -raio.direcao.escalar(normal);

    
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