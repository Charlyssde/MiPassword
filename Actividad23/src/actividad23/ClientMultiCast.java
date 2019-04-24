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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author texch
 */
public class ClientMultiCast {

  public static void main(String[] args) {

    MulticastSocket ms = null;
    InetAddress ia = null;
    String respuesta;

    try {
      ms = new MulticastSocket(4000);
      ia = InetAddress.getByName("224.0.0.1");
      ms.joinGroup(ia);
      byte[] buffer = new byte[8192];

      while (true) {
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        ms.receive(dp);

        respuesta = new String(dp.getData(), dp.getOffset(), dp.getLength());

        System.out.println(respuesta);

      }

    } catch (IOException ex) {
      Logger.getLogger(ClientMultiCast.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      if (ms != null) {
        try {
          ms.leaveGroup(ia);
          ms.close();
        } catch (IOException ex) {
          Logger.getLogger(ClientMultiCast.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      }
    }
  }

}
