package modelos.reflexoes;

import modelos.*;

import java.util.List;
import java.util.Random;

import algebra.*;

public class Difusa extends Superficie{

    Random rand = new Random();
    
    public void refletir(Ponto ponto, Raio raio, List<Raio> raios){

        double phi   = rand.nextDouble() * Math.PI;
        double theta = rand.nextDouble() * Math.PI;

        double x = Math.sin(phi) * Math.cos(theta);
        double y = Math.sin(phi) * Math.sin(theta);
        double z = Math.cos(theta);

        Vetor direcao = new Vetor(x,y,z);

        Vetor normal  = ponto.normal.vezes(-1);
        Vetor direita = normal.ortogonal();
        Vetor frente  = direita.ortogonal();

        Matriz toWorld = new Matriz(direita,normal, frente, new Vetor(0,0,0,0)).transposta();

        Vetor direcaoReal = toWorld.vezes(direcao.quatroD()).tresD().unitario();

        Raio refletido = raio.refletido();

        refletido.origem      = ponto.pos.mais(direcaoReal);
        refletido.direcao     = direcaoReal; 
        refletido.intensidade = ponto.getKd().mult(raio.intensidade);
        
        raios.add(refletido);
    }
}
