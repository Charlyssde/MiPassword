/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.ArrayList;

/**
 *
 * @author Jatniel Martínez
 */
public class Boveda {
  
  private String nombre;
  private String numLlaves;
  private Usuario owner;
  private ArrayList<Llave> llaves;
  
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

  public String getNumLlaves() {
    return numLlaves;
  }

  public void setNumLlaves(String numLlaves) {
    this.numLlaves = numLlaves;
  }

  public Usuario getOwner() {
    return owner;
  }

  public void setOwner(Usuario owner) {
    this.owner = owner;
  }

  public ArrayList<Llave> getLlaves() {
    return llaves;
  }

  public void setLlaves(ArrayList<Llave> llaves) {
    this.llaves = llaves;
  }
 
   public final void cargarLlaves(){
    llaves = new ArrayList<>();
    Llave uno = new Llave(this,"www.website.com", "YISUSK98", "32148");
    Llave dos = new Llave(this,"www.afsa.com", "OOI", "321815");
    llaves.add(uno);
    llaves.add(dos);
    
  }
   
   @Override
   public String toString(){
     return nombre;
   }
}
