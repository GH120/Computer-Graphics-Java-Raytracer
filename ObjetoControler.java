import java.util.ArrayList;

import algebra.*;
import modelos.Objeto;
import modelos.objetos.*;

public class ObjetoControler extends Controler{
  
  Objeto selecionado;

  ArrayList<Objeto> arvore;

  Controler setPrograma(Programa programa){
    super.setPrograma(programa);
    selecionado = null;
    return this;
  }

  void setupPropriedades(){
    
    super.setupPropriedades();

    setupCor();

    if(selecionado instanceof Textura){
      Textura textura = (Textura) selecionado;
      propriedades.put("Textura", textura.path);
    }

    System.out.println(propriedades);
  }

  void setupCor(){

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

  void compilar(){

    Vetor Kd = getVetor("Kd");
    Vetor Ke = getVetor("Ke");
    Vetor Ka = getVetor("Ka");

    try{
      mudarCoeficientes(Kd,Ke,Ka);
    }
    catch(Exception e){
      System.out.println("Coeficientes mal tipados");
    }

    mudarTextura();

    
  }

  void mudarTextura(){

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

  void mudarCoeficientes(Vetor Kd, Vetor Ke, Vetor Ka) throws Exception{

    if(Kd == null || Ke == null || Ka == null) return;

    selecionado.setKd(Kd.valores);
    selecionado.setKa(Ka.valores);
    selecionado.setKe(Ke.valores);
  }

  void selecionar(Objeto objeto){
    selecionado = objeto;
    setupPropriedades();
    arvore = new ArrayList<>();
  }

  String getName(){

    if(selecionado == null) return "Nenhum";
    
    return selecionado.toString();
  }
}