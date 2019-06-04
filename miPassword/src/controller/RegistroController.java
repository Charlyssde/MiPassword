/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.rmi.RemoteException;
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
import mipasswordinterface.Usuario;
import model.AlertMessage;

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
  private Label lblVer;
  @FXML
  private Label lblVer1;
  @FXML
  private TextField txtPassReveal;
  private Client cliente;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void guardarUsuario(MouseEvent event) throws RemoteException {
    if (camposVacios()) {
      AlertMessage.mensaje("No pueden quedar campos vacíos");
    } else if (!validarCorreo()) {
      AlertMessage.mensaje("El correo electrónico no es válido, inserte otro");
      txtCorreo.clear();
    } else {

      Usuario nuevo = new Usuario(txtUsername.getText(), txtNombres.getText(),
          txtApellidos.getText(), txtTelefono.getText(), txtPassword.getText(),
          txtCorreo.getText());
      /*AES cript = new AES(nuevo);
      cript.genKeyPair(512);
      
      String newPass = cript.EncryptPassword(nuevo.getPassword());
      nuevo.setPassword(newPass);
      */
      cliente.server.registrarUsuario(nuevo);
      AlertMessage.mensaje("Registro guardado exitosamente, inicie sesión nuevamente");
      Stage stage = (Stage) anchorPane.getScene().getWindow();
      stage.close();
    }
  }

  @FXML
  private void cencelarRegistro(MouseEvent event) {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  public void cargarCliente(Client cl) {
    this.cliente = cl;
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
    lblVer.setVisible(true);
    lblVer.setDisable(false);
  }

  @FXML
  private void hidePassword(MouseEvent event) {
    this.txtPassword.setVisible(true);
    this.txtPassReveal.setVisible(false);
  }

  @FXML
  private void revealPassword(MouseEvent event) {
    this.txtPassReveal.setText(txtPassword.getText());
    this.txtPassword.setVisible(false);
    this.txtPassReveal.setVisible(true);

  }

}
