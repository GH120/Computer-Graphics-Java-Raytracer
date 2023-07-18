package modelos;

import modelos.objetos.*;

import java.util.*;

public class BoundingVolumeHierarchy {

    ArrayList<Esfera> esferas;

    //Extrai as boundingSpheres dos componentes do objeto composto e adiciona a pilha
    Esfera processar(Composto composto){

        for(Objeto objeto : composto.componentes){

            Esfera boundingEsfera = extrair(objeto);

            if(boundingEsfera != null) esferas.add(boundingEsfera);
        }

        return null;
    }
    
    Esfera extrair(Objeto objeto){
        
        //Todos os objetos compostos vão ser processados para extrair seus componentes simples
        if(objeto instanceof Composto) return processar((Composto) objeto);
 
        ObjetoSimples simples = (ObjetoSimples) objeto;

        return simples.BoundingBox();
    }
}
