package programa;
import java.util.ArrayList;

import algebra.*;
import modelos.*;
import modelos.objetos.*;

public class ObjetoControler extends Controler{
  
  Objeto selecionado;

  ArrayList<Objeto> arvore;

  public Controler setPrograma(Programa programa){
    super.setPrograma(programa);
    selecionado = null;
    return this;
  }

  public void setupPropriedades(){
    
    super.setupPropriedades();

    setupCoordenadas();

    if(selecionado instanceof Textura){
      Textura textura = (Textura) selecionado;
      propriedades.put("Textura", textura.path);
    }
    else setupCor();

    System.out.println(propriedades);
  }

  public void setupCor(){

    if(selecionado == null) return;
    if(selecionado.getKd(null) == null) return;

    Vetor Kd = selecionado.getKd(null);

    propriedades.put("Kd1", "" + Kd.get(0));
    propriedades.put("Kd2", "" + Kd.get(1));
    propriedades.put("Kd3", "" + Kd.get(2));

    Vetor Ka = selecionado.getKa(null);

    propriedades.put("Ka1", "" + Ka.get(0));
    propriedades.put("Ka2", "" + Ka.get(1));
    propriedades.put("Ka3", "" + Ka.get(2));

    Vetor Ke = selecionado.getKe(null);

    propriedades.put("Ke1", "" + Ke.get(0));
    propriedades.put("Ke2", "" + Ke.get(1));
    propriedades.put("Ke3", "" + Ke.get(2));
  }

  public void setupCoordenadas(){

    if(selecionado == null) return;
    
    Vetor L1 = selecionado.toWorld.getLinha(0);

    propriedades.put("L11", "" + L1.get(0));
    propriedades.put("L12", "" + L1.get(1));
    propriedades.put("L13", "" + L1.get(2));
    propriedades.put("L14", "" + L1.get(3));

    Vetor L2 = selecionado.toWorld.getLinha(1);

    propriedades.put("L21", "" + L2.get(0));
    propriedades.put("L22", "" + L2.get(1));
    propriedades.put("L23", "" + L2.get(2));
    propriedades.put("L24", "" + L2.get(3));

    Vetor L3 = selecionado.toWorld.getLinha(2);

    propriedades.put("L31", "" + L3.get(0));
    propriedades.put("L32", "" + L3.get(1));
    propriedades.put("L33", "" + L3.get(2));
    propriedades.put("L34", "" + L3.get(3));

    Vetor L4 = selecionado.toWorld.getLinha(3);

    propriedades.put("L41", "" + L4.get(0));
    propriedades.put("L42", "" + L4.get(1));
    propriedades.put("L43", "" + L4.get(2));
    propriedades.put("L44", "" + L4.get(3));
  }

  public void compilar(){

    Vetor Kd = getVetor("Kd");
    Vetor Ke = getVetor("Ke");
    Vetor Ka = getVetor("Ka");

    Vetor L1 = getVetor("L1", 4);
    Vetor L2 = getVetor("L2", 4);
    Vetor L3 = getVetor("L3", 4);
    Vetor L4 = getVetor("L4", 4);

    try{
      mudarCoeficientes(Kd,Ke,Ka);
      // mudarCoordenadas(L1,L2,L3,L4); => calcular inversa da matriz
    }
    catch(Exception e){
      System.out.println("Coeficientes mal tipados");
    }

    mudarTextura();

    
  }

  public void mudarTextura(){

    if(!(propriedades.containsKey("Textura"))) return;

    if(!(selecionado instanceof Textura)){
      System.out.println("Não suporta textura");
    }

    ((Textura) selecionado).setTextura(propriedades.get("Textura"));
  }

  void selectComponente(int index){
    Composto objeto = (Composto) selecionado;

    arvore.add(objeto);

    selecionado = objeto.getComponentes().get(index);
  }

  public void mudarCoeficientes(Vetor Kd, Vetor Ke, Vetor Ka) throws Exception{

    if(Kd == null || Ke == null || Ka == null) return;

    selecionado.setKd(Kd.valores);
    selecionado.setKa(Ka.valores);
    selecionado.setKe(Ke.valores);
  }

  public void selecionar(Objeto objeto){
    selecionado = objeto;
    setupPropriedades();
    arvore = new ArrayList<>();
  }

  public String getName(){

    if(selecionado == null) return "Nenhum";
    
    return selecionado.toString();
  }
}