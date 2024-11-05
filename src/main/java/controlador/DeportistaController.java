package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class DeportistaController implements Initializable {
    @FXML
    private Button btnEliminar;
    @FXML // fx:id="foto"
    private ImageView foto;
    @FXML
    private RadioButton rbFemale;
    @FXML
    private RadioButton rbMale;
    @FXML
    private ToggleGroup tgSexo;
    @FXML
    private TextField txtAltura;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPeso;

    @FXML
    private ResourceBundle resources;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void cancelar(ActionEvent event) {
    }
    @FXML
    void eliminar(ActionEvent event) {
    }
    @FXML
    void guardar(ActionEvent event) {
    }
    @FXML
    void seleccionImagen(ActionEvent event) {
    }
    /**
     * Función que muestra un mensaje de alerta al usuario
     *
     * @param texto contenido de la alerta
     */
    public void alerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("ERROR");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }
    /**
     * Función que muestra un mensaje de confirmación al usuario
     *
     * @param texto contenido del mensaje
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }
}