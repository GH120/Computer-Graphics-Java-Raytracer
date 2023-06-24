package modelos.raytracers;
import java.util.LinkedList;

import algebra.*;
import modelos.*;
import modelos.reflexoes.Superficie;

public class MonteCarlo {

    int depth = 3;
    LinkedList<Raio> samples =null;

    //CalcularSamplesDaLuz => pega as luzes da cena e calcula samples delas
    //Com esses samples, vamos ter pontos onde temos certeza que a luz incide
    //Usamos esses pontos como possíveis direções de reflexão para o raytracing
    //Usar uma estrutura de dados de otimização? => octree
    //Depois adicionar peso para os samples mais pesados

    //Além disso, sobrescrever o iluminar, fontes não pontuais extensas
    //Quando o raio do pixel bater no objeto, calcular os samples da luz com um subalgoritmo
    //Esses samples vão ser raios calculados com reflexão e tudo
    //Mas eles só veem se colidiu com alguma das luzes extensas
    //Soma as contribuições desses raios multiplicando pelo coeficiente?
    //Raios com sombra contam para serem divididos, 
    //se 30 samples acertaram 15 em fontes de luzes dos quais 10 tem sombra, então multiplicar por 33% a intensidade da luz
    
    public Vetor BuscarCor(Raio raio, Cena cena){

        Ponto ponto = cena.objetos.colisao(raio.origem, raio.direcao);

        if (ponto == null)
        return cena.background;

        if( raio.interno) 
        ponto.normal = ponto.normal.vezes(-1);

    

        if(raio.profundidade < depth){

            Superficie superficie = ponto.objeto.superficie;

            //Mudar isso, refletir vai retornar um novo raio ao invés de diretamente adicionar a pilha
            //Além disso, vai usar os samples da luz para decidir a direção principal de reflexão
            if(superficie != null) superficie.refletir(ponto, raio, samples);

        }
        
        // Vetor luz = iluminar(ponto, raio.direcao);
        
        // return luz.mult(raio.intensidade);
        return null;
    }
}
