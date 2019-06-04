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
 * FXML Controller class
 *
 * @author texch
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
  
  private final String PATRON = "^(.+)@(.+)$";
  private Usuario usuario;
  private Client cliente;
 
  BovedasListController anterior;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

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
  
  private boolean camposVacios(){
    
    if(txtApellidos.getText().isEmpty() || txtCorreo.getText().isEmpty() || txtNombre.getText().isEmpty()
        || txtTelefono.getText().isEmpty()){
      return true;
    }
    
    return false;
  }
  
  private boolean esCorreo(){
    
    Pattern pattern = Pattern.compile(PATRON);
    Matcher matcher = pattern.matcher(txtCorreo.getText());
    return matcher.matches();
    
  }
  
  public void cargarDatos(Usuario usuario, Client cl, BovedasListController anterior){
    this.usuario = usuario;
    txtApellidos.setText(usuario.getApellido());
    txtCorreo.setText(usuario.getCorreo());
    txtNombre.setText(usuario.getNombre());
    txtTelefono.setText(usuario.getTelefono());
    this.cliente = cl;
    this.anterior = anterior;
    
  }

  @FXML
  private void cancelar(MouseEvent event) {
    cerrar();
        
  }
  
  private void cerrar(){
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
  
}
