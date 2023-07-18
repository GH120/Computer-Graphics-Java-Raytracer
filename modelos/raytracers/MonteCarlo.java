package modelos.raytracers;
import java.util.ArrayList;
import java.util.LinkedList;

import algebra.*;
import modelos.*;
import modelos.fontes.*;
import modelos.reflexoes.Superficie;

//Com iluminação global => DEPRECATED
//Refazer quase tudo, na verdade, basta fazer o sampling das fontes extensas
//Basicamente, atirar vários raios e ver quais acertam a fonte extensa, transformar esses pontos em fontes pontuais direcionais e adicionar a cena
public class MonteCarlo {

    int depth = 3;
    int numberSamples = 10;
    int colisionCount = 4;
    LinkedList<Raio> samples = new LinkedList<Raio>();
    Cena cena;

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

    Vetor iluminar(Ponto ponto, Vetor viewer){

        //Leva em conta apenas as luzes pontuais
        Vetor luz = null; //super.iluminar(ponto, viewer);

        

        return luz;
    }

    Vetor iluminarExtenso(Ponto ponto, Raio raio){

        Vetor iluminacao = new Vetor(0,0,0);

        //Leva agora em conta as luzes extensas

        raio.profundidade = 0;

        for(int i=0; i < numberSamples; i++)
            refletir(ponto, raio, samples);

        for(int colisions = 0; colisions < colisionCount;){ 
            
            Raio refletido = samples.pop();

            if(refletido.profundidade > depth) continue;

            Ponto   colidiuObjeto  = cena.objetos.colisao(refletido.origem, refletido.direcao);
            Ponto   colidiuFonte   = null;
            Extensa extensa        = null;

            //TODO: Pegar o ponto mais proximo, ele só pega a primeira colisão
            for(Extensa fonte : cena.fontesExtensas){

                extensa = fonte;
                
                colidiuFonte = fonte.colisao(refletido);

                if(colidiuFonte != null) break;
            }

            boolean atingiuFonte = raio.pontoMaisProximo(colidiuFonte, colidiuObjeto);

            if(atingiuFonte){
                Vetor luz = extensa.luz(colidiuFonte, raio);

                iluminacao.add(luz);
                
                colisions++;
            }
            else refletir(colidiuObjeto, raio, samples);

            //Adiciona refletido novo se os samples tiverem se exaurido,
            //Mas não terem batido pelo menos 10 na fonte extensa
            if(samples.isEmpty()) refletir(ponto, raio, samples);
        }

        iluminacao.vezes(1/colisionCount);

        return null;
    }

    void refletir(Ponto ponto, Raio raio, LinkedList<Raio> pilha){
        

        Superficie superficie = ponto.objeto.superficie;

        if(superficie == null) return;

        superficie.refletir(ponto, raio, pilha);
    }
}
