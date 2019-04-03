/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Permite enviar un SMS desde Java.
 * @author JatnielMartínez
 */
public class JavaSmsHttpRequest {
  
  private final String destinatario;
  private final String mensaje;
  private final String usuario;
  private final String contrasena;
  private final String remitente;
  
  public JavaSmsHttpRequest(String recipient, String message, String username, String password, String originator) {
    destinatario = recipient;
    mensaje = message;
    usuario = username;
    contrasena = password;
    remitente = originator;
  }
  
  public void enviarMensaje() {
    try {
      String enc = "UTF-8";    //Codificador de caracteres para el mensaje
      String solicitudURL = "http://127.0.0.1:9501/api?action=sendmessage"    //Se declara la url de la solicitud
          + "&username=" + URLEncoder.encode(usuario, enc)
          + "&password=" + URLEncoder.encode(contrasena, enc)
          + "&recipient=" + URLEncoder.encode(destinatario, enc)
          + "&messagetype=SMS:TEXT"    //Establece el tipo de mensaje
          + "&messagedata=" + URLEncoder.encode(mensaje, enc)
          + "&originator=" + URLEncoder.encode(remitente, enc)
          + "&serviceprovider=GSMModem1"    //Establece el proveedor de servicios
          + "&responseformat=html";    //Establece el formato de respuesta
      URL url = new URL(solicitudURL);
      HttpURLConnection uc = (HttpURLConnection)url.openConnection();
      System.out.println(uc.getResponseMessage());    //Devuelve el mensaje de respuesta
      uc.disconnect();
    } catch (UnsupportedEncodingException ex) {
      System.err.println("UnsupportedEncodingException: " + ex.getMessage());
    } catch (MalformedURLException ex) {
      System.err.println("MalformedURLException: " + ex.getMessage());
    } catch (IOException ex) {
      System.err.println("IOException: " + ex.getMessage());
    }
  }
  
}
