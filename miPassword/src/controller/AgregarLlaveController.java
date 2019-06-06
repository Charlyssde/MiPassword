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
 * Clase controller encargada de agregar una nueva llave
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
  private Client cliente;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

 
  /**
   * evento que guarda una nueva llave
   * @param event
   * @throws RemoteException 
   */
  @FXML
  private void guardarLlave(MouseEvent event) throws RemoteException {
    
    if(validarDatos()){
      Llave nueva = new Llave(txtNombreLlave.getText(),txtUrl.getText(), txtUsuario.getText(), 
          txtPassword.getText(), owner);
          cliente.server.agregarLlave(nueva);
          anterior.actualizarListaBovedas();
          anterior.actualizarTablaLlaves(owner, cliente);
          
          cerrar();
          AlertMessage.mensaje("Se ha guardado exitosamente la nueva llave");
    }
  }

  /**
   * evento que cierra la ventana
   * @param event 
   */
  @FXML
  private void cancelar(MouseEvent event) {
    cerrar();
  }
   
  
  /**
   * metodo que devuelve si hay o no campos vacios
   * @return true si no hay campos vacios, false si hay campos vacios
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
   * metodo para cargar los datos necesarios para la adición de la boveda
   * @param anterior pantalla anterior
   * @param owner dueño de la boveda
   * @param nuevaLista lista con las bovedas actuales
   * @param cl  cliente conectado al servidor
   */
  public void cargarDatos(BovedasListController anterior,Boveda owner, ArrayList<Boveda> nuevaLista, Client cl){
    this.cliente = cl;
    this.nuevaLista = nuevaLista;
    this.anterior = anterior;
    this.owner = owner;  
  }
  
  /**
   * metodo para cerrar la ventana actual
   */
  private void cerrar(){
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
}
