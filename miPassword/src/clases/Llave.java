/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import interfaces.InterfaceLlave;

/**
 *
 * @author Jatniel Martínez
 */
public class Llave implements InterfaceLlave {
  
  private char url;
  private char nomUsuario;
  private char password;
  
  public Llave() {
    
  }
  
  public Llave(char url, char nomUsuario, char password) {
    this.url = url;
    this.nomUsuario = nomUsuario;
    this.password = password;
  }

  @Override
  public void crearLlave() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void consultarLlaves() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void actualizarLlave() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void eliminarLlave() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
