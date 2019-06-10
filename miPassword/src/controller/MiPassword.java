/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.AlertMessage;

/**
 *Clase  que inicializa el programa de MiPassword
 * 
 * @author Carlos Carrillo
 */
public class MiPassword extends Application {
  private Client c;
  
  
  /**
   * Metodo que inicia la primera pantalla e inicializa la conexión al servidor
   * @param primaryStage
   * @throws RemoteException 
   */
  @Override
  public void start(Stage primaryStage) {
    
    try {
      c = new Client();
      c.iniciarClient();
    } catch (RemoteException ex) {
      AlertMessage.mensaje("No se pudo conectar al servidor, intente más tarde");
    }
    
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/LogIn.fxml"));
      Parent root = loader.load();
      LogInController display = loader.getController();
      display.CargarCliente(c);
      Scene scene = new Scene(root);
      primaryStage.setTitle("LogIn");
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.show();
    } catch (IOException ex) {
      Logger.getLogger(MiPassword.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
  
}
