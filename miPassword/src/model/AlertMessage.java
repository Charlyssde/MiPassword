/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 *
 * @author texch
 */
public class AlertMessage {
  
  private AlertMessage(){
    
  }

  public static void mensaje(String mensaje) {
    Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
    dialogo.setTitle("Aviso");
    dialogo.setHeaderText("INFORMACIÓN");
    dialogo.setContentText(mensaje);
    dialogo.initStyle(StageStyle.UTILITY);
    dialogo.showAndWait();
  }
  
  public static Optional<ButtonType> confirmacion(String mensaje) {
    Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
    dialogo.setTitle("Confirmación");
    dialogo.setHeaderText("Confirmar Eliminación");
    dialogo.setContentText(mensaje);
    dialogo.initStyle(StageStyle.UTILITY);
    return dialogo.showAndWait();
  }
}
