
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import modelos.*;
import modelos.objetos.Textura;

public class ObjetoUI extends JPanel{

  ObjetoUI(Controler handleObjeto){

    setLayout(new GridLayout(1,3));
    setPreferredSize(new Dimension(375,97));

    this.add(new PropertiesUI("Propriedades", handleObjeto));
    this.add(new TextureUI(handleObjeto));
    this.add(new ComponentsUI(handleObjeto));
  }
}

class ComponentsUI extends JPanel{

  ComponentsUI(Controler controler){

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(125,97));

    JLabel labelAlg = new JLabel("Componentes", SwingConstants.CENTER);
    labelAlg.setOpaque(true);
    labelAlg.setPreferredSize(new Dimension(125, 25));
    labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    labelAlg.setBackground(new Color(0xA9A9A9));
    labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    this.add(labelAlg, BorderLayout.PAGE_START);
    
    JPanel matriz = new JPanel(){{
      
        setPreferredSize(new Dimension(125, 72));
        setLayout(new GridLayout(4, 4, 0, 0));

        this.add(new Row("L1", controler,4));
        this.add(new Row("L2", controler,4));
        this.add(new Row("L3", controler,4));
        this.add(new Row("L4", controler,4));
    }};

    this.add(matriz, BorderLayout.CENTER);
  }
}

class TextureUI extends JPanel{

  Textura textura;

  TextureUI(Controler controler){

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(125,97));

    ObjetoControler handleObj = (ObjetoControler) controler;

    Objeto objeto = handleObj.selecionado;

    if(objeto instanceof Textura){
      textura = (Textura) objeto;
    }
    else{
      textura = null;
    }

    String path = (textura == null)? "Nenhuma" : textura.path;

    JLabel labelAlg = new JLabel("Textura", SwingConstants.CENTER);
    labelAlg.setOpaque(true);
    labelAlg.setPreferredSize(new Dimension(125, 25));
    labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    labelAlg.setBackground(new Color(0xA9A9A9));
    labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    this.add(labelAlg, BorderLayout.PAGE_START);

    //Tentar com cada clique verificar se a textura existe
    Text textOrigem = new Text(path);
    textOrigem.setActionCommand(controler, "Textura");
    textOrigem.setPreferredSize(new Dimension(125, 72));
    textOrigem.setHorizontalAlignment(Text.CENTER);
    textOrigem.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    textOrigem.setBackground(new Color(0xD3D3D3));
    textOrigem.addKeyListener(controler);
    this.add(textOrigem,BorderLayout.CENTER);
    
  }

}

class PropertiesUI extends JPanel {

  Controler controler;
  JButton button;

  PropertiesUI(String tipo, Controler controler) {

    // Observa o controler para mudar o botão
    this.controler = controler;
    // controler.acoplar(this);

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(125, 97));

    JLabel labelAlg = new JLabel(tipo, SwingConstants.CENTER);
    labelAlg.setOpaque(true);
    labelAlg.setPreferredSize(new Dimension(125, 25));
    labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    labelAlg.setBackground(new Color(0xA9A9A9));
    labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    this.add(labelAlg, BorderLayout.PAGE_START);

    this.add(new Grid(controler), BorderLayout.CENTER);
  }
}

class Grid extends JPanel {

  Grid(Controler controler) {

    setPreferredSize(new Dimension(125, 72));
    setLayout(new GridLayout(3, 3, 0, 0));

    this.add(new Row("Kd", controler), BorderLayout.NORTH);
    this.add(new Row("Ke", controler), BorderLayout.CENTER);
    this.add(new Row("Ka", controler), BorderLayout.SOUTH);
  }
}

class Row extends JPanel {

  Row(String name, Controler controler) {

    GridLayout layout = new GridLayout();
    layout.setHgap(0);
    layout.setVgap(0);

    setPreferredSize(new Dimension(125, 24));
    setLayout(layout);

    JButton button = new JButton(name);
    button.setPreferredSize(new Dimension(125, 24));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    button.setBackground(new Color(0xD3D3D3));
    button.setBorder(null);
    this.add(button);
    button.setFocusable(false);

    Text textOrigem = new Text("...");
    textOrigem.setPreferredSize(new Dimension(125, 24));
    textOrigem.setHorizontalAlignment(Text.CENTER);
    textOrigem.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    textOrigem.setBackground(new Color(0xD3D3D3));
    textOrigem.setActionCommand(controler, name + 1);
    textOrigem.addKeyListener(controler);
    this.add(textOrigem);
    // textOrigem.addKeyListener(controler);

    textOrigem = new Text("...");
    textOrigem.setHorizontalAlignment(Text.CENTER);
    textOrigem.setActionCommand(controler, name + 2);
    textOrigem.setPreferredSize(new Dimension(125, 24));
    textOrigem.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    textOrigem.addKeyListener(controler);
    textOrigem.setBackground(new Color(0xD3D3D3));
    this.add(textOrigem);

    textOrigem = new Text("...");
    textOrigem.setHorizontalAlignment(Text.CENTER);
    textOrigem.setActionCommand(controler, name + 3);
    textOrigem.setPreferredSize(new Dimension(125, 24));
    textOrigem.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    textOrigem.setBackground(new Color(0xD3D3D3));
    textOrigem.addKeyListener(controler);
    this.add(textOrigem);
  }

  Row(String name, Controler controler, int tamanho){

    GridLayout layout = new GridLayout();
    layout.setHgap(0);
    layout.setVgap(0);

    setPreferredSize(new Dimension(125, 24));
    setLayout(layout);

    JButton button = new JButton(name);
    button.setPreferredSize(new Dimension(125, 24));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    button.setBackground(new Color(0xD3D3D3));
    button.setBorder(null);
    this.add(button);
    button.setFocusable(false);
    
    for(int i=1;i<tamanho+1;i++){
      Text textOrigem = new Text("...");
      textOrigem.setHorizontalAlignment(Text.CENTER);
      textOrigem.setActionCommand(controler, name + i);
      textOrigem.setPreferredSize(new Dimension(125, 24));
      textOrigem.setFont(new Font("ALGERIAN", Font.BOLD, 8));
      textOrigem.setBackground(new Color(0xD3D3D3));
      textOrigem.addKeyListener(controler);
      this.add(textOrigem);
    }
  }
}