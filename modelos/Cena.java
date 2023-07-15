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
  public ArrayList<Fonte>   fontes         = new ArrayList<>();
  public ArrayList<Camera>  cameras        = new ArrayList<>();
  public ArrayList<Extensa> fontesExtensas = new ArrayList<>();

  public void Objetos(Objeto ...conjunto){
    objetos = new Conjunto(conjunto);
  }

  public void Fontes(Fonte ...luzes){
    for(Fonte fonte : luzes){
      fontes.add(fonte);
    }
  }

  public void FontesExtensas(Extensa ...luzes){
    for(Extensa fonte : luzes){
      fontesExtensas.add(fonte);
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

  //Lógica basicamente a seguinte:
  //1.0 - Extrai todos os objetos simples 
  //1.1 - Pega a bounding sphere deles e adiciona a uma lista
  //2.0 - Pega as bounding spheres mais proximas entre si, formando grupos de 3 ou mais => Heap
  //2.1 - Aplica o algoritmo do ritter para 3 ou mais bounding spheres
  //2.2 - O algoritmo funciona pegando os 6 pontos extremos x,y,z positivos e negativos de cada esfera
  //2.3 - Vamos usar o miniball calculator em java já usado, assim, só precisamos colocar esses pontos
  Conjunto BoundingVolumeTree(){
    return new Conjunto();
  }
  
}