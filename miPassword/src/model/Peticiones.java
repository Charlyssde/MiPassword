/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author texch
 */
public class Peticiones {

  private static final String SITE = "http://localhost:5000/";
  
  public static int responseCode;

  public static Usuario Login(Login login) {
    Usuario usuario = null;
    try {
      URL url = new URL(SITE + "api/Login");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Accept", "application/json");
      conn.setConnectTimeout(3000);
      Gson gson = new Gson();
      String json = gson.toJson(login);
      conn.setRequestMethod("POST");
      conn.connect();
      OutputStream outputStream = conn.getOutputStream();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
      writer.write(json);
      writer.close();
      outputStream.close();
      
      responseCode = conn.getResponseCode();
      if (conn.getResponseCode() == 200) {
        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
        TypeToken<Usuario> token = new TypeToken<Usuario>() {
        };
        usuario = new Gson().fromJson(isr, token.getType());
      }
    } catch (java.net.SocketTimeoutException e) {
      responseCode = 0;
    } catch (java.io.IOException e) {
      System.out.println("Exception " + e.getMessage());
    } 
    return usuario;
  }

  public static int EditarDatos(Usuario user, int id) {
    return 0;
  }

  public static List<Boveda> AllBovedas( Usuario user) {
    List<Boveda> bovedas = new ArrayList<>();
try {
      URL url = new URL(SITE );
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");
      conn.setConnectTimeout(1000);
      responseCode = conn.getResponseCode();
      if (conn.getResponseCode() == 200) {
        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
        TypeToken<List<Boveda>> token = new TypeToken<List<Boveda>>() {
        };
        bovedas = new Gson().fromJson(isr, token.getType());
      }
      
    } catch (java.net.SocketTimeoutException e) {
      System.out.println("TimeoutException");
    } catch (java.io.IOException e) {
      System.out.println("Exception " + e.getMessage());
    }
    return bovedas;
  }
  
  public static int EditarBoveda(Boveda boveda, int id){
    return 0;
  }
  
  public static int AgregarBoveda(Boveda boveda){
    return 0;
  }
  
  public static List<Llave> AllLlaves (Boveda boveda){
    List<Llave> llaves = new ArrayList<>();
    try {
      URL url = new URL(SITE);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");
      conn.setConnectTimeout(1000);
      responseCode = conn.getResponseCode();
      if (conn.getResponseCode() == 200) {
        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
        TypeToken<List<Llave>> token = new TypeToken<List<Llave>>() {
        };
        llaves = new Gson().fromJson(isr, token.getType());
      }
      
    } catch (java.net.SocketTimeoutException e) {
      System.out.println("TimeoutException");
    } catch (java.io.IOException e) {
      System.out.println("Exception " + e.getMessage());
    } 
    return llaves;
  }
  
  public static int AgregarLlave(Llave llave){
    return 0;
  }
  
  public static int EditarLlave(Llave llave, int id){
    return 0;
  }

}
