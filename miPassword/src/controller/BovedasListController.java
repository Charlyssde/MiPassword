/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

//import model.Boveda;
//import model.Llave;
//import model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
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
import mipasswordinterface.Boveda;
import mipasswordinterface.Llave;
import mipasswordinterface.Usuario;
import model.AlertMessage;

/**
 * Clase controller que permite ver todas las llaves y bovedas del usuario
 *
 * @author Carlos Carrillo
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
  
  private Client cliente;
  @FXML
  private Button btnUsuario;
  

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

  
  /**
   * evento que carga la pantalla del login y cierra la sesión del usuario
   * @param event 
   */
  @FXML
  private void salir(MouseEvent event) {
    
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/LogIn.fxml"));
      Parent root = loader.load();
      LogInController display = loader.getController();
      display.CargarCliente(cliente);
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

  /**
   * evento que incializa la pantalla para el registro de una nueva boveda
   * @param event 
   */
  @FXML
  private void agregarBoveda(MouseEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/agregarBoveda.fxml"));
      loader.load();
      AnchorPane root = loader.getRoot();
      AgregarBovedaController ventana = loader.getController();
      ventana.cargarObjetos(this, this.bovedas, this.owner, cliente);
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

  
  /**
   * evento que inicializa la pantalla para editar una boveda
   * @param event 
   */
  @FXML
  private void editarBoveda(MouseEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/editarBoveda.fxml"));
      loader.load();
      AnchorPane edit = loader.getRoot();
      EditarBovedaController editBoveda = loader.getController();
      editBoveda.cargarBoveda(bovedas, editada, this, cliente);
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

  /**
   * evento encargado de eliminar una boveda siempre y cuando no contenga llaves
   * @param event
   * @throws RemoteException 
   */
  @FXML
  private void eliminarBoveda(MouseEvent event) throws RemoteException {
    Optional<ButtonType> result = AlertMessage.confirmacion("¿Deseas realmente eliminar la bóveda?");
    if (result.get() == ButtonType.OK) {
      btnEliminarBoveda.setDisable(true);
      btnEditarBoveda.setDisable(true);
      btnNuevaLlave.setDisable(true);
      for (Boveda b : bovedas) {
        if (b.equals(editada)) {
          if(!cliente.server.getAllLlaves(b).isEmpty()){
            AlertMessage.mensaje("No se puede eliminar una bóveda que aún contenga llaves");
            break;
          } else {
            cliente.server.eliminarBoveda(b);
            actualizarListaBovedas();
            AlertMessage.mensaje("Eliminado correctamente");
            break;
          }
        }
      }
    }

  }

  /**
   * evento que inicializa la pantalla para agregar una llave
   * @param event
   * @throws IOException 
   */
  @FXML
  private void nuevaLlave(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/agregarLlave.fxml"));
    loader.load();
    AnchorPane root = loader.getRoot();
    AgregarLlaveController ventana = loader.getController();
    ventana.cargarDatos(this, this.editada, this.bovedas, cliente);
    Scene scene = new Scene(root);
    Stage primaryStage = new Stage();
    primaryStage.setTitle("Agregar Llave");
    primaryStage.setScene(scene);
    primaryStage.initModality(Modality.APPLICATION_MODAL);
    primaryStage.show();
    btnEditarLlave.setDisable(true);
    btnEliminarLlave.setDisable(true);
    btnNuevaLlave.setDisable(true);
  }

  /**
   * evento que inicializa la pantalla para editar una llave
   * @param event
   * @throws IOException 
   */
  @FXML
  private void editarLlave(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/editarLlave.fxml"));
    loader.load();
    AnchorPane root = loader.getRoot();
    EditarLlaveController ventana = loader.getController();
    ventana.cargarDatos(this, this.editada, this.selectedLlave, this.llaves, cliente,this.bovedas);
    Scene scene = new Scene(root);
    Stage primaryStage = new Stage();
    primaryStage.setTitle("Editar Llave");
    primaryStage.setScene(scene);
    primaryStage.initModality(Modality.APPLICATION_MODAL);
    primaryStage.show();
    btnEditarLlave.setDisable(true);
    btnEliminarLlave.setDisable(true);
    btnNuevaLlave.setDisable(true);
  }

  /**
   * evento que se encarga de eliminar una llave seleccionada
   * @param event
   * @throws RemoteException 
   */
  @FXML
  private void eliminarLlave(MouseEvent event) throws RemoteException {
    Optional<ButtonType> result = AlertMessage.confirmacion("¿Deseas realmente eliminar la llave?");
    if (result.get() == ButtonType.OK) {
      for (Llave del : llaves) {
        if (del.equals(selectedLlave)) {
          cliente.server.eliminarLlave(del);
          editada = cliente.server.getBoveda(editada);
          actualizarTablaLlaves(editada, cliente);
          actualizarListaBovedas();
          AlertMessage.mensaje("Llave eliminada satisfactoriamente");
        }
      }
      btnEditarLlave.setDisable(true);
      btnEliminarLlave.setDisable(true);
      btnNuevaLlave.setDisable(true);
    }
  }

  /**
   * metodo para cargar el usuario que inicio sesión
   * @param user usuario iniciado
   * @param c cliente conectado al servidor
   * @throws RemoteException 
   */
  public void cargarUsuario(Usuario user, Client c) throws RemoteException {
     this.cliente = c;
    this.owner = user;
    this.lblUsername.setText(user.getNombre());
    cargarBovedas();
  }
  
  /**
   * metodo para cargar las bovedas que le corresponden al usuario
   * @throws RemoteException 
   */
  public void cargarBovedas() throws RemoteException {
    bovedas = cliente.server.getAllBovedas(owner.getUsername());
    System.out.println(bovedas.size());
    for(Boveda b : bovedas){
      listaBovedas.getItems().add(b.toString());
    }
  }

  
  /**
   * metodo que actualiza la tabla de las llaves de acuerdo a una boveda
   * @param nueva boveda dueña de las llaves
   * @param cl cliente conectado al servidor
   * @throws RemoteException 
   */
  public void actualizarTablaLlaves(Boveda nueva, Client cl) throws RemoteException {
    this.cliente = cl;
    tblLlaves.getItems().clear();
    llaves = cliente.server.getAllLlaves(nueva);
    tblLlaves.getItems().addAll(llaves);
    
  }

  /**
   * metodo que actualiza la lista de bovedas del usuario
   * @throws RemoteException 
   */
  public void actualizarListaBovedas() throws RemoteException {
    listaBovedas.getItems().clear();
    this.bovedas = cliente.server.getAllBovedas(owner.getUsername());
    ArrayList<String> nombres = new ArrayList<>();
    for (Boveda b : bovedas ) {
      nombres.add(b.getNombre());
    }
    selectedInBovedas();
    listaBovedas.getItems().addAll(nombres);
  }

  /**
   * metodo que les da el valor a las columnas de la tabla de llaves
   */
  private void cargarColumnasLlave() {
    colUrl.setCellValueFactory(
        new PropertyValueFactory<>("url"));
    colUsername.setCellValueFactory(
        new PropertyValueFactory<>("username"));
    colPassword.setCellValueFactory(
        new PropertyValueFactory<>("password"));
    colNombre.setCellValueFactory(
        new PropertyValueFactory<>("nombre"));

  }

  /**
   * metodo que establece el comportamiento al seleccionar un elemento de las llaves
   */
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

  /**
   * metodo que establece el comportamiento al seleccionar un elemento de las bovedas
   */
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
              try {
                llaves = cliente.server.getAllLlaves(editada);
                tblLlaves.getItems().addAll(llaves);
              } catch (RemoteException ex) {
                Logger.getLogger(BovedasListController.class.getName()).log(Level.SEVERE, null, ex);
              }
              
              btnNuevaLlave.setDisable(false);
            btnEditarBoveda.setDisable(false);
            btnEliminarBoveda.setDisable(false);
              break;
            }
          }
          
        } else {
          btnEditarBoveda.setDisable(true);
          btnEliminarBoveda.setDisable(true);
          btnNuevaLlave.setDisable(true);
        }
      }

    });
  }

  /**
   * evento que inicializa la pantalla para editar los datos del usuario
   * @param event
   * @throws IOException 
   */
  @FXML
  private void editarDatosUsuario(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/editarUsuario.fxml"));
    loader.load();
    AnchorPane root = loader.getRoot();
    EditarUsuarioController ventana = loader.getController();
    ventana.cargarDatos(owner,cliente,this);
    Scene scene = new Scene(root);
    Stage primaryStage = new Stage();
    primaryStage.setTitle("Editar Usuario");
    primaryStage.setScene(scene);
    primaryStage.initModality(Modality.APPLICATION_MODAL);
    primaryStage.show();
    btnEditarLlave.setDisable(true);
    btnEliminarLlave.setDisable(true);
    
  }

  /**
   * metodo para establecer el nombre del usuario en la etiqueta
   * @param usuario usuario que inició sesión
   */
  void actualizarUsuario(Usuario usuario) {
    lblUsername.setText(usuario.getNombre());
    
  }
}
