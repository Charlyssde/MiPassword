/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import clases.Boveda;
import clases.Llave;
import clases.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AlertMessage;

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
  private ListView<String> listaBovedas;
  @FXML
  private TableView<Llave> tblLlaves;
    @FXML
  private TableColumn<Llave, String> colUrl;
  @FXML
  private TableColumn<Llave, String> colUsername;
  @FXML
  private TableColumn<Llave, String> colPassword;
  @FXML
  private Label lblUsername;
  @FXML
  private Button btnNuevaBoveda;
  @FXML
  private Button btnRegresar;
  @FXML
  private Button btnNuevaLlave;
  @FXML
  private Button btnEditarBoveda;
  @FXML
  private Button btnEliminarBoveda;
  @FXML
  private Button btnEditarLlave;
  @FXML
  private Button btnEliminarLlave;
    
  private ArrayList<Boveda> bovedas;
  
  private ArrayList<Llave> llaves;
  
  private String selectedLlave;
  
  private String selectedBoveda;
  
  private Usuario owner;


  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    cargarBovedas();
    cargarColumnas();
    cargarLlaves();
    listaBovedas.getItems().add(bovedas.get(0).toString());
    selectedInLlaves();
    selectedInBovedas();
  }  

  @FXML
  private void salir(MouseEvent event) {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
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
  
  private void selectedInLlaves() {
    tblLlaves.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (tblLlaves.getSelectionModel().getSelectedItem() != null) {
          TableView.TableViewSelectionModel selectionModel = tblLlaves.getSelectionModel();
          selectedLlave = (String) selectionModel.getSelectedItem();
          btnEditarLlave.setDisable(false);
          btnEliminarLlave.setDisable(false);
        } else {
          btnEditarLlave.setDisable(true);
          btnEliminarLlave.setDisable(true);
        }
      }

    });
  }
  
  private void selectedInBovedas(){
     listaBovedas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (listaBovedas.getSelectionModel().getSelectedItem() != null) {
          SelectionModel selectionModel = listaBovedas.getSelectionModel();
          selectedBoveda = (String) selectionModel.getSelectedItem();
          btnEditarBoveda.setDisable(false);
          btnEliminarBoveda.setDisable(false);
        } else {
          btnEditarBoveda.setDisable(true);
          btnEliminarBoveda.setDisable(true);
        }
      }

    });
  }

  @FXML
  private void editarLlave(MouseEvent event) {
  }

  @FXML
  private void eliminarLlave(MouseEvent event) {
  }

  @FXML
  private void editarBoveda(MouseEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/editarBoveda.fxml"));
      loader.load();
      AnchorPane edit = loader.getRoot();
      EditarBovedaController editBoveda = loader.getController();
      editBoveda.cargarBoveda(selectedBoveda);
      Scene scene = new Scene(edit);
      Stage primaryStage = new Stage();
      primaryStage.setTitle("Editar Boveda");
      primaryStage.setScene(scene);
      primaryStage.initModality(Modality.APPLICATION_MODAL);
      primaryStage.show();
    } catch (IOException ex) {
      Logger.getLogger(MiPassword.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.btnEditarBoveda.setDisable(true);
    this.btnEliminarBoveda.setDisable(true);
  }

  @FXML
  private void eliminarBoveda(MouseEvent event) {
    AlertMessage.mensaje("Eliminado correctamente de la base de datos");
  }
  
  public void cargarUsuario(Usuario user){
    this.lblUsername.setText(user.getUsername());
    
  }
  
  public void cargarBovedas(){
    bovedas = new ArrayList<>();
    Boveda boveda = new Boveda("Redes Sociales",this.owner);
    boveda.cargarLlaves();
    bovedas.add(boveda);
    System.out.println(bovedas.get(0).toString());
    
  }
  
  public void cargarLlaves(){
    llaves = new ArrayList<>();
    llaves.addAll(bovedas.get(0).getLlaves());
    System.out.println(bovedas.get(0).getLlaves().get(0));
    tblLlaves.getItems().addAll(llaves);
    
  }

  public void cargarColumnas(){
    colUrl.setCellValueFactory(
        new PropertyValueFactory<Llave, String>("url"));
    colUsername.setCellValueFactory(
        new PropertyValueFactory<Llave, String>("username"));
    colPassword.setCellValueFactory(
        new PropertyValueFactory<Llave, String>("password"));
    
  }
  @FXML
  private void nuevaLlave(MouseEvent event) {
  }
}
