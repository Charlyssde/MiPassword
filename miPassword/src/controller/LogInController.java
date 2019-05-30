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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AlertMessage;
import model.Login;
import model.Peticiones;
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
  private PasswordField txtPass;
  @FXML
  private Button btnIngresar;

  private Usuario user;

  private final String PATRON = "^(.+)@(.+)$";

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

  }

  @FXML
  private void iniciarSesion(MouseEvent event) {
    if (validarDatos()) {
      if (validarCorreo()) {
        Login intento = new Login(txtCorreo.getText(), txtPass.getText());
        user = Peticiones.Login(intento);
        if (user == null) {
          int resp = Peticiones.responseCode;
          if (resp == 0) {
            AlertMessage.mensaje("No se pudo conectar con el servidor, intente de nuevo más tarde");
          } else {
            if (resp == 404) {
              AlertMessage.mensaje("Usuario no encontrado");
            }
            if (resp >= 400 && resp < 500 && resp != 404) {
              AlertMessage.mensaje("Bad Request");
            }
            if (resp >= 500 || resp < 200) {
              AlertMessage.mensaje("Ocurrió un error en el servidor, intente de nuevo");
            }
          }

        } else {
          cargarPantallaBovedas();
        }
      } else {
        AlertMessage.mensaje("El correo ingresado no es válido, inserte uno válido");
      }
    } else {
      AlertMessage.mensaje("Por favor ingresar todos los datos solicitados");
    }
  }

  private boolean validarDatos() {
    return !(txtCorreo.getText().isEmpty() || txtPass.getText().isEmpty());
  }

  private boolean validarCorreo() {
    Pattern pattern = Pattern.compile(PATRON);
    Matcher matcher = pattern.matcher(txtCorreo.getText());
    return matcher.matches();
  }

  @FXML
  private void RegistrarUsuario(MouseEvent event) {
    cargarPantallaRegistro();
  }

  private void cargarPantallaRegistro() {
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

  private void cargarPantallaBovedas() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/BovedasList.fxml"));
      loader.load();
      AnchorPane root = loader.getRoot();
      BovedasListController ventana = loader.getController();
      ventana.cargarUsuario(this.user);
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
