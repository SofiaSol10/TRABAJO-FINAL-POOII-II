/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Denuncia;
import Models.DistritosModelo;
import Repositories.DenunciaRepository;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import service.DenunciaService;

/**
 * FXML Controller class
 *
 * @author Sofía
 */
public class FXMLRegistrarDenunciaController implements Initializable{

    /**
     * Initializes the controller class.
     */
    @FXML
    private RadioButton RB_SI;
    @FXML
    private RadioButton RB_NO;
    @FXML
    private DatePicker TXT_FECHA;
    @FXML
    private TextField TXT_HORA;
    @FXML
    private ComboBox<String> CB_DISTRITO;
    @FXML
    private TextArea TF_DESC_LUGAR;
    @FXML
    private Button BTN_CARGARFOTO;
    @FXML
    private TextField TXT_NOMBRE;
    @FXML
    private ComboBox<String> CB_TIPO_DEN;
    @FXML
    private TextArea TF_DESC_DEN;
    @FXML
    private ToggleGroup Anonimato;//grupo de radio buttons
    
    private String rutaFoto = null;//nos ayuda a obtener la direccion de la imagen almacenada
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DistritosModelo M_distritos = new DistritosModelo();
        M_distritos.leerDistritos();
        CB_DISTRITO.getItems().addAll(M_distritos.getNombresDistritos());
        CB_TIPO_DEN.getItems().addAll("Robo","Extorsión","Asesinato","Violencia Doméstica","Feminicidio","Acoso Sexual","Corrupción");
        borrarCampos();
    }
    
    @FXML
    public void cargarFoto(ActionEvent event){ //detecta boton cargar Foto
        // Crear un FileChooser
        FileChooser fileChooser = new FileChooser();

        // Configurar las extensiones permitidas
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.jpeg", "*.png")
        );

        // Abrir el cuadro de diálogo para seleccionar archivo
        Stage stage = new Stage(); 
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                
                String fileName = selectedFile.getName(); // Nombre del archivo

                
                Path destino = Paths.get("src/ImagenesDenuncias/" + fileName);

                // Crear directorio si no existe
                Files.createDirectories(destino.getParent());

                // Copiar archivo al paquete
                Files.copy(selectedFile.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                rutaFoto = destino.toString();//ALMACENANDO RUTA PARA COLOCARLO EN EL DATABASE

                System.out.println("Archivo guardado en: " + destino.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error al guardar el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }
    
    @FXML
    public void registrarDenuncia(ActionEvent event){
        
        try{
        DenunciaRepository denRepository = new DenunciaRepository();
        DenunciaService denService = new DenunciaService(denRepository);
        LocalDate Fecha =  TXT_FECHA.getValue();
        LocalTime Hora = LocalTime.parse(TXT_HORA.getText());
        String distrito = CB_DISTRITO.getSelectionModel().getSelectedItem();
        String lugarDesc = TF_DESC_LUGAR.getText();
        boolean esAnonimo;
        if (RB_SI.isSelected()) {
            esAnonimo = true; // Usuario seleccionó anonimato
          } else if (RB_NO.isSelected()) {
            esAnonimo = false; // Usuario no seleccionó anonimato
          }
        String nombre;
        if (esAnonimo=false) {
            nombre = TXT_NOMBRE.getText(); 
        } else {
            nombre = "Anonimo";
        }
        String tipoDenu =CB_TIPO_DEN.getSelectionModel().getSelectedItem();
        String denuDesc = TF_DESC_DEN.getText();
        String foto = rutaFoto;
        Denuncia denuncia = new Denuncia.DenunciaBuilder()
                .setFecha(Fecha)
                .setHora(Hora)
                .setDistrito(distrito)
                .setLugarDesc(lugarDesc)
                .setEsAnonimo(esAnonimo)
                .setNombre(nombre)
                .setTipoDenu(tipoDenu)
                .setDenuDesc(denuDesc)
                .setFoto(foto)
                .build();
        denService.guardarDenuncia(denuncia);
        mostrarAlertaExito("¡El registro de su denuncia se completó con éxito!, podrá visualizarlo en la sección Ver Denuncias");
        borrarCampos();
        }catch(Exception e){
            mostrarAlertaError("Error al guardar la denuncia.");
        }
        
       
    }
    public void borrarCampos(){
        TXT_FECHA.setValue(null);
        TXT_HORA.clear();
        CB_DISTRITO.setValue(null);
        TF_DESC_LUGAR.clear();
        TXT_NOMBRE.clear();
        CB_TIPO_DEN.setValue(null);
        rutaFoto = null;
        Anonimato.selectToggle(RB_SI);
        TF_DESC_DEN.clear();
    } 
    
    private boolean validarFormulario() {
    // Validar fecha
    if (TXT_FECHA.getValue() == null) {
        mostrarAlertaError("La fecha es obligatoria.");
        return false;
    }

    // Validar hora
    if (TXT_HORA.getText().trim().isEmpty()) {
         mostrarAlertaError("La hora es obligatoria.");
        return false;
    }

    // Validar distrito
    if (CB_DISTRITO.getSelectionModel().getSelectedItem() == null) { // Asumiendo que el índice 0 es un placeholder
        mostrarAlertaError("Por favor, selecciona un distrito.");
        return false;
    }

    // Validar descripción del lugar
    if (TF_DESC_LUGAR.getText().trim().isEmpty()) {
        mostrarAlertaError("La descripción del lugar no puede estar vacía.");
        return false;
    }

    // Validar tipo de denuncia
    if (CB_TIPO_DEN.getSelectionModel().getSelectedItem() == null) { // Asumiendo que el índice 0 es un placeholder
        mostrarAlertaError("Por favor, selecciona un tipo de denuncia.");
        return false;
    }

    // Validar descripción de la denuncia
    if (TF_DESC_DEN.getText().trim().isEmpty()) {
        mostrarAlertaError("La descripción de la denuncia no puede estar vacía.");
        return false;
    }
    
    

    // Si pasa todas las validaciones, devolver true
    return true;
    }
    private void mostrarAlertaError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error de Validación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait(); // Bloquea hasta que el usuario cierre la alerta
       }
    private void mostrarAlertaExito(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait(); // Bloquea hasta que el usuario cierre la alerta
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
