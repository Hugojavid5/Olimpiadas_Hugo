package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Equipo;

public class EquiposController {
    @FXML
    private Button btnEliminar;
    @FXML
    private ComboBox<Equipo> cbEquipo;
    @FXML
    private TextField txtIniciales;
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