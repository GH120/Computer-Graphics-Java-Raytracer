package modelos;

import modelos.objetos.*;

import java.util.*;

public class BoundingVolumeHierarchy {

    public ArrayList<Conjunto> esferas = new ArrayList<>();

    //Lógica geral: subdividir as esferas em grupos de 3-5 objetos
    //Para isso, agrupamos as esferas próximas usando uma estrutura de dados
    //Podemos então criar recursivamente uma árvore usando o algorítmo de ritter para encontrar a menor esfera

    //Extrai as boundingSpheres dos componentes do objeto composto e adiciona a pilha
    public Esfera processar(Composto composto){

        for(Objeto objeto : composto.componentes){

            Esfera boundingEsfera = extrair(objeto);

            if(boundingEsfera == null) continue;

            boundingEsfera.toWorld = objeto.toWorld;
            boundingEsfera.inversa = objeto.inversa;

            //Objeto contido pela fronteira da boundingSphere
            Conjunto conjunto = new Conjunto(objeto).setFronteira(boundingEsfera);

            esferas.add(conjunto);
        }

        return null;
    }
    
    Esfera extrair(Objeto objeto){
        
        //Todos os objetos compostos vão ser processados para extrair seus componentes simples
        if(objeto instanceof Composto) return processar((Composto) objeto);

        if(objeto instanceof Textura) objeto = ((Textura) objeto).objeto;
 
        ObjetoSimples simples = (ObjetoSimples) objeto;

        return simples.BoundingBox();
    }
}
