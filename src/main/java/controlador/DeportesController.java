package controlador;

import Dao.DaoDeporte;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Deporte;
import javafx.beans.value.ObservableValue;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controlador para la vista de deportes.
 * Gestiona la interacción con la interfaz de usuario para añadir, modificar, eliminar y visualizar deportes.
 */
public class DeportesController implements Initializable {
    private Deporte deporte;
    private Deporte crear;

    @FXML
    private Button btnEliminar;
    @FXML
    private Label lblDelete;
    @FXML
    private ComboBox<Deporte> cbDeporte;
    @FXML
    private TextField txtNombre;
    @FXML
    private ResourceBundle resources;

    /**
     * Inicializa el controlador, cargando los deportes y configurando el ComboBox.
     *
     * @param url La URL de la localización.
     * @param resourceBundle El conjunto de recursos para la localización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        this.deporte = null;
        crear = new Deporte();
        crear.setId_deporte(0);
        crear.setNombre(resources.getString("cb.new"));
        cargarDeportes();
        // Listener ComboBox
        cbDeporte.getSelectionModel().selectedItemProperty().addListener(this::cambioDeporte);
    }

    /**
     * Carga los deportes desde la base de datos y los añade al ComboBox.
     * También añade la opción de "crear" al principio de la lista.
     */
    public void cargarDeportes() {
        cbDeporte.getItems().clear();
        cbDeporte.getItems().add(crear);
        ObservableList<Deporte> deportes = DaoDeporte.cargarListado();
        cbDeporte.getItems().addAll(deportes);
        cbDeporte.getSelectionModel().select(0);
    }

    /**
     * Gestor para el cambio de selección en el ComboBox de deportes.
     * Muestra o oculta el botón de eliminar según si el deporte es seleccionable o no.
     *
     * @param observable El valor observado.
     * @param oldValue El valor anterior.
     * @param newValue El nuevo valor seleccionado.
     */
    public void cambioDeporte(ObservableValue<? extends Deporte> observable, Deporte oldValue, Deporte newValue) {
        if (newValue != null) {
            btnEliminar.setDisable(true);
            lblDelete.setVisible(false);
            if (newValue.equals(crear)) {
                deporte = null;
                txtNombre.setText(null);
            } else {
                deporte = newValue;
                txtNombre.setText(deporte.getNombre());
                if (DaoDeporte.esEliminable(deporte)) {
                    btnEliminar.setDisable(false);
                } else {
                    lblDelete.setVisible(true);
                }
            }
        }
    }

    /**
     * Cancela la operación y cierra la ventana actual.
     *
     * @param event El evento de acción.
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    /**
     * Elimina el deporte seleccionado. Muestra una confirmación antes de proceder.
     *
     * @param event El evento de acción.
     */
    @FXML
    void eliminar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(txtNombre.getScene().getWindow());
        alert.setHeaderText(null);
        alert.setTitle(resources.getString("window.confirm"));
        alert.setContentText(resources.getString("delete.sports.prompt"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (DaoDeporte.eliminar(deporte)) {
                confirmacion(resources.getString("delete.sports.success"));
                cargarDeportes();
            } else {
                alerta(resources.getString("delete.sports.fail"));
            }
        }
    }

    /**
     * Guarda los cambios realizados en el deporte. Si no se ha modificado uno existente, se crea uno nuevo.
     * Valida que el nombre no esté vacío antes de guardar.
     *
     * @param event El evento de acción.
     */
    @FXML
    void guardar(ActionEvent event) {
        if (txtNombre.getText().isEmpty()) {
            alerta(resources.getString("validate.sports.name"));
        } else {
            Deporte nuevo = new Deporte();
            nuevo.setNombre(txtNombre.getText());
            if (this.deporte == null) {
                int id = DaoDeporte.insertar(nuevo);
                if (id == -1) {
                    alerta(resources.getString("save.fail"));
                } else {
                    confirmacion(resources.getString("save.sports"));
                    cargarDeportes();
                }
            } else {
                if (DaoDeporte.modificar(this.deporte, nuevo)) {
                    confirmacion(resources.getString("update.sports"));
                    cargarDeportes();
                } else {
                    alerta(resources.getString("save.fail"));
                }
            }
        }
    }

    /**
     * Muestra una alerta de error con el mensaje proporcionado.
     *
     * @param texto El texto del mensaje de error.
     */
    public void alerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("Error");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    /**
     * Muestra una alerta de confirmación con el mensaje proporcionado.
     *
     * @param texto El texto del mensaje de confirmación.
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }
}
