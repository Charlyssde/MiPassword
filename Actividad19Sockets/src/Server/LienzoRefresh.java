/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author texch
 */
public class LienzoRefresh implements Runnable{

  private Lienzo lienzo;
  
  public LienzoRefresh(Lienzo lienzo){
    this.lienzo = lienzo;
  }
  @Override
  public void run() {
    while(true){
      try{
        lienzo.update(lienzo.getGraphics());
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        System.out.println("InterruptedException LienzoRefresh");
      }
    }
  }
  
}
