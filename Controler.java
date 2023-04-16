import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import algebra.*;
import modelos.Observable;

public abstract class Controler extends Observable implements ActionListener, KeyListener{

  HashMap<String,String> propriedades;
  Programa programa;

  Controler setPrograma(Programa programa){    
    this.programa = programa;
    setupPropriedades();
    return this;
  }

  void setupPropriedades(){
    propriedades = new HashMap<>();
  }

  void compilar(){
    
  }
  
  abstract String getName();
  
  public void actionPerformed(ActionEvent e){
    System.out.println("funciona");
  }

  public void keyPressed(KeyEvent e){
    
  }

  public void keyReleased(KeyEvent e){
    
    Text textField = (Text) e.getSource();
    String texto = textField.getText();
    String nome = textField.nome;

    propriedades.put(nome, texto);

    for (String key: propriedades.keySet()) {
      String value = propriedades.get(key);
      System.out.println(key + " " + value);
    }
  }

  public void keyTyped(KeyEvent e){
    
  }

  Vetor getVetor(String id){

    Double v1 = getValor(id+"1");
    Double v2 = getValor(id+"2");
    Double v3 = getValor(id+"3");

    if(v1 == null) return null;
    if(v2 == null) return null;
    if(v3 == null) return null;

    return new Vetor(v1,v2,v3);
  }

  Double getValor(String id){
    
    if(!propriedades.containsKey(id)) return null;

    String valor = propriedades.get(id);

    try{
      return Double.parseDouble(valor);
    }
    catch(Exception e){
      return null;
    }
  }
}