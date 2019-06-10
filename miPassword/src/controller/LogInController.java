/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import model.AlertMessage;
import mipasswordinterface.Usuario;
import mipasswordinterface.Login;
import model.AES;

/**
 * Clase controller encargada de mostrar el login y dar la opcion de registro
 *
 * @author Carlos Carrillo
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

  private Usuario user = null;

  private static final String PATRON = "^(.+)@(.+)$";

  private Client cliente;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    
    try {
      cliente = new Client();
    } catch (RemoteException ex) {
      Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   * Método que intenta un inicio de sesión de acuerdo a los datos ingresados
   * @param event
   * @throws RemoteException 
   */
  @FXML
  private void iniciarSesion(MouseEvent event) throws RemoteException {
    if (validarDatos()) {
      if (validarCorreo()) {
        Login temp = new Login(txtCorreo.getText(),txtPass.getText() );
        user = cliente.server.LogIn(temp);
        if (user != null) {
          cargarPantallaBovedas();
        } else {
          AlertMessage.mensaje("No se ha encontrado el usuario deseado, ingrese datos correctos");
        }

      } else {
        AlertMessage.mensaje("El correo ingresado no es válido, inserte uno válido");
      }

    } else {
      AlertMessage.mensaje("Por favor ingresar todos los datos solicitados");
    }
  }

  /**
   * metodo que determina si se encuentran o no vacios los campos
   * 
   * @return si algun campo se encuentra vacío
   */
  private boolean validarDatos() {
    return !(txtCorreo.getText().isEmpty() || txtPass.getText().isEmpty());
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
   * evento que manda a llamar al metodo para cargar la pantalla de registro
   * @param event 
   */
  @FXML
  private void RegistrarUsuario(MouseEvent event) {
    cargarPantallaRegistro();
  }

  /**
   * Metodo que se encarga de inicializar la pantalla correspondiente al registro de usuario
   */
  private void cargarPantallaRegistro() {
    txtCorreo.setText("");
    txtPass.setText("");
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Registro.fxml"));
      loader.load();
      AnchorPane root = loader.getRoot();
      Scene scene = new Scene(root);
      RegistroController display = loader.getController();
      display.cargarCliente(cliente);
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

  /**
   * Metodo que, una vez hecho el login, carga la pantalla principal del programa
   */
  private void cargarPantallaBovedas() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/BovedasList.fxml"));
      loader.load();
      AnchorPane root = loader.getRoot();
      BovedasListController ventana = loader.getController();
      ventana.cargarUsuario(this.user, cliente);
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

  /**
   * metodo para cargar el cliente conectado al servidor
   * @param c cliente conectado al servidor
   */
  public void CargarCliente(Client c) {
    this.cliente = c;
  }

  /**
   * metodo para desencriptar la contraseña a partir de la contraseña entrante
   * 
   * @param text contraseña 
   * @return contraseña desencriptada
   * @throws RemoteException 
   */
  /*private String desencriptarPassword(String text) throws RemoteException {
    user = cliente.server.getUsuario(txtCorreo.getText(), txtPass.getText());
    String passDec = "";
    try {
      AES cript = new AES(user);
       passDec = cript.DecryptPassword(user.getPassword());
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NoSuchPaddingException ex) {
      Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvalidKeyException ex) {
      Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalBlockSizeException ex) {
      Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (BadPaddingException ex) {
      Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return passDec;
  }*/
  

}
