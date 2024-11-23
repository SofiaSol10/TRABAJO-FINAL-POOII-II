/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Denuncia;
import Repositories.DenunciaRepository;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*; // modelos de componentes
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.DenunciaService;

/**
 * FXML Controller class
 *
 * @author Sofía
 */
public class FXMLVerDenunciasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Denuncia> VTABLEDENUNCIAS;
    @FXML
    private DatePicker CB_FILTRARFECHA;
    @FXML
    private ComboBox<String> CB_FILTRADISTRITO;
    @FXML
    private Button BTN_FILTRARDENUNCIAS;
    @FXML
    private Button BTN_MOSTRARDETALLES;
   @FXML
    private TableColumn<Denuncia, String> TipoDenunTC;
   @FXML
    private TableColumn<Denuncia, String> distritoTC;
    @FXML
    private TableColumn<Denuncia, LocalDate> fechaTC;
    @FXML
    private TableColumn<Denuncia, String> usuarioTC;
    @FXML
    private TableColumn<Denuncia, LocalTime> horaTC;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDenunciasTabla();
    }  
    
    public void mostrarDenunciasTabla(){
        DenunciaRepository denRepository = new DenunciaRepository();
        DenunciaService denService = new DenunciaService(denRepository);
        List<Denuncia> listaDenuncias = denService.obtenerTodasLasDenuncias();
       
        
        // Convertir la lista de denuncias a un ObservableList
        ObservableList<Denuncia> denuncias = FXCollections.observableArrayList(listaDenuncias);
        
        // Asignar el ObservableList a la TableView
        VTABLEDENUNCIAS.setItems(denuncias);

        // Configurar el cellValueFactory para cada columna
        TipoDenunTC.setCellValueFactory(new PropertyValueFactory<>("tipoDenu")); // Asegúrate de que 'tipoDenu' es el nombre correcto de la propiedad
        distritoTC.setCellValueFactory(new PropertyValueFactory<>("distrito")); // Asegúrate de que 'distrito' es el nombre correcto de la propiedad
        fechaTC.setCellValueFactory(new PropertyValueFactory<>("fecha")); // Asegúrate de que 'fecha' es el nombre correcto de la propiedad
        usuarioTC.setCellValueFactory(new PropertyValueFactory<>("nombre")); // Asegúrate de que 'nombre' es el nombre correcto de la propiedad
        horaTC.setCellValueFactory(new PropertyValueFactory<>("hora")); // Asegúrate de que 'hora' es el nombre correcto de la propiedad
    }
    
    @FXML
    void mostrarDetallesDenuncia(ActionEvent event) {
        

    }
    
    
    @FXML
    void FILTRAR(ActionEvent event) {
        

    }
    
    public void MostrarVista(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FXMLVerDenunciasVista.fxml"));//cargando la vista
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
