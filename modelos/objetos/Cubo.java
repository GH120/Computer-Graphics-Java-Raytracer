package modelos.objetos;

//Uma malha de triângulos, conjunto de objetos
public class Cubo extends Malha{

  public Cubo(double length){

    addVertice(0,0,0);
    addVertice(0,0,length);
    addVertice(length,0,length);
    addVertice(length,0,0);
    addVertice(0,length,0);
    addVertice(0,length,length);
    addVertice(length,length,length);
    addVertice(length,length,0);

    addAresta(0,1).addAresta(1,2).addAresta(2,3);
    addAresta(3,0).addAresta(4,5).addAresta(5,6);
    addAresta(6,7).addAresta(7,4).addAresta(0,4);
    addAresta(1,5).addAresta(2,6).addAresta(3,7);
    addAresta(2,7).addAresta(5,7).addAresta(5,2);
    addAresta(1,4).addAresta(1,3).addAresta(3,4);

    addFace(6,10,12);
    addFace(12,2,11);
    addFace(7,4,13);
    addFace(13,5,6);
    addFace(5,14,10);
    addFace(9,1,14);
    addFace(4,15,9);
    addFace(8,0,15);
    addFace(1,16,2);
    addFace(3,16,10);
    addFace(11,17,7);
    addFace(3,8,17);

    double l = length/2;

    fronteira = new Esfera(l,l,l).setRaio(l*1.75);

  }
}