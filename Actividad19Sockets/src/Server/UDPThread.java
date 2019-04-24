/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import static Server.MainServer.players;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author texch
 */
public class UDPThread implements  Runnable{

  @Override
  public void run() {
    DatagramSocket aSocket = null;
    try{
      aSocket = new DatagramSocket(6789);
      byte[] buffer = new byte[1000];
      System.out.println("--> SERVIDOR INICIADO EN PUERTO 6789");
      System.out.println("-------------------------------------");
      while(true){
        DatagramPacket request = new DatagramPacket(buffer,buffer.length);
        aSocket.receive(request);
        
        System.out.printf("Conexion recibida de: %s - %s\n", request.getPort(), request.getAddress().getHostName());
        String mensaje = new String(request.getData(), request.getOffset(), request.getLength());
        
        int[] position = Lienzo.getRandomFreePosition();
        players.add(new Player(Lienzo.getRandomColor(), mensaje, position[0], position[1], 
            request.getPort(), request.getAddress().getHostName()));
        
      }
      
    } catch (SocketException ex) {
      Logger.getLogger(UDPThread.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(UDPThread.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      if(aSocket != null){
        aSocket.close();
      }
    }
  }
  
}
