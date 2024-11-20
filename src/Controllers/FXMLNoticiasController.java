/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.NoticiasModelo;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sofía
 */
public class FXMLNoticiasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private NoticiasModelo M_Noticias;
    @FXML
    private WebView webView;
    @FXML
    private ListView<String> listaTitulos;
    @FXML
    private WebEngine engine;
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        engine = webView.getEngine();
        mostrarTNoticias();
        
    }

   public void mostrarTNoticias(){
       M_Noticias = new NoticiasModelo();
       M_Noticias.leerNoticia(); //leer TXT de Noticias
        List<String> listatitulos = M_Noticias.getTitulos();
        listaTitulos.getItems().addAll(listatitulos);

        // Listener para cambiar el contenido del WebView cuando se seleccione un título
        listaTitulos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Si hay un título seleccionado, cargar su link en el WebView
            if (newValue != null) {
                int index = listaTitulos.getSelectionModel().getSelectedIndex();
                String LinkSeleccionado = M_Noticias.getLinks().get(index);
                engine.load(LinkSeleccionado);
            }
        });

       
   }    
    
    public void MostrarVista(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FXMLNoticiasVista.fxml"));//cargando la vista
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
