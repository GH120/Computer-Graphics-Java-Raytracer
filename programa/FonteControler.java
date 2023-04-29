package programa;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import algebra.*;
import modelos.fontes.*;

public class FonteControler extends Controler{

  Fonte fonte;
  //Guarda a cor quando apaga a luz, que é apenas colocar intensidade 0
  HashMap<Integer, Vetor> cores;


  public Controler setPrograma(Programa programa){
    super.setPrograma(programa);
    fonte = programa.cena.fontes.get(0);
    cores = new HashMap<>();
    setupPropriedades();
    return this;
  }

  void setupPropriedades(){
    
    super.setupPropriedades();

    if(fonte == null) return;

    Vetor posicao = fonte.posicao;

    propriedades.put("Posição1", "" + posicao.valores[0]);
    propriedades.put("Posição2", "" + posicao.valores[1]);
    propriedades.put("Posição3", "" + posicao.valores[2]);

    Vetor Cor = fonte.If;

    propriedades.put("Cor1", "" + Cor.valores[0]);
    propriedades.put("Cor2", "" + Cor.valores[1]);
    propriedades.put("Cor3", "" + Cor.valores[2]);

    if(!(fonte instanceof Direcional)) return;

    Vetor direcao = ((Direcional)fonte).direcao;

    propriedades.put("Direção1", "" + direcao.valores[0]);
    propriedades.put("Direção2", "" + direcao.valores[1]);
    propriedades.put("Direção3", "" + direcao.valores[2]);
  }

  public void compilar(){
    
    if(On()) setCor(getVetor("Cor"));
    mover(getVetor("Posição"));
    setDirecao(getVetor("Direção"));
    setAbertura(getValor("Ângulo"));
  }

  public void mover(Vetor posicao){

    if(posicao == null){
      System.out.println("Posição da luz mal inserida");
      return;
    }
    
    fonte.posicao = posicao;
  }

  public void setDirecao(Vetor direcao){

    if(direcao == null) return;

    if(!(fonte instanceof Direcional)) return;

    Direcional direcional = (Direcional) fonte;

    direcional.setDirecao(direcao.valores);
  }

  public void setAbertura(Double angulo){

    if (angulo == null) return;

    if(!(fonte instanceof Spot)) return;

    Spot spot = (Spot) fonte;

    spot.setAngulo(angulo);
  }

  public void setCor(Vetor cor){

    if(cor == null){
      System.out.println("Cor da luz mal inserida");
      return;
    }
    
    fonte.If = cor;
  }
  
  public void apagar(){
    
    if(On()){
      cores.put(getIndice(), new Vetor(fonte.If.valores));
      fonte.If = new Vetor(0,0,0);
    }
    else{
      fonte.If = cores.get(getIndice());
      cores.put(getIndice(), null);
    }

    System.out.println(On());
    fonte.If.printar();

  }

  public boolean On(){
    if(!cores.containsKey(getIndice())) return true;
    if(cores.get(getIndice()) == null) return true;
    return false;
  }

  public String getName(){

    int i = 0;
    for(Fonte luz : programa.cena.fontes){
      i++;
      if(luz == fonte){
        return ("Fonte " + i);
      }
    }

    return "Não selecionada";
  }

  public void actionPerformed(ActionEvent e){
    
    switch(e.getActionCommand()){

      case "Próxima"     : mudarFonte(); break;
      case "Interruptor" : apagar();     break;
    }

    notificar();
  }

  public int getIndice(){

    int i = 0;
    for(Fonte luz : programa.cena.fontes){
      if(luz == fonte){
        return i;
      }
      i++;
    }

    return -1;
  }

  public void mudarFonte(){
    
    int quantidade = programa.cena.fontes.size();

    int indice = (getIndice() + 1) % quantidade;

    if(quantidade == 0) 
      programa.cena.Fontes(new Fonte(0,0,0).setCor(1,1,1));

    fonte = programa.cena.fontes.get(indice);

    notificar();
  }
}