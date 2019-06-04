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
import model.AlertMessage;

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

  private ArrayList<Boveda> bovedas;

  private Boveda editar;

  private BovedasListController anterior;
  private Client cliente;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  public void cargarBoveda(ArrayList<Boveda> listaNueva, Boveda nueva, BovedasListController anterior, Client c) {
    this.cliente = c;
    editar = nueva;
    txtNombre.setText(editar.getNombre());
    this.bovedas = listaNueva;
    this.anterior = anterior;
  }

  @FXML
  private void editarBoveda(MouseEvent event) throws RemoteException {
    if (validarNombre()) {
      for (Boveda b : bovedas) {
        if (b.getNombre().equals(editar.getNombre())) {
          b.setNombre(txtNombre.getText());
          cliente.server.editarBoveda(b);
          anterior.actualizarListaBovedas(bovedas);
          cerrar();
          AlertMessage.mensaje("Se ha actualizado con exito");
        }
      }
    }
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

  @FXML
  private void cancelar(MouseEvent event) {
    cerrar();
  }

  public final void cerrar() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
}
