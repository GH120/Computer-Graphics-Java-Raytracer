package programa;


import java.awt.*;
import javax.swing.*;

import modelos.*;
import programa.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Barra extends JPanel implements Observer, ActionListener{

  
  int estado = 1;
  Programa programa;

  Controler[] controlers;

  Barra(Programa programa){

    this.programa = programa;

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(500,97));

    controlers = programa.controlers;

    for(Controler controler : controlers){
      controler.addObserver(this);
      controler.setPrograma(programa);
    }

    atualizar();
  }

  public void atualizar(){

    System.out.println("funciona");

    removeAll();

    if     (estado == 1) Main();
    else if(estado == 2) HandleObjeto();
    else if(estado == 3) HandleFonte();
    else if(estado == 4) HandleCamera();
    else if(estado == 5) Modificar();

    if(estado != 1){
      this.add(
        new Container("Menu", "Voltar", this),
        BorderLayout.EAST
      );
    }

    revalidate();
    repaint();
  }

  void Main(){

    this.add(new Container("Renderizar", "Executar mudanças", this), BorderLayout.WEST);
    this.add(new Container("Modificar", "Alterar Câmera, fonte, objetos...", this), BorderLayout.CENTER);
    this.add(new Container("Salvar", "Salvar nova configuração", this), BorderLayout.EAST);
  }

  void Modificar(){
    
    this.add(new Modificar(this, controlers), BorderLayout.CENTER);
    
  }

  void HandleObjeto(){

    Controler handleObjeto = programa.controlers[0];

    this.add(new ObjetoUI(handleObjeto), BorderLayout.CENTER);
  }

  void HandleCamera(){

    Controler handleCamera = programa.controlers[2];

    this.add(new CameraUI(handleCamera), BorderLayout.CENTER);
  }

  void HandleFonte(){

    Controler handleFonte = programa.controlers[1];

    this.add(new FonteUI(handleFonte), BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e){

    switch(e.getActionCommand()){
      case "Menu"       :  estado = 1; break;
      case "Objeto"     :  estado = 2; break;
      case "Fonte"      :  estado = 3; break;
      case "Câmera"     :  estado = 4; break;
      case "Modificar"  :  estado = 5; break;
      case "Salvar"     :  compilar(); break;
      case "Renderizar" :  programa
                          .atualizar();break;
    }

    atualizar();
  }

  void compilar(){
    for(Controler controler : controlers){
      controler.compilar();
    }
  }
}


class Container extends JPanel{

  JButton button;

  Container(String tipo, String name, ActionListener observer){

    //controler.acoplar(this);

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(166,97));
    
    JLabel labelAlg = new JLabel(tipo ,SwingConstants.CENTER);
    labelAlg.setOpaque(true);
    labelAlg.setPreferredSize(new Dimension(166,25));
    labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    labelAlg.setBackground(new Color(0xA9A9A9));
    labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    this.add(labelAlg,BorderLayout.PAGE_START);

    button = new JButton(name);
    button.setPreferredSize(new Dimension(166,72));
    button.setActionCommand(tipo);
    button.setMargin(new Insets(0, 0, 0, 0));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    button.setBackground(new Color(0xD3D3D3));
    this.add(button,BorderLayout.CENTER);
    button.addActionListener(observer);
  }
}

class Modificar extends JPanel{

  String[] names =         {
                            "Objeto",
                            "Fonte", 
                            "Câmera"
                           };

  Modificar(ActionListener listener, Controler[] controlers){

    setLayout(new GridLayout(1,3,0,0));
    setPreferredSize(new Dimension(166,97));

    for(int i =0; i< 3; i++){
      
      Controler controler = controlers[i];
      String nome = names[i];
      
      this.add( new Container(nome, controler.getName(), listener));
    }
  }
}

class Text extends JTextField{

  String nome = "nome";

  Text(String conteudo){
    super(conteudo);
  }

  public void setActionCommand(Controler controler, String nome){
    this.nome = nome;
    
    if(controler.propriedades.containsKey(nome))
      setText(controler.propriedades.get(nome));
  }
}