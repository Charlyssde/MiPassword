/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Boveda;
import model.Llave;
import model.Usuario;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
  private TableColumn<Llave, String> colNombre;
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

  private Boveda editada;

  private List<Llave> llaves;

  private Llave selectedLlave;

  private String selectedBoveda;

  private Usuario owner;
  

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    bovedas = new ArrayList<>();
    cargarColumnasLlave();
    selectedInLlaves();
    selectedInBovedas();
  }

  @FXML
  private void salir(MouseEvent event) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
      Scene scene = new Scene(root);
      Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
      primaryStage.setTitle("LogIn");
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.show();
    } catch (IOException ex) {
      Logger.getLogger(MiPassword.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void agregarBoveda(MouseEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/agregarBoveda.fxml"));
      loader.load();
      AnchorPane root = loader.getRoot();
      AgregarBovedaController ventana = loader.getController();
      ventana.cargarObjetos(this, this.bovedas, this.owner);
      Scene scene = new Scene(root);
      Stage primaryStage = new Stage();
      primaryStage.setTitle("Agregar Boveda");
      primaryStage.setScene(scene);
      primaryStage.initModality(Modality.APPLICATION_MODAL);
      primaryStage.show();
    } catch (IOException ex) {
      Logger.getLogger(MiPassword.class.getName()).log(Level.SEVERE, null, ex);
    }
    btnEliminarBoveda.setDisable(true);
    btnEditarBoveda.setDisable(true);
  }

  @FXML
  private void editarBoveda(MouseEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/editarBoveda.fxml"));
      loader.load();
      AnchorPane edit = loader.getRoot();
      EditarBovedaController editBoveda = loader.getController();
      editBoveda.cargarBoveda(bovedas, editada, this);
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
    Optional<ButtonType> result = AlertMessage.confirmacion("¿Deseas realmente eliminar la llave?");
    if (result.get() == ButtonType.OK) {
      btnEliminarBoveda.setDisable(true);
      btnEditarBoveda.setDisable(true);
      for (Boveda b : bovedas) {
        if (b.equals(editada)) {
          if(!b.getLlaves().isEmpty()){
            AlertMessage.mensaje("No se puede eliminar una bóveda que aún contenga llaves");
            break;
          } else {
            bovedas.remove(b);
            actualizarListaBovedas(bovedas);
            AlertMessage.mensaje("Eliminado correctamente");
            break;
          }
        }
      }
    }

  }

  /**
   *
   * @param id el identificador que se desea buscar
   * @return usuario el usuario correspondiente al id
   */

  @FXML
  private void nuevaLlave(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/agregarLlave.fxml"));
    loader.load();
    AnchorPane root = loader.getRoot();
    AgregarLlaveController ventana = loader.getController();
    ventana.cargarDatos(this, this.editada, this.bovedas);
    Scene scene = new Scene(root);
    Stage primaryStage = new Stage();
    primaryStage.setTitle("Agregar Llave");
    primaryStage.setScene(scene);
    primaryStage.initModality(Modality.APPLICATION_MODAL);
    primaryStage.show();
    btnEditarLlave.setDisable(true);
    btnEliminarLlave.setDisable(true);
  }

  @FXML
  private void editarLlave(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/editarLlave.fxml"));
    loader.load();
    AnchorPane root = loader.getRoot();
    EditarLlaveController ventana = loader.getController();
    ventana.cargarDatos(this, this.editada, this.selectedLlave, this.llaves);
    Scene scene = new Scene(root);
    Stage primaryStage = new Stage();
    primaryStage.setTitle("Editar Llave");
    primaryStage.setScene(scene);
    primaryStage.initModality(Modality.APPLICATION_MODAL);
    primaryStage.show();
    btnEditarLlave.setDisable(true);
    btnEliminarLlave.setDisable(true);
  }

  @FXML
  private void eliminarLlave(MouseEvent event) {
    Optional<ButtonType> result = AlertMessage.confirmacion("¿Deseas realmente eliminar la llave?");
    if (result.get() == ButtonType.OK) {
      for (Llave del : llaves) {
        if (del.equals(selectedLlave)) {
          llaves.remove(del);
          actualizarTablaLlaves(editada);
          actualizarListaBovedas(bovedas);
          AlertMessage.mensaje("Llave eliminada satisfactoriamente");
          break;
        }
      }
      btnEditarLlave.setDisable(true);
      btnEliminarLlave.setDisable(true);
    }
  }

  public void cargarUsuario(Usuario user) {
    this.owner = user;
    this.lblUsername.setText(user.getCorreo());
    cargarBovedas();
  }
  public void cargarBovedas() {
    bovedas = owner.getBovedas();
    for(Boveda b : bovedas){
      listaBovedas.getItems().add(b.toString());
    }
  }

  public void actualizarTablaLlaves(Boveda nueva) {
    tblLlaves.getItems().clear();
    llaves = nueva.getLlaves();
    tblLlaves.getItems().addAll(llaves);
  }

  public void actualizarListaBovedas(ArrayList<Boveda> nuevaLista) {
    listaBovedas.getItems().clear();
    ArrayList<String> nombres = new ArrayList<>();
    for (Boveda b : nuevaLista) {
      nombres.add(b.getNombre());
    }
    listaBovedas.getItems().addAll(nombres);
  }

  public void cargarColumnasLlave() {
    colUrl.setCellValueFactory(
        new PropertyValueFactory<>("url"));
    colUsername.setCellValueFactory(
        new PropertyValueFactory<>("username"));
    colPassword.setCellValueFactory(
        new PropertyValueFactory<>("password"));
    colNombre.setCellValueFactory(
        new PropertyValueFactory<>("nombre"));

  }

  private void selectedInLlaves() {
    tblLlaves.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (tblLlaves.getSelectionModel().getSelectedItem() != null) {
          TableView.TableViewSelectionModel selectionModel = tblLlaves.getSelectionModel();
          selectedLlave = (Llave) selectionModel.getSelectedItem();
          System.out.println(selectedLlave.toString());
          btnEditarLlave.setDisable(false);
          btnEliminarLlave.setDisable(false);
        } else {
          btnEditarLlave.setDisable(true);
          btnEliminarLlave.setDisable(true);
        }
      }

    });
  }

  private void selectedInBovedas() {
    listaBovedas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (listaBovedas.getSelectionModel().getSelectedItem() != null) {
          SelectionModel selectionModel = listaBovedas.getSelectionModel();
          selectedBoveda = (String) selectionModel.getSelectedItem();
          for (Boveda b : bovedas) {
            if (selectedBoveda.equals(b.getNombre())) {
              editada = b;
              tblLlaves.getItems().clear();
              llaves = editada.getLlaves();
              tblLlaves.getItems().addAll(llaves);
              break;
            }
          }
          btnNuevaLlave.setDisable(false);
          btnEditarBoveda.setDisable(false);
          btnEliminarBoveda.setDisable(false);
        } else {
          btnEditarBoveda.setDisable(true);
          btnEliminarBoveda.setDisable(true);
        }
      }

    });
  }
}
