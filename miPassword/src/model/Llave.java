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
  
  private String url;
  private String username;
  private String password;
  private Boveda boveda;
  
  public Llave() {
    
  }
  
  public Llave(String url, String username, String password) {
    this.url = url;
    this.username = username;
    this.password = password;
    //this.boveda = boveda;
  }
  
  @Override
  public String toString(){
    return username + " - " + password;
  }
  
}
