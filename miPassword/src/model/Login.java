/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author texch
 */
public class Login {
  
   public String correo;
  
  public String password;
  
  public Login(String email, String password){
    this.correo =  email;
    this.password = password;
  }
  
}
