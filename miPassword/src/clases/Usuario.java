/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import interfaces.InterfaceUsuario;

/**
 *
 * @author Jatniel Martínez
 */
public class Usuario implements InterfaceUsuario {
  
  private char username;
  private char nombre;
  private char apellido;
  private char telefono;
  private char password;
  private char correo;
  private char clavePublica;
  private char clavePrivada;
  
  public Usuario() {
    
  }
  
  public Usuario(char username, char nombre, char apellido, char telefono, char password,
      char correo, char clavePublica, char clavePrivada) {
    this.username = username;
    this.nombre = nombre;
    this.apellido = apellido;
    this.telefono = telefono;
    this.password = password;
    this.correo = correo;
    this.clavePublica = clavePublica;
    this.clavePrivada = clavePrivada;
  }

  @Override
  public void registrarUsuario() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void verInformacion() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void cerrarSesion() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
