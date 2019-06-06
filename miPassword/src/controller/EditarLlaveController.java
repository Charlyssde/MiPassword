/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.rmi.RemoteException;
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
import mipasswordinterface.Boveda;
import mipasswordinterface.Llave;
import model.AlertMessage;

/**
 * Clase controller encargada de editar la llave seleccionada
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
  private Client cliente;
  private ArrayList<Boveda> bovedas;


  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

  /**
   * evento para guardar la llave
   * @param event
   * @throws RemoteException 
   */
  @FXML
  private void guardarLlave(MouseEvent event) throws RemoteException {
    if(validarDatos()){
      for(Llave key : llaves){
        if(key.equals(selectedLlave)){
          key.setNombre(txtNombreLlave.getText());
          key.setPassword(txtPassword.getText());
          key.setUrl(txtUrl.getText());
          key.setUsername(txtUsuario.getText());
          Llave aux = new Llave(txtNombreLlave.getText(), txtUrl.getText(), 
              txtUsuario.getText(), txtPassword.getText(), editada);
          aux.setId(key.getId());
          cliente.server.editarLlave(aux);
          anterior.actualizarTablaLlaves(editada, cliente);
          AlertMessage.mensaje("Se han modificado los datos exitosamente");
          cerrarVentana();
          break;
        }
      }
    }
  }

  /**
   * evento que llama al metodo de cerrar la ventana
   * @param event 
   */
  
  @FXML
  private void cancelar(MouseEvent event) {
    cerrarVentana();
  }
  
  /**
   * metodo encargado de verificar que no haya datos vacios
   * @return false si hay campos vacios, true si no hay vacios
   */
  private boolean validarDatos(){
    if(txtNombreLlave.getText().isEmpty() || txtPassword.getText().isEmpty() || 
        txtUrl.getText().isEmpty() || txtUsuario.getText().isEmpty()){
      AlertMessage.mensaje("No pueden quedar campos vacíos");
      return false;
    }
    return true;
  }
  
  /**
   * metodo para cerrar la ventana actual
   */
  private void cerrarVentana(){
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  /**
   * metodo para cargar los datos necesarios para la edicion de la llave
   * 
   * @param anterior pantalla anterior
   * @param editada boveda a la que pertenece la llave
   * @param selectedLlave llave seleccionada
   * @param llaves lista de llaves 
   * @param cl cliente conectado al servidor
   * @param bovedas  lista de bovedas 
   */
  public void cargarDatos(BovedasListController anterior, Boveda editada, 
      Llave selectedLlave, List<Llave> llaves, Client cl, ArrayList<Boveda> bovedas) {
    this.cliente = cl;
    this.anterior = anterior;
    this.editada = editada;
    this.selectedLlave = selectedLlave;
    this.llaves = llaves;
    this.bovedas = bovedas;
    cargarInformacion(this.selectedLlave);
  }

  /**
   * metodo para cargar en los campos de texto la informacion de la llave
   * @param selectedLlave 
   */
  private void cargarInformacion(Llave selectedLlave) {
    txtNombreLlave.setText(selectedLlave.getNombre());
    txtUrl.setText(selectedLlave.getUrl());
    txtUsuario.setText(selectedLlave.getUsername());
    txtPassword.setText(selectedLlave.getPassword());
  }
  
}
