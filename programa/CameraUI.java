package programa;

import java.awt.*;
import javax.swing.*;

import modelos.*;
import modelos.cameras.Ortografica;
import modelos.cameras.Perspectiva;
import modelos.cameras.Projecao;
import programa.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CameraUI extends JPanel implements Observer{

  Controler controler;
  int estado = 2;

  CameraUI(Controler controler){
    
    this.controler = controler;

    setLayout(new GridLayout(1,4,0,0));
    setPreferredSize(new Dimension(500, 97));

    atualizar();
  }

  public void atualizar(){

    removeAll();

    if      (estado == 1) Default();
    else if (estado == 2) Modificar();

    revalidate();
    repaint();
  }
  
  void Default(){
    this.add(new ProximaCameraUI(controler));
  }

  void Modificar(){

    this.add(new CoordinatesUI(controler));
    this.add(new Tamanho(controler));
    this.add(new ProjecaoUI(controler));
    this.add(new ProximaCameraUI(controler));
  }
}

class ProximaCameraUI extends JPanel{

  ProximaCameraUI(Controler controler){

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(125,97));

    JLabel labelAlg = new JLabel("Mudar camera", SwingConstants.CENTER);
    labelAlg.setOpaque(true);
    labelAlg.setPreferredSize(new Dimension(125, 25));
    labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    labelAlg.setBackground(new Color(0xA9A9A9));
    labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    this.add(labelAlg, BorderLayout.PAGE_START);

    JButton button = new JButton(controler.getName());
    button.addActionListener(controler);
    button.setActionCommand("Próxima");
    button.setMargin(new Insets(0, 0, 0, 0));
    button.setPreferredSize(new Dimension(125, 72));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    button.setBackground(new Color(0xD3D3D3));
    this.add(button, BorderLayout.CENTER);
  }
}

class CoordinatesUI extends JPanel{

  Controler controler;

  CoordinatesUI(Controler controler){

    this.controler = controler;

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(125,97));

    JLabel labelAlg = new JLabel("Coordenadas", SwingConstants.CENTER);
    labelAlg.setOpaque(true);
    labelAlg.setPreferredSize(new Dimension(125, 25));
    labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    labelAlg.setBackground(new Color(0xA9A9A9));
    labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    this.add(labelAlg, BorderLayout.PAGE_START);

    this.add(new Grid2(controler), BorderLayout.CENTER);
  }
}

class Grid2 extends JPanel {

  Grid2(Controler controler) {

    setPreferredSize(new Dimension(125, 72));
    setLayout(new GridLayout(3, 3, 0, 0));

    this.add(new Row("Eye", controler), BorderLayout.NORTH);
    this.add(new Row("LookAt", controler), BorderLayout.CENTER);
    this.add(new Row("Up", controler), BorderLayout.SOUTH);
  }
}

class Tamanho extends JPanel{

  Tamanho(Controler controler){

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(125,97));

    JLabel labelAlg = new JLabel("Tamanho", SwingConstants.CENTER);
    labelAlg.setOpaque(true);
    labelAlg.setPreferredSize(new Dimension(125, 25));
    labelAlg.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    labelAlg.setBackground(new Color(0xA9A9A9));
    labelAlg.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    this.add(labelAlg, BorderLayout.PAGE_START);

    this.add(new GridTamanho(controler), BorderLayout.CENTER);
  }
}

class GridTamanho extends JPanel{
  
  GridTamanho(Controler controler) {

    setPreferredSize(new Dimension(125, 72));
    setLayout(new GridLayout(2, 2, 0, 0));

    JButton button = new JButton("X");
    button.setPreferredSize(new Dimension(83, 24));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    button.setBackground(new Color(0xD3D3D3));
    button.setBorder(null);
    this.add(button);
    button.setFocusable(false);

    Text textOrigem = new Text("...");
    textOrigem.setActionCommand(controler, "X");
    textOrigem.addKeyListener(controler);
    textOrigem.setPreferredSize(new Dimension(83, 24));
    textOrigem.setHorizontalAlignment(Text.CENTER);
    textOrigem.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    textOrigem.setBackground(new Color(0xD3D3D3));
    this.add(textOrigem);

    button = new JButton("Y");
    button.setPreferredSize(new Dimension(83, 24));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    button.setBackground(new Color(0xD3D3D3));
    button.setBorder(null);
    this.add(button);
    button.setFocusable(false);

    textOrigem = new Text("...");
    textOrigem.setActionCommand(controler, "Y");
    textOrigem.setPreferredSize(new Dimension(83, 24));
    textOrigem.addKeyListener(controler);
    textOrigem.setHorizontalAlignment(Text.CENTER);
    textOrigem.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    textOrigem.setBackground(new Color(0xD3D3D3));
    this.add(textOrigem);
  }
  
}

class ProjecaoUI extends JPanel implements ActionListener{

  CameraControler handleCamera;
  int estado;

  ProjecaoUI(Controler controler){

    setPreferredSize(new Dimension(125,97));
    setLayout(new BorderLayout());

    handleCamera = (CameraControler) controler;

    Projecao projecao = handleCamera.camera.projecao;

    if(projecao instanceof Perspectiva) estado = 1;

    if(projecao instanceof Ortografica) estado = 2;

    atualizar();
  }

  void atualizar(){

    removeAll();

    if      (estado == 1) Perspectiva();
    else if (estado == 2) Ortografica();

    revalidate();
    repaint();
  }

  void Perspectiva(){

    handleCamera.propriedades.put("Projeção", "Perspectiva");

    JButton button = new JButton("Perspectiva");
    button.setPreferredSize(new Dimension(83, 24));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    button.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    button.setBackground(new Color(0xA9A9A9));
    button.addActionListener(this);
    this.add(button, BorderLayout.NORTH);

    this.add(new PerspectivaUI(handleCamera), BorderLayout.CENTER);
  }

  void Ortografica(){

    handleCamera.propriedades.put("Projeção", "Ortográfica");

    JButton button = new JButton("Ortográfica");
    button.setPreferredSize(new Dimension(83, 24));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    button.setBorder(BorderFactory.createLineBorder(new Color(0x7f7f7f), 1));
    button.setBackground(new Color(0xA9A9A9));
    button.setBorder(null);
    button.addActionListener(this);
    this.add(button, BorderLayout.NORTH);
    
    this.add(new Row("Direção", handleCamera), BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e){

    estado = 1 + ((estado + 2) % 2);

    atualizar();
  }
}


class PerspectivaUI extends JPanel{

  PerspectivaUI(CameraControler handleCamera){
    
    setPreferredSize(new Dimension(125,97));
    setLayout(new GridLayout(1,2,0,0));
    
    JButton button = new JButton("Zoom");
    button.setPreferredSize(new Dimension(50, 24));
    button.setFont(new Font("ALGERIAN", Font.BOLD, 8));
    button.setBackground(new Color(0xD3D3D3));
    button.setBorder(null);
    this.add(button, BorderLayout.WEST);
    button.setFocusable(false);

    Text textOrigem = new Text("...");
    textOrigem.setActionCommand(handleCamera, "Zoom");
    textOrigem.addKeyListener(handleCamera);
    textOrigem.setBorder(null);
    textOrigem.setPreferredSize(new Dimension(75, 72));
    textOrigem.setHorizontalAlignment(Text.CENTER);
    textOrigem.setFont(new Font("ALGERIAN", Font.BOLD, 10));
    textOrigem.setBackground(new Color(0xD3D3D3));
    this.add(textOrigem, BorderLayout.CENTER);
    
  }
}