package modelos;
import java.util.*;

import java.awt.*;
import algebra.*;
import modelos.cameras.*;
import modelos.fontes.*;
import modelos.objetos.*;

//Guarda as cameras, fontes e objetos
//Usada como base para instanciação de cenas concretas,
//Dentre elas, Natal, Galpão e Cenário principal
public abstract class Cena{

  public Vetor L_ambiente;
  public Vetor background = Vetor.pixel(Color.GRAY.getRGB());  
  
  public Conjunto           objetos;
  public ArrayList<Fonte>   fontes  = new ArrayList<>();
  public ArrayList<Camera>  cameras = new ArrayList<>();

  public void Objetos(Objeto ...conjunto){
    objetos = new Conjunto(conjunto);
  }

  public void Fontes(Fonte ...luzes){
    for(Fonte fonte : luzes){
      fontes.add(fonte);
    }
  }

  void Cameras(Camera ...cams){
    for(Camera camera : cams){
      cameras.add(camera);
    }
  }

  public Cena luzAmbiente(double ...rgb){
    L_ambiente = new Vetor(rgb);
    return this;
  }
  
}