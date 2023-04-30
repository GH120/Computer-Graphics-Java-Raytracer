import modelos.*;
import modelos.cenas.*;
import modelos.raytracers.*;
import programa.Programa;
import algebra.*;

class Main {

  static int CAMERA_PERSPECTIVA = 1;
  static int CAMERA_ORTOGRAFICA = 2;
  
  public static void main(String[] args) {
    
      rodarPrograma(
                    new NatalOtimizado(),                //Cena escolhida
                    new ConcurrentRaytracer(10),    //Raytracer escolhido
                    700, 700,               //Resolução
                    CAMERA_PERSPECTIVA                   //Câmera da cena escolhida
      );
    
  }

  static void rodarPrograma(Cena cena, Raytracer tracer ,int width, int height, int index){
        
    new Programa().setCena(
                            cena
                            .luzAmbiente(0.1,0.1,0.1)
                  )
                  .setTracer(tracer)
                  .getCamera(index)
                  .setResolution(width, height)
                  .setSize(width, height)
                  .renderizar();
  }
}


class Teste{

  static void testeOctree(){

    // Node node =new Octree(new Vetor(0,0,0), 500)
    // .processar(cena.objetos.componentes)
    // .filtrar();

    // // node.print();

    // Raio raio = new Raio();
    // raio.origem = new Vetor(0,0,0);
    // raio.direcao = new Vetor(0,-120,-150).unitario();

    // node.colisao(raio);

    // Conjunto conjunto = (Conjunto)cena.objetos.componentes.get(5);

    // Composto cubo = (Composto) conjunto.componentes.get(1);

    // Objeto triangulo = cubo.componentes.get(0);

    // triangulo.toWorld.printar();
  }

  static void testeGlossy(){

    // ArrayList<Raio> raios = new ArrayList<>();

    // Objeto plano = new Plano(0,150,0)
    // .setNormal(0,-1,0)
    // .setKd(0.4, 0.4, 0.4)
    // .setKa(0.4, 0.4, 0.4)
    // .setKe(0.4, 0.4, 0.4);

    // // Creating a ray with origin at (0,0,0) and direction (1,1,1)
    // Raio ray = new Raio();
    // ray.origem = new Vetor(0, 0, 0);
    // ray.direcao = new Vetor(1, 1, 1).unitario();
    // ray.intensidade = new Vetor(1,1,1);
    // ray.linha = 1;
    // ray.coluna = 1;

    // // Creating a point with position (2,2,2) and normal vector (0,1,0)
    // Ponto point = new Ponto(plano, new Vetor(2, 2, 2), new Vetor(0, 1, 0));
    // point.pos = new Vetor(2, 2, 2);
    // point.normal = new Vetor(0, 1, 0).unitario();

    // new Glossy(0.99,3,1).refletir(point, ray, raios);

    // for(Raio raio: raios) raio.printar();
  }
}