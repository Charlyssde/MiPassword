/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.MainServer;
import com.sun.imageio.plugins.jpeg.JPEG;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author texch
 */
public class InicioCliente {
  
  public InicioCliente(){
    
    JFrame frame = new JFrame();
    frame.setSize(300,300);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setVisible(true);
    
    JTextField nombre = new JTextField();
    JButton entrar = new JButton("Entrar");
    
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    
    panel.add(nombre,BorderLayout.NORTH);
    panel.add(entrar, BorderLayout.SOUTH);
    
    frame.add(panel);
    
    
  }
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new InicioCliente();
      }
    });
  }
}
