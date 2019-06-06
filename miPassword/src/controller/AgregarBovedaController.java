/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mipasswordinterface.Boveda;
import mipasswordinterface.Usuario;
import model.AlertMessage;
//import model.Boveda;
//import model.Usuario;

/**
 * Clase controller encargada de agregar una boveda
 *
 * @author Carlos Carrillo
 */
public class AgregarBovedaController implements Initializable {

  @FXML
  private TextField txtNombre;
  @FXML
  private Button btnAceptar;
  @FXML
  private Button btnGuardar;
  @FXML
  private AnchorPane anchorPane;

  private Usuario user;

  private BovedasListController anterior;

  private ArrayList<Boveda> bovedas;
  
  private Client cliente;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  /**
   * evento que agrega una boveda
   * @param event
   * @throws RemoteException 
   */
  @FXML
  private void agregarBoveda(MouseEvent event) throws RemoteException {
    
    if (validarNombre()) {
      Boveda nueva = new Boveda(txtNombre.getText(), user);
      cliente.server.agregarBoveda(nueva);
      anterior.actualizarListaBovedas();
      cerrarVentana();
      AlertMessage.mensaje("Se ha guardado la boveda de forma exitosa");
    }
  }

  /**
   * evento que llama al metodo para cerrar la ventana
   * @param event 
   */
  @FXML
  private void cancelar(MouseEvent event) {
    cerrarVentana();
  }

  /**
   * metodo para cerrar la ventana
   */
  private void cerrarVentana() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  /**
   * metodo que carga los objetos necesarios para crear la boveda
   * @param anterior pantalla anterior
   * @param listaBovedas lista de las bovedas
   * @param owner dueño de las bovedas
   * @param c cliente conectado al servidor
   */
  public void cargarObjetos(BovedasListController anterior, ArrayList<Boveda> listaBovedas, Usuario owner, Client c) {
    this.cliente = c;
    this.anterior = anterior;
    this.bovedas = listaBovedas;
    this.user = owner;
  }

  
  /**
   * metodo que valida que el nombre no exista o este vacio
   * @return true si no esta repetido o esta vacio
   */
  private boolean validarNombre() {

    if (txtNombre.getText().isEmpty()) {
      AlertMessage.mensaje("Error: No puede quedar vacío el campo de texto");
      return false;
    } else if (existeYa()) {
      AlertMessage.mensaje("Error: El nombre ya existe actualmente");
      return false;
    }

    return true;
  }

  /**
   * metodo para verificar que no exista una boveda con tal nombre
   * @return trye si existe, false si no
   */
  private boolean existeYa() {

    for (Boveda b : bovedas) {
      if (b.getNombre().equals(txtNombre.getText())) {
        return true;
      }
    }

    return false;
  }

}
