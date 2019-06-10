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
import model.AES;
import model.AlertMessage;

/**
 * Clase controller encargada de realizar el registro de un usuario
 *
 * @author Carlos Carrillo
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

  private static final String PATRON = "^(.+)@(.+)$";
  @FXML
  private Label lblVer;
  @FXML
  private TextField txtPassReveal;
  private Client cliente;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    /*
    No hay funcion para rellenar el método
    */
  }

  /**
   * Realiza la creación de un usuario y lo guarda.
   * @param event el click del mouse sobre el botón de guardar
   * @throws Exception 
   */
  @FXML
  private void guardarUsuario(MouseEvent event) throws RemoteException{
    if (camposVacios()) {
      AlertMessage.mensaje("No pueden quedar campos vacíos");
    } else if (!validarCorreo()) {
      AlertMessage.mensaje("El correo electrónico no es válido, inserte otro");
      txtCorreo.clear();
    } else {

      Usuario nuevo = new Usuario(txtUsername.getText(), txtNombres.getText(),
          txtApellidos.getText(), txtTelefono.getText(), txtPassword.getText(),
          txtCorreo.getText());
      AES cript = new AES(nuevo);
      cript.genKeyPair(512);
      
      cliente.server.registrarUsuario(nuevo);
      AlertMessage.mensaje("Registro guardado exitosamente, inicie sesión nuevamente");
      Stage stage = (Stage) anchorPane.getScene().getWindow();
      stage.close();
    }
  }

  
  /**
   * Cierra la ventana de registro sin relalizar acciones.
   * 
   * @param event evento del click en el boton de cancelar
   * 
   */
  @FXML
  private void cencelarRegistro(MouseEvent event) {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  
  /**
   * Carga el cliente que se encargará de conectar con el servidor 
   * @param cl cliente que se encuentra conectado al servidor para realizar las peticiones
   */
  public void cargarCliente(Client cl) {
    this.cliente = cl;
  }

  
  /**
   * Metodo para verificar que no haya campos vacíos
   * 
   * @return si algun campo se encuentra vacío 
   */
  private boolean camposVacios() {
    return (txtCorreo.getText().isEmpty() || txtPassword.getText().isEmpty()
        || txtNombres.getText().isEmpty() || txtApellidos.getText().isEmpty()
        || txtUsername.getText().isEmpty() || txtTelefono.getText().isEmpty());
  }

  
  /**
   * Metodo para validar que el campo de correo siga el patron de un corero electronico.
   * 
   * @return si es un correo valido o no.
   */
  private boolean validarCorreo() {
    Pattern pattern = Pattern.compile(PATRON);
    Matcher matcher = pattern.matcher(txtCorreo.getText());
    return matcher.matches();
  }

  /**
   * Metodo que habilita la etiqueta para ver la contraseña ingresada
   * @param event tecla
   */
  @FXML
  private void ingresarPassword(KeyEvent event) {
    lblVer.setVisible(true);
    lblVer.setDisable(false);
  }

  
  /**
   * Metodo para ocultar el valor textual de la contraseña
   * @param event quitar click presionado sobre la etiqueta VER
   */
  @FXML
  private void hidePassword(MouseEvent event) {
    this.txtPassword.setVisible(true);
    this.txtPassReveal.setVisible(false);
  }

  /**
   * método para revelar el valor textual de la contraseña
   * @param event click presionado sobre la etiqueta VER
   */
  @FXML
  private void revealPassword(MouseEvent event) {
    this.txtPassReveal.setText(txtPassword.getText());
    this.txtPassword.setVisible(false);
    this.txtPassReveal.setVisible(true);

  }

}
