package modelos.reflexoes;

import modelos.*;

import java.util.List;
import java.util.Random;

import algebra.*;

public class Difusa extends Superficie{

    Random rand = new Random();

    public Difusa(){
        eficiencia = 1;
    }

    public Difusa(double eficiencia){
        this.eficiencia = eficiencia;
    }
    
    public void refletir(Ponto ponto, Raio raio, List<Raio> raios){

        double phi   = rand.nextDouble() * Math.PI;
        double theta = rand.nextDouble() * Math.PI;

        double x = Math.sin(phi) * Math.cos(theta);
        double y = Math.sin(phi) * Math.sin(theta);
        double z = Math.cos(theta);

        Vetor direcao = new Vetor(x,y,z);

        Vetor normal  = ponto.normal;
        Vetor direita = normal.ortogonal();
        Vetor frente  = normal.vetorial(direita); //vetorial é ortogonal as duas

        Matriz toWorld = new Matriz(direita,normal, frente, new Vetor(0,0,0,0)).transposta();

        Vetor direcaoReal = toWorld.vezes(direcao.quatroD()).tresD().unitario();

        Raio refletido = raio.refletido();

        // System.out.println("ponto");
        // ponto.printar();
        // System.out.println("incidente");
        // raio.direcao.printar();
        // System.out.println("refletido");
        // direcaoReal.printar();
        
        //O quanto ele desviou da normal
        if(Double.isNaN(direcaoReal.escalar(normal))){
            normal.printar();
            direita.printar();
            frente.printar();
            System.exit(0);
        }
        double atenuacao = eficiencia * direcaoReal.escalar(normal);

        refletido.origem      = ponto.pos.mais(direcaoReal);
        refletido.direcao     = direcaoReal; 
        refletido.intensidade = raio.intensidade.vezes(atenuacao);

        raios.add(refletido);
    }
}
