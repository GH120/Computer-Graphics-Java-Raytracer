package modelos.reflexoes;

import modelos.*;

import java.util.List;

//Preciso criar Umaa maneira de mudar a reflexão do raio baseado no Reflexão...
//Todo objeto vai ter Uma Reflexão que define Umaa reflexão para determinado ponto de colisão
//Dado Uma raio r ele vai gerar n raios ri
//Reflexão especular gera Uma raio refletido
//Criar Umaa classe Reflexão, todo objeto tem Uma Reflexão
//Para determinado ponto, Uma Reflexão retorna Umaa série de raios
//Uma Reflexão especular teria Uma índice de especularidade, ou seja, quanto ele reflete de luz
//Uma Reflexão glossy similarmente teria outro coeficiente
//Uma Reflexão translucido também
//Uma Reflexão de difusão monte-carlo teria também Uma coeficiente para cada raio refletido
//esse ultimo teria Uma calculo inicial com n raios para determinar a distribuição de probabilidades
//Melhor deixar para implementar como Uma raytracer o montecarlo

//Objeto => n materiais cada qual com Umaa razão para sua reflexão

//Logo, Uma Reflexão tem Umaa razão de eficiência para geração de raios

public abstract class Superficie {

    double eficiencia = 1.0;

    public abstract void refletir(Ponto ponto, Raio raio, List<Raio> raios);
}

//Material has reflections, => objeto.material.reflect(ponto,raio,raios);