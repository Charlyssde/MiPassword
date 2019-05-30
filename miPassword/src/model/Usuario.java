/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jatniel Martínez
 */
public class Usuario {
  
  private String username;
  private String nombre;
  private String apellido;
  private String telefono;
  private String password;
  private String correo;
  private PublicKey clavePublica;
  private String clavePrivada;
  private List<Boveda> bovedas;
  
  private final Random rd = new Random();

  public Usuario(String username, String nombre, String apellido, String telefono, 
      String password, String correo) {
    
    this.username = username;
    this.nombre = nombre;
    this.apellido = apellido;
    this.telefono = telefono;
    this.password = password;
    this.correo = correo;
  }
  
  public Usuario() {
    
  }
  
  public Usuario(String correo, String password){
    this.correo = correo;
    this.password = password;
    bovedas = new ArrayList<>();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public PublicKey getClavePublica() {
    return clavePublica;
  }

  public void setClavePublica(PublicKey clavePublica) {
    this.clavePublica = clavePublica;
  }

  public String getClavePrivada() {
    return clavePrivada;
  }

  public void setClavePrivada(String clavePrivada) {
    this.clavePrivada = clavePrivada;
  }
  
  public void setBovedas(List<Boveda> bovedas){
    this.bovedas = bovedas;
  }
  
  public ArrayList<Boveda> getBovedas(){
    return (ArrayList<Boveda>)bovedas;
  }
  
}
