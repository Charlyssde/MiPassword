/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jatniel Martínez
 */
public class Boveda {
  
  private String nombre;
  private int numLlaves;
  private Usuario owner;
  private ObservableList<Llave> llaves;
  
  public Boveda() {
    
  }
  
  public Boveda(String nombre, Usuario owner) {
    this.nombre = nombre;
    this.owner = owner;
    cargarLlaves();
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getNumLlaves() {
    return numLlaves;
  }

  public void setNumLlaves() {
    this.numLlaves = llaves.size();
  }

  public Usuario getOwner() {
    return owner;
  }

  public void setOwner(Usuario owner) {
    this.owner = owner;
  }

  public List<Llave> getLlaves() {
    return llaves;
  }
 
   public final List<Llave> cargarLlaves(){
    llaves = FXCollections.observableArrayList();
    Llave uno = new Llave("Primera","www.website.com", "YISUSK98", "32148");
    Llave dos = new Llave("Segunda","www.afsa.com", "OOI", "321815");
    llaves.add(uno);
    llaves.add(dos);
    return llaves;
  }
   
   @Override
   public String toString(){
     return nombre;
   }
}
