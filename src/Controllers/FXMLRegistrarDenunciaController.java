/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sofía
 */
public class FXMLRegistrarDenunciaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private RadioButton RB_SI;
    
    @FXML
    private RadioButton RB_NO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void MostrarVista(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FXMLRegistrarDenunciaVista.fxml"));//cargando la vista
            Parent root = loader.load();
            
            // Crear una nueva ventana o cambiar la actual
            Stage stage = new Stage(); // O usa `((Stage) botón.getScene().getWindow())` para la actual
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
