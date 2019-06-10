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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mipasswordinterface.Usuario;
import model.AlertMessage;

/**
 * Clase controller encargada de la edicion de datos del usuario
 *
 * @author Carlos Carrillo
 */
public class EditarUsuarioController implements Initializable {

  @FXML
  private AnchorPane anchorPane;
  @FXML
  private TextField txtNombre;
  @FXML
  private TextField txtApellidos;
  @FXML
  private TextField txtCorreo;
  @FXML
  private TextField txtTelefono;
  @FXML
  private Button btnGuardar;
  @FXML
  private Button btnCancelar;
  
  private static final String PATRON = "^(.+)@(.+)$";
  private Usuario usuario;
  private Client cliente;
 
  BovedasListController anterior;

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
   * metodo que cambia los datos del usuario y los actualiza
   * @param event
   * @throws RemoteException 
   */
  @FXML
  private void guardarDatos(MouseEvent event) throws RemoteException {
    
    if(camposVacios()){
      AlertMessage.mensaje("No pueden quedar campos vacíos");
    }else{
      if(esCorreo()){
        usuario.setNombre(txtNombre.getText());
        usuario.setApellido(txtApellidos.getText());
        usuario.setCorreo(txtCorreo.getText());
        usuario.setTelefono(txtTelefono.getText());
        cliente.server.editarUsuario(usuario);
        AlertMessage.mensaje("Se actualizaron los datos correctamente");
        anterior.actualizarUsuario(usuario);
        cerrar();
      }else{
        AlertMessage.mensaje("El correo no es valido, inserte uno valido");
      }
    }
    
  }
  
  /**
   * metodo que valida que todos los campos estén llenos
   * @return true si algun campo está lleno, false si no hay campos vacios
   */
  private boolean camposVacios(){
    
    return txtApellidos.getText().isEmpty() || txtCorreo.getText().isEmpty() || txtNombre.getText().isEmpty()
        || txtTelefono.getText().isEmpty();
  }
  
  /**
   * Metodo para validar que el campo de correo siga el patron de un corero electronico.
   * 
   * @return si es un correo valido o no.
   */
  private boolean esCorreo(){
    
    Pattern pattern = Pattern.compile(PATRON);
    Matcher matcher = pattern.matcher(txtCorreo.getText());
    return matcher.matches();
    
  }
  
  /**
   * Metodo para asignar los valores a los campos de texto y cargar los elementos necesarios
   * @param usuario el usuario con los uevos datos
   * @param cl cliente conectado al servidor 
   * @param anterior pantalla anterior, para acutalizar los datos de esa pantalla
   */
  public void cargarDatos(Usuario usuario, Client cl, BovedasListController anterior){
    this.usuario = usuario;
    txtApellidos.setText(usuario.getApellido());
    txtCorreo.setText(usuario.getCorreo());
    txtNombre.setText(usuario.getNombre());
    txtTelefono.setText(usuario.getTelefono());
    this.cliente = cl;
    this.anterior = anterior;
    
  }

  /**
   * metodo que llama al metodo para cerrar ventana
   * @param event 
   */
  @FXML
  private void cancelar(MouseEvent event) {
    cerrar();
        
  }
  
  /**
   * metodo que cierra la ventana acutal
   */
  private void cerrar(){
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
  
}
