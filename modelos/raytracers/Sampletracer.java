package modelos.raytracers;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import algebra.*;
import modelos.*;
import modelos.cameras.*;
//Decorator de tracer, adiciona capacidade de lançar vários raios por pixel
public class Sampletracer extends Raytracer{

    int               pixelCount;
    Raytracer         raytracer;

    public Sampletracer(Raytracer tracer, int rays){
        this.raytracer = tracer;
        this.pixelCount = rays;
    }

    public void render() {

        gerarRaios();

        raytracer.raios = raios;

        calcularRaios();
    }

    public Vetor buscarCor(Raio raio, List<Raio> pilha) {

        return raytracer.buscarCor(raio, pilha);
    }

    public void calcularRaios() {

        raytracer.calcularRaios();
    }

    public void gerarRaios() {

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
                for(int k = 0; k < pixelCount; k++ ){
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

        double intensity = 1.0/pixelCount;

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

    public Raytracer gerarBuffer(){
        super.gerarBuffer();
        raytracer.buffer = this.buffer;
        return this;
    }

    public Raytracer setCena(Cena cena){
        this.cena = cena;
        this.raytracer.setCena(cena);
        return this;
    }
  
    public Raytracer setCamera(Camera camera){
        this.camera = camera;
        this.raytracer.setCamera(camera);
        return this;
    }
}

