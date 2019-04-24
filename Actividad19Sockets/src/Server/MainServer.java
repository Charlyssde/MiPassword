/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author texch
 */
public class MainServer{
  
  static public List<Player> players;
  
  public MainServer(){
    
    Lienzo lienzo = new Lienzo();
    players = new ArrayList<>(); 
    /*
    int[] pos = Lienzo.getRandomFreePosition();
    players.add(new Player(Lienzo.getRandomColor(), "Player 1", pos[0], pos[1], 0, "hostName"));
    pos = Lienzo.getRandomFreePosition();
    players.add(new Player(Lienzo.getRandomColor(), "Player 2", pos[0], pos[1], 0, "hostName"));
    pos = Lienzo.getRandomFreePosition();
    players.add(new Player(Lienzo.getRandomColor(), "Player 3", pos[0], pos[1], 0, "hostName"));*/
    
    JFrame frame = new JFrame();
    frame.setSize(400,400);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.add(lienzo);
 
    frame.setResizable(false);
    frame.setVisible(true);
    
    new Thread(new UDPThread()).start();
    new Thread(new LienzoRefresh(lienzo)).start();
    
  }
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new MainServer();
      }
    });
  }
}
