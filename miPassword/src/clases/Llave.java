/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Jatniel Martínez
 */
public class Llave {
  
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
  
}
