package algebra;

import java.util.*;
import java.awt.*;

public class Vetor{

  public double[] valores;

  public Vetor(int tamanho){
    valores = new double[tamanho];
    for(int i=0; i<tamanho;i++) 
      valores[i] = 0;
  }

  public Vetor(double... valor){
    valores = new double[valor.length];
    for(int i=0; i<valor.length;i++) 
      valores[i] = valor[i];
  }

  public double escalar(Vetor vetor2){
    
    double resultado = 0f;
    for(int i=0; i< vetor2.valores.length;i++)
      resultado += valores[i] * vetor2.valores[i];
    return resultado;
  }

  public Vetor menos(Vetor vetor2){
    
    Vetor novo = new Vetor(valores);

    for(int i=0;i<valores.length;i++)
      novo.valores[i] -= vetor2.valores[i];

    return novo;
  }

  public Vetor mais(Vetor vetor2){

    Vetor novo = new Vetor(valores);

    for(int i=0;i<valores.length;i++)
      novo.valores[i] += vetor2.valores[i];

    return novo;
  }

  public void add(Vetor vetor2){

    for(int i=0; i < valores.length; i++){
      valores[i] += vetor2.valores[i];
    }
  }

  public Vetor vezes(double c){
    Vetor novo = new Vetor(valores);
    for(int i=0;i<valores.length;i++){
      novo.valores[i] = valores[i]*c;
    }
    return novo;
  }

  public double modulo(){
    double resultado = 0;
    for(double v: valores){
      resultado += v*v;
    }

    return (double)Math.sqrt(resultado);
  }

  //Fast square root -> muito imprecisa infelizmente
  public Vetor unitario(){

    // double x = valores[0];
    // double y = valores[1];
    // double z = valores[2];

    // double lenApprox = Double.longBitsToDouble(0x5fe6ec85e7de30daL - (Double.doubleToLongBits(x*x + y*y + z*z) >> 1));

    // x *= lenApprox;
    // y *= lenApprox;
    // z *= lenApprox;

    return vezes(1/modulo());
  }

  //transforma vetor para 4 dimensões
  public Vetor quatroD(){
    
    return new Vetor(
                      get(0),
                      get(1),
                      get(2),
                      0
                    );
  }

  public Vetor tresD(){
    return new Vetor(get(0),get(1),get(2));
  }

  // operador @
  public Vetor mult(Vetor vetor2){

    Vetor novo = new Vetor(valores);

    for(int i=0;i<valores.length;i++){
      novo.valores[i] *= vetor2.valores[i];
    }

    return novo;
  }

  public Vetor vetorial(Vetor vetor2){
    double[] A = valores;
    double[] B = vetor2.valores;
     
    double x = A[1]*B[2] - A[2]*B[1];
    double y = A[2]*B[0] - A[0]*B[2];
    double z = A[0]*B[1] - A[1]*B[0];

    return new Vetor(x,y,z);
  }

  public Vetor ortogonal() {

    Vetor a = new Vetor(1, 0, 0);

    double sentido = a.escalar(this.unitario());

    if (sentido == 1 || sentido == -1) {
        a = new Vetor(0, 1, 0);
    }

    Vetor n = vetorial(a).unitario();

    Vetor b = n.vetorial(this);
    
    return b;
  }


  //Basicamente um vezes entre vetores
  public Matriz produtoExterno(Vetor vetor){

    Vetor[] vetores = new Vetor[vetor.size()];

    int i = 0;
    for(double valor : valores){
      vetores[i++] = vetor.vezes(valor);
    }

    return new Matriz(vetores);
  }
  
  public Color getCor(){
    int r = (int)Math.min(255*valores[0], 255);
    int g = (int)Math.min(255*valores[1], 255);
    int b = (int)Math.min(255*valores[2], 255);

    Color cor = Color.BLACK;
    try{
      cor =  new Color(r,g,b);
    }
    catch(Exception e){
      //System.out.println(r+ " " + g + " "+b);
    }

    return cor;
  }

  public static Vetor pixel(int rgb){
    double blue     =  rgb & 0xff;
    double green   = (rgb & 0xff00) >> 8;
    double red    = (rgb & 0xff0000) >> 16;

    return new Vetor(red/255.0, green/255.0, blue/255.0);
  }

  public boolean igual(Vetor vetor2){
    int i=0;
    for(double valor : vetor2.valores){
      if(valores[i++] != valor) return false;
    }
    return true;
  }

  public void printar(){
    System.out.println(Arrays.toString(valores));
  }

  public int size(){
    return valores.length;
  }

  public double get(int i){
    return valores[i];
  }

  public double[] eixos(){
    return Arrays.copyOfRange(valores,0,3);
  }
}