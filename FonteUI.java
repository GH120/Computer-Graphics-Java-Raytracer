
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import modelos.*;
import modelos.fontes.*;

public class FonteUI extends JPanel implements Observer{

  FonteControler controler;

  FonteUI(Controler handleFontes){

    controler = (FonteControler) handleFontes;

    setPreferredSize(new Dimension(500,97));
    setLayout(new GridLayout(1,4,0,0));

    controler.addObserver(this);

    atualizar();
  }

  public void atualizar(){

    removeAll();

    this.add(new SetupUI(controler));
    
    if(controler.fonte instanceof Direcional)
      this.add(new IncidenciaUI(controler));
    
    this.add(new InterruptorUI(controler));
    this.add(new ProximaFonteUI(controler));

    revalidate();
    repaint();
  }
}

class SetupUI extends JPanel{

  SetupUI(Controler controler) {

    setPreferredSize(new Dimension(125,97));
    setLayout(new GridLayout(3,1,0,0));

    JLabel labelAlg = new JLabel("Setup", SwingConstants.CENTER);
    labelAlg.setOpaque(true);
    labelAlg.setPreferredSize(new Dimension(125, 25));
    labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    labelAlg.setBackground(new Color(0xA9A9A9));
    labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    this.add(labelAlg);

    this.add(new Row("Posição", controler));

    this.add(new Row("Cor", controler));
  }
}

class InterruptorUI extends JPanel implements Observer{

  FonteControler handleFontes;

  InterruptorUI(Controler controler){

      handleFontes = (FonteControler) controler;
      handleFontes.addObserver(this);

      setPreferredSize(new Dimension(125,97));
      setLayout(new BorderLayout());

      atualizar();
  }

  public void atualizar(){
    
      JLabel labelAlg = new JLabel("Interruptor", SwingConstants.CENTER);
      labelAlg.setOpaque(true);
      labelAlg.setPreferredSize(new Dimension(125, 25));
      labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
      labelAlg.setBackground(new Color(0xA9A9A9));
      labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
      this.add(labelAlg, BorderLayout.PAGE_START);

      String text = (handleFontes.On())? "On" : "Off";

      JButton button = new JButton(text);
      button.setActionCommand("Interruptor");
      button.addActionListener(handleFontes);
      button.setPreferredSize(new Dimension(125, 24));
      button.setFont(new Font("ALGERIAN", Font.BOLD, 8));
      button.setBackground(new Color(0xD3D3D3));
      this.add(button, BorderLayout.CENTER);
    
  }
}

class IncidenciaUI extends JPanel{

    IncidenciaUI(FonteControler controler) {

      setPreferredSize(new Dimension(125,97));
      setLayout(new GridLayout(3,1,0,0));
  
      JLabel labelAlg = new JLabel("Incidência", SwingConstants.CENTER);
      labelAlg.setOpaque(true);
      labelAlg.setPreferredSize(new Dimension(125, 25));
      labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
      labelAlg.setBackground(new Color(0xA9A9A9));
      labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
      this.add(labelAlg, BorderLayout.PAGE_START);

      if(controler.fonte instanceof Spot)
        this.add(new AnguloUI(controler), BorderLayout.CENTER);
  
      this.add(new Row("Direção", controler),BorderLayout.SOUTH);
    }
}

class AnguloUI extends JPanel{

  AnguloUI(Controler controler){

    setPreferredSize(new Dimension(125,97));
    setLayout(new GridLayout(1,4,0,0));

    JButton button = new JButton("Ângulo");
    button.setPreferredSize(new Dimension(85, 24));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    button.setBackground(new Color(0xD3D3D3));
    button.setBorder(null);
    this.add(button);
    button.setFocusable(false);

    Text textOrigem = new Text("...");
    textOrigem.setPreferredSize(new Dimension(100, 24));
    textOrigem.setActionCommand(controler, "Ângulo");
    textOrigem.addKeyListener(controler);
    textOrigem.setHorizontalAlignment(Text.CENTER);
    textOrigem.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    textOrigem.setBackground(new Color(0xD3D3D3));
    this.add(textOrigem);
  }
}

class ProximaFonteUI extends JPanel{

  ProximaFonteUI(Controler controler){

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(125,97));

    JLabel labelAlg = new JLabel("Mudar Fonte", SwingConstants.CENTER);
    labelAlg.setOpaque(true);
    labelAlg.setPreferredSize(new Dimension(125, 25));
    labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    labelAlg.setBackground(new Color(0xA9A9A9));
    labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    this.add(labelAlg, BorderLayout.PAGE_START);

    JButton button = new JButton(controler.getName());
    button.addActionListener(controler);
    button.setMargin(new Insets(0, 0, 0, 0));
    button.setActionCommand("Próxima");
    button.setPreferredSize(new Dimension(125, 72));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    button.setBackground(new Color(0xD3D3D3));
    this.add(button, BorderLayout.CENTER);
  }
}