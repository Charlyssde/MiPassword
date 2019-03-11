/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author texch
 */
public class BovedasListController implements Initializable {

  @FXML
  private AnchorPane anchorPane;
  @FXML
  private BorderPane borderPane;
  @FXML
  private SplitPane splitPane;
  @FXML
  private ListView<String> listaBovedas;
  @FXML
  private Label lblUsername;
  @FXML
  private Button btnNuevaBoveda;
  @FXML
  private Button btnRegresar;
  @FXML
  private Button btnNuevaLlave;
  @FXML
  private Button btnEditar;
  @FXML
  private Button btnEliminar;
  @FXML
  private TableView<String> tblLlaves;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    listaBovedas.getItems().addAll("Redes Sociales", "Cuentas Bancarias", "Tiendas OnLine");
    tblLlaves.getItems().addAll("");
  }  

  @FXML
  private void salir(MouseEvent event) {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void editarElemento(MouseEvent event) {
  }

  @FXML
  private void eliminarElemento(MouseEvent event) {
  }

  @FXML
  private void agregarBoveda(MouseEvent event) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/view/agregarBoveda.fxml"));
      Scene scene = new Scene(root);
      Stage primaryStage = new Stage();
      primaryStage.setTitle("Agregar Boveda");
      primaryStage.setScene(scene);
      primaryStage.initModality(Modality.APPLICATION_MODAL);
      primaryStage.show();
    } catch (IOException ex) {
      Logger.getLogger(MiPassword.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
}
