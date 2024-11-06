package controlador;
import Dao.DaoDeporte;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Deporte;

import java.net.URL;
import java.util.ResourceBundle;

public class DeportesController implements Initializable {
    @FXML
    private Button btnEliminar;
    @FXML
    private ComboBox<Deporte> cbDeporte;
    @FXML
    private TextField txtNombre;
    @FXML
    private ResourceBundle resources;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        Deporte nuevo = new Deporte();
        nuevo.setId_deporte(0);
        nuevo.setNombre(resources.getString("cb.new"));
        cbDeporte.getItems().add(nuevo);
        ObservableList<Deporte> deportes = DaoDeporte.cargarListado();
        cbDeporte.getItems().addAll(deportes);
        cbDeporte.getSelectionModel().select(0);
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
}