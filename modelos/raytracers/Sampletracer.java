package modelos.raytracers;

import java.util.LinkedList;
import java.util.Random;

import algebra.*;
import modelos.*;
import modelos.objetos.Esfera;
import modelos.objetos.Piso;
import modelos.reflexoes.Superficie;

public class Sampletracer extends Raytracer{
    LinkedList<Raio>  raios;
    int               depth;
    int         rayperpixel;

    public Sampletracer(int depth, int rays){
        this.depth = depth;
        this.rayperpixel = rays;
    }

    public void render() {

        gerarRaios();

        while(!raios.isEmpty()){

            Raio raio = raios.removeFirst();

            int l = raio.linha; int c = raio.coluna;

            buffer[c][l] = buffer[c][l].mais(buscaCor(raio));
        }
    }

    Vetor buscaCor(Raio raio) {

        Ponto ponto = cena.objetos.colisao(raio.origem, raio.direcao);

        if (ponto == null)
            return cena.background;

        if(raio.profundidade < depth){

            Superficie superficie = ponto.objeto.superficie;

            if(superficie != null) superficie.refletir(ponto, raio, raios);

        }

        Vetor luz = iluminar(ponto, raio.direcao);

        return luz.mult(raio.intensidade);
    }

    void gerarRaios() {

        raios = new LinkedList<>();

        double w = camera.wJanela;
        double h = camera.hJanela;

        double deltax = w / linhas;
        double deltay = h / colunas;

        Random random = new Random();

        for (int l = 0; l < linhas; l++) {
            double randomDeltay = random.nextDouble();
            for (int c = 0; c < colunas; c++) {
                double randomDeltax = random.nextDouble();
                for(int k = 0; k < rayperpixel; k++ ){
                    double y = h / 2 - deltay * l;
                    double x = w / 2 - deltax * c;

                    gerarRaio(x - randomDeltax, y - randomDeltay, c, l);
                }
            }
        }
    }

    void gerarRaio(double x, double y, int c, int l) {

        Raio raio = new Raio();

        raio.linha = l;

        raio.coluna = c;

        double intensity = 1.0/rayperpixel;

        // posicao x,y da camera transformada para as coordenadas de mundo
        raio.origem = camera.toWorld((new Posicao(x, y, 0)));

        // raio que incide sobre o pixel[c][l]
        raio.direcao = camera.projecao.getDirecao(raio.origem);

        // O coeficiente de reflexão de multiplas colisões
        raio.intensidade = new Vetor(intensity, intensity, intensity);

        raio.origem = raio.origem.tresD();
        raio.direcao = raio.direcao.tresD();

        raios.add(raio);

    }
}

