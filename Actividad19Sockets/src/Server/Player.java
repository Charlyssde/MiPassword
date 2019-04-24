/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.Color;

/**
 *
 * @author texch
 */
public class Player {
  
  public static final int WIDTHPLAYER = 20;
  public static final int LABELMARGIN = 5;
  
  private Color color;
  private String name;
  private int posX;
  private int posY;
  private int port;
  private String hostName;
  private int score;

  public Player(Color color, String name, int posX, int posY, int port, String hostName) {
    this.color = color;
    this.name = name;
    this.posX = posX;
    this.posY = posY;
    this.port = port;
    this.hostName = hostName;
    this.score = 0;
  }

  /**
   * @return the posX
   */
  public int getPosX() {
    return posX;
  }

  /**
   * @param posX the posX to set
   */
  public void setPosX(int posX) {
    this.posX = posX;
  }

  /**
   * @return the posY
   */
  public int getPosY() {
    return posY;
  }

  /**
   * @param posY the posY to set
   */
  public void setPosY(int posY) {
    this.posY = posY;
  }

  /**
   * @return the port
   */
  public int getPort() {
    return port;
  }

  /**
   * @param port the port to set
   */
  public void setPort(int port) {
    this.port = port;
  }

  /**
   * @return the hostName
   */
  public String getHostName() {
    return hostName;
  }

  /**
   * @param hostName the hostName to set
   */
  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  /**
   * @return the score
   */
  public int getScore() {
    return score;
  }

  /**
   * @param score the score to set
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * @return the color
   */
  public Color getColor() {
    return color;
  }

  /**
   * @param color the color to set
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
  
  
  
}
