package modelos.fontes;

import java.util.Random;

import algebra.*;
import modelos.*;
import modelos.objetos.Conjunto;
import modelos.objetos.Triangulo;

public class Extensa extends Fonte {
    
    private Random    rand = new Random();
    public  Objeto    geometria;
    public  Triangulo T1, T2;
    public  double    samples;

    public Extensa(Vetor p1, Vetor p2, Vetor p3, Vetor p4){

        If = new Vetor(1,1,1);

        samples = 10;
        
        T1 = new Triangulo(p1.valores).setP2(p2.valores).setP3(p3.valores);

        T2 = new Triangulo(p1.valores).setP2(p3.valores).setP3(p4.valores);

        geometria = new Conjunto(T1, T2);

    }

    Extensa setSamples(int samples){
        this.samples = samples;
        return this;
    }

    public Fonte sample(){

        double t1,t2,t3,t4, sum;

        t1 = rand.nextDouble();
        t2 = rand.nextDouble();
        t3 = rand.nextDouble();
        t4 = rand.nextDouble();

        sum = t1+t2+t3+t4;

        t1 /= sum;
        t2 /= sum;
        t3 /= sum;
        t4 /= sum;

        Vetor posicao = T1.P1.vezes(t1)
                             .mais(T1.P2.vezes(t2))
                             .mais(T1.P3.vezes(t3))
                             .mais(T2.P3.vezes(t4));
        
        Fonte pontual = new Spot(posicao.valores)
                            .setAngulo(90)
                            .setDirecao(T1.n.vezes(-1).valores)
                            .setCor(If.vezes(1/samples).valores);

        return pontual;
    }

    //Photon mapping
    //Colisão

    public Ponto colisao(Raio raio){
        return geometria.colisao(raio.origem, raio.direcao);
    }
}
