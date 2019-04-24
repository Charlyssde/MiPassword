/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author texch
 */
public class UDPCliente {

  public UDPCliente() {
  }
  
  public static void main(String[] args) {
    DatagramSocket aSocket = null;
    try{
      aSocket = new DatagramSocket();
      byte[] m = "Carlos".getBytes();
      InetAddress aHost = InetAddress.getByName("localhost");
      int serverPort = 6789;
      DatagramPacket request = new DatagramPacket(m,m.length,aHost, serverPort);
      aSocket.send(request);
    } catch (SocketException ex) {
      Logger.getLogger(UDPCliente.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnknownHostException ex) {
      Logger.getLogger(UDPCliente.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(UDPCliente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
