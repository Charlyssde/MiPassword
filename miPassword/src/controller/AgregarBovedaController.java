/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
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
 * FXML Controller class
 *
 * @author texch
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

  @FXML
  private void agregarBoveda(MouseEvent event) throws RemoteException {
    Random rd = new Random();
    if (validarNombre()) {
      Boveda nueva = new Boveda(txtNombre.getText(), user,rd.nextInt());
      cliente.server.agregarBoveda(nueva);
      bovedas.add(nueva);
      anterior.actualizarListaBovedas(this.bovedas);
      cerrarVentana();
      AlertMessage.mensaje("Se ha guardado la boveda de forma exitosa");
    }
  }

  @FXML
  private void cancelar(MouseEvent event) {
    cerrarVentana();
  }

  private void cerrarVentana() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  public void cargarObjetos(BovedasListController anterior, ArrayList<Boveda> listaBovedas, Usuario owner, Client c) {
    this.cliente = c;
    this.anterior = anterior;
    this.bovedas = listaBovedas;
    this.user = owner;
  }

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

  private boolean existeYa() {

    for (Boveda b : bovedas) {
      if (b.getNombre().equals(txtNombre.getText())) {
        return true;
      }
    }

    return false;
  }

}
