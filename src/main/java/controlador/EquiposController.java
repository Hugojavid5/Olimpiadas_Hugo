package controlador;

import Dao.DaoEquipo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import model.Equipo;

public class EquiposController implements Initializable {

    private Equipo equipo;
    private Equipo crear;

    @FXML
    private Button btt_Eliminar;

    @FXML
    private Label lbl_Delete;

    @FXML
    private ComboBox<Equipo> cb_Equipo;

    @FXML
    private TextField txt_Iniciales;

    @FXML
    private TextField txt_Nombre;

    @FXML
    private ResourceBundle resources; // ResourceBundle injected automatically by FXML loader

    /**
     * Función que se ejecuta cuando se inicia la ventana.
     * Inicializa el ComboBox con los equipos existentes y configura el listener para el cambio de selección.
     *
     * @param url la URL del archivo FXML.
     * @param resourceBundle el ResourceBundle para la internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        this.equipo = null;
        crear = new Equipo();
        crear.setId_equipo(0);
        crear.setNombre(resources.getString("cb.new"));
        cargarEquipos();
        // Listener ComboBox
        cb_Equipo.getSelectionModel().selectedItemProperty().addListener(this::cambioEquipo);
    }

    /**
     * Función que carga los equipos de la base de datos al ComboBox.
     * Añade todos los equipos existentes, incluyendo una opción para crear un nuevo equipo.
     */
    public void cargarEquipos() {
        cb_Equipo.getItems().clear();
        cb_Equipo.getItems().add(crear);
        ObservableList<Equipo> equipos = DaoEquipo.cargarListado();
        cb_Equipo.getItems().addAll(equipos);
        cb_Equipo.getSelectionModel().select(0);
    }

    /**
     * Listener que se ejecuta cuando se cambia la selección en el ComboBox de equipos.
     * Actualiza los campos de texto con la información del equipo seleccionado.
     *
     * @param observable el valor observado.
     * @param oldValue el valor anterior.
     * @param newValue el nuevo valor seleccionado.
     */
    public void cambioEquipo(ObservableValue<? extends Equipo> observable, Equipo oldValue, Equipo newValue) {
        if (newValue != null) {
            btt_Eliminar.setDisable(true);
            lbl_Delete.setVisible(false);
            if (newValue.equals(crear)) {
                equipo = null;
                txt_Nombre.setText(null);
                txt_Iniciales.setText(null);
            } else {
                equipo = newValue;
                txt_Nombre.setText(equipo.getNombre());
                txt_Iniciales.setText(equipo.getIniciales());
                if (DaoEquipo.esEliminable(equipo)) {
                    btt_Eliminar.setDisable(false);
                } else {
                    lbl_Delete.setVisible(true);
                }
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Cancelar". Cierra la ventana actual.
     *
     * @param event el evento de acción.
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)txt_Nombre.getScene().getWindow();
        stage.close();
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Eliminar". Elimina el equipo seleccionado tras confirmar con el usuario.
     *
     * @param event el evento de acción.
     */
    @FXML
    void eliminar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(txt_Nombre.getScene().getWindow());
        alert.setHeaderText(null);
        alert.setTitle(resources.getString("window.confirm"));
        alert.setContentText(resources.getString("delete.teams.prompt"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (DaoEquipo.eliminar(equipo)) {
                confirmacion(resources.getString("delete.teams.success"));
                cargarEquipos();
            } else {
                alerta(resources.getString("delete.teams.fail"));
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Guardar". Valida los datos del formulario
     * y procesa la información para crear o actualizar un equipo.
     *
     * @param event el evento de acción.
     */
    @FXML
    void guardar(ActionEvent event) {
        String error = "";
        if (txt_Nombre.getText().isEmpty()) {
            error = resources.getString("validate.teams.name") + "\n";
        }
        if (txt_Iniciales.getText().isEmpty()) {
            error +=  resources.getString("validate.teams.noc") + "\n";
        } else {
            if (txt_Iniciales.getText().length() > 3) {
                error +=  resources.getString("validate.teams.noc.num") +  "\n";
            }
        }
        if (!error.isEmpty()) {
            alerta(error);
        } else {
            Equipo nuevo = new Equipo();
            nuevo.setNombre(txt_Nombre.getText());
            nuevo.setIniciales(txt_Iniciales.getText());
            if (this.equipo == null) {
                int id = DaoEquipo.insertar(nuevo);
                if (id == -1) {
                    alerta(resources.getString("save.fail"));
                } else {
                    confirmacion(resources.getString("save.teams"));
                    cargarEquipos();
                }
            } else {
                if (DaoEquipo.modificar(equipo, nuevo)) {
                    confirmacion(resources.getString("update.teams"));
                    cargarEquipos();
                } else {
                    alerta(resources.getString("save.fail"));
                }
            }
        }
    }

    /**
     * Función que muestra un mensaje de alerta al usuario con el contenido proporcionado.
     *
     * @param texto el contenido de la alerta.
     */
    public void alerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("Error");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    /**
     * Función que muestra un mensaje de confirmación al usuario con el contenido proporcionado.
     *
     * @param texto el contenido del mensaje.
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }
}
