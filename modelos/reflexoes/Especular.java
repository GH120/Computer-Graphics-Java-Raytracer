package modelos.reflexoes;
import java.util.List;

import modelos.*;

public class Especular extends Reflexao{

    public void refletir(Ponto ponto, Raio raio, List<Raio> raios){

        Raio refletido = reflexao(ponto, raio);

        // refletido.intensidade = refletido.intensidade.vezes(eficiencia);

        raios.add(refletido);
    }

    Raio reflexao(Ponto ponto, Raio raio){

        Raio refletido = raio.refletido();

        if(raio.interno){ 
            ponto = new Ponto(ponto.objeto, 
                              ponto.pos,
                              ponto.normal.vezes(-1));
        }
        
    
        //Direção muda para o raio refletido no ponto
        refletido.direcao =  raio.direcao
                                  .menos(
                                      ponto.normal
                                      .vezes(
                                        2*raio.direcao
                                        .escalar(ponto.normal)
                                      )
                                  )
                                  .unitario();
    
        //O raio refletido tem origem no ponto de colisão
        refletido.origem = ponto.pos.mais(refletido.direcao);
    
        //Intensidade diminui pelo fator de reflexão especular
        refletido.intensidade = raio.intensidade.mult(ponto.getKe());
    
        return refletido;
      }
}
