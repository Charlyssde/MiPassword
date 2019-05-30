/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.AlertMessage;
import model.Peticiones;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author texch
 */
public class RegistroController implements Initializable {

  @FXML
  private AnchorPane anchorPane;
  @FXML
  private Button btnGuardar;
  @FXML
  private Button btnCancelar;
  @FXML
  private TextField txtUsername;
  @FXML
  private Label lblVerificarNumero;
  @FXML
  private TextField txtPassword;
  @FXML
  private TextField txtCorreo;
  @FXML
  private TextField txtApellidos;
  @FXML
  private TextField txtNombres;
  @FXML
  private TextField txtTelefono;

  private final String PATRON = "^(.+)@(.+)$";
  @FXML
  private Label lblInfo;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void verificarNumeroTelefonico(MouseEvent event) {

  }

  @FXML
  private void guardarUsuario(MouseEvent event) {
    if (camposVacios()) {
      AlertMessage.mensaje("No pueden quedar campos vacíos");
    } else if (!validarCorreo()) {
      AlertMessage.mensaje("El correo electrónico no es válido, inserte otro");
      txtCorreo.clear();
    } else {
      Usuario nuevo = new Usuario(txtUsername.getText(), txtNombres.getText(),
          txtApellidos.getText(), txtTelefono.getText(), txtPassword.getText(),
          txtCorreo.getText());
      Peticiones.RegistrarNuevo(nuevo);
      int result = Peticiones.responseCode;
      if (result == 200) {
        AlertMessage.mensaje("Registro exitoso, pruebe a iniciar sesión desde la pantalla de login.");
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
      } else {
        if (result == 400) {
          AlertMessage.mensaje("Ya existe un usuario con este nombre, seleccione otro, por favor.");
          txtUsername.clear();
        }
        if (result == 0) {
          AlertMessage.mensaje("No se pudo conectar con el servidor, intente de nuevo más tarde");
        } else {
          if (result >= 400 && result < 500 && result != 404) {
            AlertMessage.mensaje("Ocurrió un problema al intentar guardar el usuario, inténtelo más tarde");
          }
          if (result >= 500 || result < 200) {
            AlertMessage.mensaje("Ocurrió un error en el servidor, intente de nuevo");
          }
        }
      }
    }
  }

  @FXML
  private void cencelarRegistro(MouseEvent event) {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  private boolean camposVacios() {
    return (txtCorreo.getText().isEmpty() || txtPassword.getText().isEmpty()
        || txtNombres.getText().isEmpty() || txtApellidos.getText().isEmpty()
        || txtUsername.getText().isEmpty() || txtTelefono.getText().isEmpty());
  }

  private boolean validarCorreo() {
    Pattern pattern = Pattern.compile(PATRON);
    Matcher matcher = pattern.matcher(txtCorreo.getText());
    return matcher.matches();
  }

  @FXML
  private void ingresarPassword(KeyEvent event) {
  }

}
