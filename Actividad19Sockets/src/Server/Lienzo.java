/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;
import javax.swing.JLabel;

/**
 *
 * @author texch
 */
public class Lienzo extends JLabel{
  
  private static final int CELLS = 20;
  private static final int CELLWIDTH = 20;
  private static final int WIDTH = 400;
  private static final int HEIGHT = 400;
  
  public static int[] [] board;
  private static Color[] colors;
  
  public Lienzo(){
    board = new int [20] [20];
    
    for(int y = 0; y < CELLS; y++){
      for (int x = 0; x < CELLS; x++){
        board [x][y] = 0;
      }
    }
    
    colors = new Color[] {Color.GRAY, Color.GREEN, Color.BLUE, Color.CYAN, 
      Color.RED, Color.RED, Color.ORANGE,Color.MAGENTA};
    
  }
  
  public static int[] getRandomFreePosition(){
    int[] position = new int[2];
    Random rd = new Random();
    do{
      position[0] = rd.nextInt(CELLS);
      position[1] = rd.nextInt(CELLS);
    }while (board [position[0]][position[1]] != 0);
    
    return position;
  }
  
  public static Color getRandomColor(){
    Random rd = new Random();
    return colors[rd.nextInt(colors.length)];
  }
  
  public Dimension getPreferredSize(){
    return new Dimension(WIDTH, HEIGHT);
  }
  
  protected void paintComponent(Graphics graphics){
    super.paintComponent(graphics);
    
    if(graphics instanceof Graphics2D){
      Graphics2D graphics2D = (Graphics2D) graphics;
      graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      
      graphics2D.setColor(Color.black);
      graphics2D.fillRect(0, 0, WIDTH, HEIGHT);
      
      graphics2D.setColor(Color.red);
      //graphics2D.fillOval(100, 100, 20, 20);
      
      
      for(Player p : MainServer.players){
      
        graphics2D.setColor(p.getColor());
        
        graphics2D.drawString(p.getName() + " (" + p.getScore() + ")", 
            p.getPosX() * CELLWIDTH, (p.getPosY() * CELLWIDTH) - Player.LABELMARGIN);
        
        graphics2D.fillOval(p.getPosX() * CELLWIDTH, p.getPosY() * CELLWIDTH, 
            Player.WIDTHPLAYER, Player.WIDTHPLAYER);
      
      }
      
    }
  }
  
  
}
