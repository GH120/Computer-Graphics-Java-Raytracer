package programa;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import java.awt.Graphics2D;


import javax.swing.JPanel;

import modelos.raytracers.*;

public class Tela extends JPanel {

  public Raytracer raytracer = null;

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  
    if (raytracer == null)
      return;
  
    int width = getWidth();
    int height = getHeight();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  
    for (int i = 0; i < linhas(); i++) {
      for (int j = 0; j < colunas(); j++) {
        drawPixel(image, i, j);
      }
    }
  
    g.drawImage(image, 0, 0, null);
  }
  
  void drawPixel(BufferedImage image, int i, int j) {
    int hsize = (int) Math.round((double) getHeight() / linhas());
    int wsize = (int) Math.round((double) getWidth() / colunas());
    Graphics2D g2d = image.createGraphics();
    g2d.setColor(getCor(i, j));
    g2d.fillRect(i * wsize, j * hsize, wsize, hsize);
    g2d.dispose();
  }

  double linhas() {
    return (double) raytracer.linhas;
  }

  double colunas() {
    return (double) raytracer.colunas;
  }

  Color getCor(int i, int j) {
    return raytracer.buffer[i][j].getCor();
  }

  Tela setRaytracer(Raytracer raytracer) {
    this.raytracer = raytracer;
    return this;
  }
}