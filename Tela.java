
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import modelos.raytracers.Raytracer;

public class Tela extends JPanel {

  Raytracer raytracer = null;

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (raytracer == null)
      return;

    for (int i = 0; i < linhas(); i++) {
      for (int j = 0; j < colunas(); j++) {
        drawPixel(g, i, j);
      }
    }
  }

  // Desenha cada quadrante da matriz do canvas
  void drawPixel(Graphics g, int i, int j) {
    int hsize = (int) Math.round((double) getHeight() / linhas());
    int wsize = (int) Math.round((double) getWidth() / colunas());
    g.setColor(getCor(i, j));
    g.fillRect(i * wsize, j * hsize, wsize, hsize);
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