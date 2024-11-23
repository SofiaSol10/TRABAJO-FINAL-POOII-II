/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Denuncia;
import Models.DistritosModelo;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ComboBox<String> CB_FILTRARTIPODENUNCIA;

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
  
    
    //SECCION DETALLES DENUNCIA
     @FXML
    private ImageView IV_FOTO;

    @FXML
    private Label LB_DISTRITO;

    @FXML
    private Label LB_TIPODENUNCIA;

    @FXML
    private Label LB_FECHA;

    @FXML
    private Label LB_HORA;

    @FXML
    private Label LB_NOMBRE;

    @FXML
    private TextArea TA_DESC_DENUN;

    @FXML
    private TextArea TA_DESC_LUGAR;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DistritosModelo M_distritos = new DistritosModelo();
        M_distritos.leerDistritos();
        CB_FILTRADISTRITO.getItems().addAll(M_distritos.getNombresDistritos());
        CB_FILTRARTIPODENUNCIA.getItems().addAll("Robo","Extorsión","Asesinato","Violencia Doméstica","Feminicidio","Acoso Sexual","Corrupción");
        mostrarDenunciasTabla();
    }  
    
    public void mostrarDenunciasTabla(){
        DenunciaRepository denRepository = new DenunciaRepository();
        DenunciaService denService = new DenunciaService(denRepository);
        List<Denuncia> listaDenuncias = denService.obtenerTodasLasDenuncias();
       
        
        VTABLEDENUNCIAS.getItems().addAll(listaDenuncias);

        // Configurar el cellValueFactory para cada columna
        TipoDenunTC.setCellValueFactory(new PropertyValueFactory<>("tipoDenu"));
        distritoTC.setCellValueFactory(new PropertyValueFactory<>("distrito")); 
        fechaTC.setCellValueFactory(new PropertyValueFactory<>("fecha")); 
        usuarioTC.setCellValueFactory(new PropertyValueFactory<>("nombre")); 
        horaTC.setCellValueFactory(new PropertyValueFactory<>("hora")); 
    }
    
    @FXML
    void mostrarDetallesDenuncia(ActionEvent event) {
        limpiarDetalles();
        Denuncia denunciaSeleccionada = VTABLEDENUNCIAS.getSelectionModel().getSelectedItem();
        if (denunciaSeleccionada != null) {
           
            LB_NOMBRE.setText(denunciaSeleccionada.getNombre());
            LB_FECHA.setText(denunciaSeleccionada.getFecha().toString());
            LB_HORA.setText(denunciaSeleccionada.getHora().toString());
            LB_DISTRITO.setText(denunciaSeleccionada.getDistrito());
            LB_TIPODENUNCIA.setText(denunciaSeleccionada.getTipoDenu());
            if (denunciaSeleccionada.getFoto() != null) {
                String rutaImagen = denunciaSeleccionada.getFoto();

                // Reemplazar las barras invertidas por barras diagonales ( se guardo en la base de datos con el sentido contrario al que acepta el imageView
                String rutaAdaptada = rutaImagen.replace("\\" , "/");

                // Usar la ruta adaptada para cargar la imagen
                Image image = new Image("file:"+ rutaAdaptada);
                
                IV_FOTO.setImage(image);
            }
            TA_DESC_LUGAR.setText(denunciaSeleccionada.getLugarDesc());
            TA_DESC_DENUN.setText(denunciaSeleccionada.getDenuDesc());
  
        } else {
        // Mostrar un mensaje si no hay fila seleccionada
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText("No se ha seleccionado ninguna denuncia.");
        alert.showAndWait();
        }
        

    }
    
    void limpiarDetalles(){
            LB_NOMBRE.setText("---");
            LB_FECHA.setText("---");
            LB_HORA.setText("---");
            LB_DISTRITO.setText("---");
            LB_TIPODENUNCIA.setText("---");
            IV_FOTO.setImage(null);
            TA_DESC_LUGAR.clear();
            TA_DESC_DENUN.clear();
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
    private void mostrarAlertaError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error de Validación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait(); // Bloquea hasta que el usuario cierre la alerta
       }
    
    
    
}
