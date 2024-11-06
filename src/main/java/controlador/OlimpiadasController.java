package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Olimpiada;

/**
 * Clase que controla los eventos de la ventana olimpiadas
 */
public class OlimpiadasController {
    @FXML
    private Button btnEliminar;
    @FXML
    private ComboBox<Olimpiada> cbOlimpiada;
    @FXML
    private RadioButton rbInvierno;
    @FXML
    private RadioButton rbVerano;
    @FXML
    private ToggleGroup tgTemporada;
    @FXML
    private TextField txtAnio;
    @FXML
    private TextField txtCiudad;
    @FXML
    private TextField txtNombre;
    @FXML
    void cancelar(ActionEvent event) {
    }
    @FXML
    void eliminar(ActionEvent event) {
    }
    @FXML
    void guardar(ActionEvent event) {
    }
}