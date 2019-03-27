/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AlertMessage;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author texch
 */
public class LogInController implements Initializable {

  @FXML
  private AnchorPane anchorPane;
  @FXML
  private Label lblRegistro;
  @FXML
  private TextField txtCorreo;
  @FXML
  private TextField txtPassword;
  @FXML
  private Button btnIngresar;
  
  private Usuario user;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

  }

  @FXML
  private void registrarse(MouseEvent event) {
    //cargarPantallaRegistro();
  }

  @FXML
  private void iniciarSesion(MouseEvent event) {
    if (validarDatos()) {
      user = new Usuario(txtCorreo.getText());
      if(user.getUsername().equals("Admin")){
        cargarPantallaBovedas();
      }else{
        AlertMessage.mensaje("Error");
      }
      
    }
  }

  private boolean validarDatos() {
    if (txtCorreo.getText().isEmpty() || txtPassword.getText().isEmpty()) {
      AlertMessage.mensaje("Por favor ingresar todos los datos solicitados");
      return false;
    }
    return true;
  }
  
  
  private void cargarPantallaRegistro(){
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Registro.fxml"));
        loader.load();
        AnchorPane root = loader.getRoot();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Registro");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
      } catch (IOException ex) {
        Logger.getLogger(MiPassword.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  private void cargarPantallaBovedas(){
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/BovedasList.fxml"));
        loader.load();
        AnchorPane root = loader.getRoot();
        BovedasListController ventana = loader.getController();
        ventana.cargarUsuario(user);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        primaryStage.setTitle("Bovedas");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
      } catch (IOException ex) {
        Logger.getLogger(MiPassword.class.getName()).log(Level.SEVERE, null, ex);
      }
  }

}
