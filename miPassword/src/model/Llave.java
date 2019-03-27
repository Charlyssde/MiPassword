/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Boveda;

/**
 *
 * @author Jatniel Martínez
 */
public class Llave {
  
  private int id;
  private String nombre;
  private String url;
  private String username;
  private String password;
  private Boveda boveda;
  
  public Llave() {
    
  }
  
  public Llave(String nombre,String url, String username, String password) {
    this.nombre = nombre;
    this.url = url;
    this.username = username;
    this.password = password;
    //this.boveda = boveda;
  }
  
  @Override
  public String toString(){
    return nombre + " - " + username + " - " + password;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boveda getBoveda() {
    return boveda;
  }

  public void setBoveda(Boveda boveda) {
    this.boveda = boveda;
  }
  
  
}
