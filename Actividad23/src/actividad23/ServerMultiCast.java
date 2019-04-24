/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad23;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author texch
 */
public class ServerMultiCast {
  
  
  public static void main(String[] args) {
    MulticastSocket ms = null;
    InetAddress ia = null;
    int port = 4000;
    DatagramPacket  dp = null;
    String mensaje;
    try{
      ia = InetAddress.getByName("224.0.0.1");
      ms = new MulticastSocket();
      int contador = 1;
      while(true){
        
        mensaje = "mensaje #" + contador++;
        dp = new DatagramPacket(mensaje.getBytes("UTF-8"), mensaje.getBytes("UTF-8").length, ia, port);
        
        ms.send(dp);
        Thread.currentThread().sleep(2000);
        
      }
    } catch (UnknownHostException ex) {
      Logger.getLogger(ServerMultiCast.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(ServerMultiCast.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InterruptedException ex) {
      Logger.getLogger(ServerMultiCast.class.getName()).log(Level.SEVERE, null, ex);
    } finally{
      if(ms != null){
        ms.close();
      }
    }
    
  }
}
