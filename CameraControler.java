import java.awt.event.ActionEvent;

import algebra.*;
import modelos.Camera;
import modelos.Ortografica;
import modelos.Perspectiva;
import modelos.Projecao;
import modelos.cameras.*;

public class CameraControler extends Controler{

  Camera camera;

  Controler setPrograma(Programa programa){
    super.setPrograma(programa);
    camera = programa.camera;
    setupPropriedades();
    return this;
  }

  void setupPropriedades(){
    
    super.setupPropriedades();

    if(camera == null) return;

    propriedades.put("X", "" +camera.wJanela);
    propriedades.put("Y", "" +camera.hJanela);

    if(camera.projecao instanceof Perspectiva){

      Perspectiva perspectiva = (Perspectiva) camera.projecao;

      propriedades.put("Zoom", "" + perspectiva.getZoom());
    }
    else{
      Ortografica ortografica = (Ortografica) camera.projecao;

      Vetor dir = ortografica.incidencia;

      propriedades.put("Direção1", "" + dir.get(0));
      propriedades.put("Direção2", "" + dir.get(1));
      propriedades.put("Direção3", "" + dir.get(2));
    }
  }

  void setupCoordenadas(){

    if(camera == null) return;
    if(camera.Eye == null) return;

    Vetor Eye = camera.Eye;

    propriedades.put("Eye1", "" + Eye.valores[0]);
    propriedades.put("Eye2", "" + Eye.valores[1]);
    propriedades.put("Eye3", "" + Eye.valores[2]);

    Vetor LookAt = camera.LookAt;

    propriedades.put("LookAt1", "" + LookAt.valores[0]);
    propriedades.put("LookAt2", "" + LookAt.valores[1]);
    propriedades.put("LookAt3", "" + LookAt.valores[2]);

    Vetor Up = camera.Up;

    propriedades.put("Up1", "" + Up.valores[0]);
    propriedades.put("Up2", "" + Up.valores[1]);
    propriedades.put("Up3", "" + Up.valores[2]);
  }

  void compilar(){
    
    mudarTamanho();
    mudarProjecao();
    setZoom();
    setDirecao();
    mudarCoordenadas();
  }

  void mudarProjecao(){

    String tipo = propriedades.get("Projeção");

    if(tipo == "Perspectiva") camera.setProjecao(new Perspectiva());

    if(tipo == "Ortográfica") camera.setProjecao(new Ortografica());

    // camera.projecao.setCoordenadas(camera.toWorld);
  }

  void mudarTamanho(){

    Double length = getValor("X");
    Double height = getValor("Y");

    if(length == null || height == null) return;
    
    camera.wJanela = length;
    camera.hJanela = height;
    
  }

  void mudarCoordenadas(){

    Vetor Eye    =   getVetor("Eye");
    Vetor LookAt =   getVetor("LookAt");
    Vetor Up     =   getVetor("Up");

    if(Eye    == null)  return;
    if(LookAt == null)  return;
    if(Up     == null)  return;

    Projecao projecao = camera.projecao;
    
    camera.setCoordenadas(Eye,LookAt,Up);

    camera.setProjecao(projecao);
  }

  void setZoom(){

    Double zoom = getValor("Zoom");

    if(zoom == null) return;

    if(!(camera.projecao instanceof Perspectiva)) return;

    Perspectiva projecao = (Perspectiva) camera.projecao;

    projecao.setZoom(zoom);
  }

  void setDirecao(){

    Vetor direcao = getVetor("Direção");

    if(direcao == null) return;

    if(!(camera.projecao instanceof Ortografica)) return;

    Ortografica projecao = (Ortografica) camera.projecao;

    projecao.incidencia = direcao;
  }

  void mudarCamera(){

    int indice = getIndice();
    int quantidade = 0;

    if(camera != null) 
      quantidade = programa.cena.cameras.size();
    
    if(indice == -1 && quantidade > 0){
      programa.camera = programa.cena.cameras.get(0);
    }
    else if(indice + 1 == quantidade){
      programa.defaultCamera();
    }
    else{
      programa.camera = programa.cena.cameras.get(indice+1);
    }

    camera = programa.camera;

    setupPropriedades();

    notificar();
  }

  int getIndice(){

    int i = 0;
    for(Camera cam : programa.cena.cameras){
      if(cam == camera){
        return i;
      }
      i++;
    }

    return -1;
  }

  String getName(){

    int indice = getIndice() + 1;

    if(indice > 0) return ("Câmera " + indice + " da cena");

    return "Câmera padrão";
  }

  public void actionPerformed(ActionEvent e){
    mudarCamera();
  }
}