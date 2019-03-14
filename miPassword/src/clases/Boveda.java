/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import interfaces.InterfaceBoveda;

/**
 *
 * @author Jatniel Martínez
 */
public class Boveda implements InterfaceBoveda {
  
  private char nombre;
  private char numLlaves;
  
  public Boveda() {
    
  }
  
  public Boveda(char nombre, char numLlaves) {
    this.nombre = nombre;
    this.numLlaves = numLlaves;
  }

  @Override
  public void crearBoveda() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void consultarBovedas() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void actualizarBovedas() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void eliminarBovedas() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
