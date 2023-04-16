package modelos;
import java.util.ArrayList;

public abstract class Observable{

  ArrayList<Observer> observers = new ArrayList<>();

  public void addObserver(Observer observer){
    observers.add(observer);
  }

  public void notificar(){
    for(int i =0; i < observers.size(); i++){ 
      observers.get(i).atualizar();
    }
  }
}