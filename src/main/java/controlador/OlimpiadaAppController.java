package controlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import Language.LanguageSwitcher;

public class OlimpiadaAppController {
    @FXML // fx:id="btnEditar"
    private MenuItem btnEditar; // Value injected by FXMLLoader
    @FXML // fx:id="btnEliminar"
    private MenuItem btnEliminar; // Value injected by FXMLLoader
    @FXML // fx:id="cbTabla"
    private ComboBox<String> cbTabla; // Value injected by FXMLLoader
    @FXML // fx:id="filtroNombre"
    private TextField filtroNombre; // Value injected by FXMLLoader
    @FXML // fx:id="langEN"
    private RadioMenuItem langEN; // Value injected by FXMLLoader
    @FXML // fx:id="langES"
    private RadioMenuItem langES; // Value injected by FXMLLoader
    @FXML // fx:id="tabla"
    private TableView tabla; // Value injected by FXMLLoader
    @FXML // fx:id="tgIdioma"
    private ToggleGroup tgIdioma; // Value injected by FXMLLoader
    @FXML
    private ResourceBundle resources; // ResourceBundle injected automatically by FXML loader

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
        tgIdioma.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            Locale locale;
            if (langES.isSelected()) {
                locale = new Locale("es", "ES");
            } else {
                locale = new Locale("en", "UK");
            }
            new LanguageSwitcher((Stage) tabla.getScene().getWindow()).switchLanguage(locale);
        });
    }
    @FXML
    void aniadir(ActionEvent event) {
    }
    @FXML
    void deportes(ActionEvent event) {
    }
    @FXML
    void editar(ActionEvent event) {
    }
    @FXML
    void eliminar(ActionEvent event) {
    }
    @FXML
    void equipos(ActionEvent event) {
    }
    @FXML
    void olimpiadas(ActionEvent event) {
    }
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
