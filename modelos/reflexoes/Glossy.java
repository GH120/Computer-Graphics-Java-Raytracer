package modelos.reflexoes;
import java.util.List;

import algebra.Vetor;
import modelos.Ponto;
import modelos.Raio;

//Falta adicionar o construtor...
public class Glossy extends Superficie{

    double roughness;

    public Glossy(double roughness, int pixelCount, double eficiencia){
        this.roughness  = roughness;
        this.eficiencia = eficiencia;
        this.pixelCount = pixelCount;
    }

    public void refletir(Ponto ponto, Raio raio, List<Raio> raios){

        eficiencia = eficiencia/pixelCount;

        for(int i=0; i < pixelCount;i++){

            Raio refletido = reflexao(ponto, raio);

            if(refletido.intensidade.escalar(refletido.intensidade) < 0.01) continue;

            refletido.intensidade = refletido.intensidade.vezes(eficiencia);

            if(refletido.intensidade.modulo() > 0) {
                refletido.intensidade.printar();
                System.out.println(refletido.profundidade);
            }
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