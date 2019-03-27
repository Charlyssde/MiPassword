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
public class AgregarLlaveController implements Initializable {

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
  private AnchorPane anchorPane;
  @FXML
  private TextField txtNombreLlave;
  
  private Boveda owner;
  
  private BovedasListController anterior;
  
  private List<Boveda> nuevaLista;

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
      Llave nueva = new Llave(txtNombreLlave.getText(),txtUrl.getText(), txtUsuario.getText(), txtPassword.getText());
      for(Boveda b : nuevaLista){
        if(b.getNombre().equals(owner.getNombre())){
          b.cargarLlaves().add(nueva);
          anterior.actualizarTablaLlaves(owner);
          anterior.actualizarListaBovedas((ArrayList<Boveda>)nuevaLista);
          cerrar();
        }
      }
    } 
  }

  @FXML
  private void cancelar(MouseEvent event) {
    cerrar();
  }
   
  
  private boolean validarDatos(){
    if(txtNombreLlave.getText().isEmpty() || txtPassword.getText().isEmpty() ||
        txtUrl.getText().isEmpty() || txtUsuario.getText().isEmpty()){
      AlertMessage.mensaje("No pueden quedar campos vacíos");
      return false;
    } else if(yaExiste()){
      AlertMessage.mensaje("Ya existe una bóveda con ese nombre, no pueden existir dos iguales");
      return false;
    }
    return true;
  }
  
  private boolean yaExiste(){
    List<Llave> hermanas = owner.getLlaves();
    for(Llave key : hermanas){
      if(txtNombreLlave.getText().equals(key.getNombre())){
        return true;
      }
    }
    return false;
  }
  
  public void cargarDatos(BovedasListController anterior,Boveda owner, ArrayList<Boveda> nuevaLista){
    this.nuevaLista = nuevaLista;
    this.anterior = anterior;
    this.owner = owner;  
  }
  private void cerrar(){
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
}
