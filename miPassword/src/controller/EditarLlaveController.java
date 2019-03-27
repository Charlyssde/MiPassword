/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.AlertMessage;
import model.Boveda;
import model.Llave;

/**
 * FXML Controller class
 *
 * @author texch
 */
public class EditarLlaveController implements Initializable {

  @FXML
  private AnchorPane anchorPane;
  @FXML
  private Button btnGuardar;
  @FXML
  private Button btnCancelar;
  @FXML
  private TextField txtUrl;
  @FXML
  private TextField txtUsuario;
  @FXML
  private TextField txtPassword;
  @FXML
  private TextField txtNombreLlave;
  
  private BovedasListController anterior;
  
  private Boveda editada;
  
  private Llave selectedLlave;
  
  private List<Llave> llaves;


  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

  @FXML
  private void guardarLlave(MouseEvent event) {
    if(validarDatos()){
      for(Llave key : llaves){
        if(key.equals(selectedLlave)){
          key.setNombre(txtNombreLlave.getText());
          key.setPassword(txtPassword.getText());
          key.setUrl(txtUrl.getText());
          key.setUsername(txtUsuario.getText());
          anterior.actualizarTablaLlaves(editada);
          break;
        }
      }
    }
  }

  @FXML
  private void cancelar(MouseEvent event) {
    cerrarVentana();
  }
  
  private boolean validarDatos(){
    if(txtNombreLlave.getText().isEmpty() || txtPassword.getText().isEmpty() || 
        txtUrl.getText().isEmpty() || txtUsuario.getText().isEmpty()){
      AlertMessage.mensaje("No pueden quedar campos vacíos");
      return false;
    }
    return true;
  }
  
  private void cerrarVentana(){
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  public void cargarDatos(BovedasListController anterior, Boveda editada, Llave selectedLlave, List<Llave> llaves) {
    this.anterior = anterior;
    this.editada = editada;
    this.selectedLlave = selectedLlave;
    this.llaves = llaves;
    cargarInformacion(this.selectedLlave);
  }

  private void cargarInformacion(Llave selectedLlave) {
    txtNombreLlave.setText(selectedLlave.getNombre());
    txtUrl.setText(selectedLlave.getUrl());
    txtUsuario.setText(selectedLlave.getUsername());
    txtPassword.setText(selectedLlave.getPassword());
  }
  
}
