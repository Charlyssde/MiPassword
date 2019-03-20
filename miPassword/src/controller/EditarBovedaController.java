/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author texch
 */
public class EditarBovedaController implements Initializable {

  @FXML
  private AnchorPane anchorPane;
  @FXML
  private TextField txtNombre;
  @FXML
  private Button btnAceptar;
  @FXML
  private Button btnGuardar;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

  @FXML
  private void agregarBoveda(MouseEvent event) {
    
    cerrar();
  }

  @FXML
  private void cancelar(MouseEvent event) {
    cerrar();
  }
  
  public void cargarBoveda(String nombre){
    txtNombre.setText(nombre); 
  }
  
  public void cerrar(){
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
  
}
