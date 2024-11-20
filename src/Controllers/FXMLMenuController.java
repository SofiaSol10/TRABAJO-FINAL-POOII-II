/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Sofía
 */
public class FXMLMenuController implements Initializable {
    
    @FXML
    private Label label;
   
    
    /*
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }*/
    @FXML private void mostrarNoticiasRecientes(ActionEvent event){
        FXMLNoticiasController C_Noticias = new FXMLNoticiasController();
        C_Noticias.MostrarVista();
    }
    
    @FXML private void mostrarRegistrarDenuncias(ActionEvent event){
        FXMLRegistrarDenunciaController C_RDenuncia = new FXMLRegistrarDenunciaController();
        C_RDenuncia.MostrarVista();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
}
