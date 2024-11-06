package controlador;
import Dao.DaoDeportista;
import Dao.DaoEquipo;
import Dao.DaoEvento;
import Dao.DaoParticipacion;
import model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class ParticipacionController implements Initializable {
    private Participacion participacion;

    @FXML
    private ListView<Deportista> lstDeportista;
    @FXML
    private ListView<Equipo> lstEquipo;
    @FXML
    private ListView<Evento> lstEvento;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtMedalla;
    @FXML
    private ResourceBundle resources;

    public ParticipacionController(Participacion participacion) {
        this.participacion = participacion;
    }
    public ParticipacionController() {
        this.participacion = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        cargarListas();
        if (this.participacion != null) {
            lstDeportista.getSelectionModel().select(participacion.getDeportista());
            lstDeportista.setDisable(true);
            lstEvento.getSelectionModel().select(participacion.getEvento());
            lstEvento.setDisable(true);
            lstEquipo.getSelectionModel().select(participacion.getEquipo());
            txtEdad.setText(participacion.getEdad() + "");
            txtMedalla.setText(participacion.getMedalla());
        }
    }
    /**
     * Función que carga las listas
     */
    public void cargarListas() {
        ObservableList<Deportista> deportistas = DaoDeportista.cargarListado();
        lstDeportista.getItems().addAll(deportistas);
        ObservableList<Evento> eventos = DaoEvento.cargarListado();
        lstEvento.getItems().addAll(eventos);
        ObservableList<Equipo> equipos = DaoEquipo.cargarListado();
        lstEquipo.getItems().addAll(equipos);
    }
    /**
     * Función que se ejecuta cuando se pulsa el botón "Cancelar". Cierra la ventana
     *
     * @param event
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)txtEdad.getScene().getWindow();
        stage.close();
    }
    /**
     * Función que se ejecuta cuando se pulsa el botón "Guardar". Válida y procesa los datos
     *
     * @param event
     */
    @FXML
    void guardar(ActionEvent event) {
        String error = validar();
        if (!error.isEmpty()) {
            alerta(error);
        } else {
            Participacion nuevo = new Participacion();
            nuevo.setDeportista(lstDeportista.getSelectionModel().getSelectedItem());
            nuevo.setEvento(lstEvento.getSelectionModel().getSelectedItem());
            nuevo.setEquipo(lstEquipo.getSelectionModel().getSelectedItem());
            nuevo.setEdad(Integer.parseInt(txtEdad.getText()));
            nuevo.setMedalla(txtMedalla.getText());
            if (this.participacion == null) {
                if (DaoParticipacion.insertar(nuevo)) {
                    confirmacion("Participación creada correctamente");
                    Stage stage = (Stage)txtEdad.getScene().getWindow();
                    stage.close();
                } else {
                    alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                }
            } else {
                if (DaoParticipacion.modificar(participacion, nuevo)) {
                    confirmacion("Participación actualizada correctamente");
                    Stage stage = (Stage)txtEdad.getScene().getWindow();
                    stage.close();
                } else {
                    alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                }
            }
        }
    }
    /**
     * Válida los datos del formulario
     *
     * @return string con posibles errores
     */
    public String validar() {
        String error = "";
        if (lstDeportista.getSelectionModel().getSelectedItems() == null) {
            error = "Un deportista de la lista tiene que estar seleccionada\n";
        }
        if (lstEvento.getSelectionModel().getSelectedItems() == null) {
            error += "Un evento de la lista tiene que estar seleccionada\n";
        }
        if (lstEquipo.getSelectionModel().getSelectedItems() == null) {
            error += "Un equipo de la lista tiene que estar seleccionada\n";
        }
        if (txtEdad.getText().isEmpty()) {
            error += "El campo edad no puede estar vacío\n";
        } else {
            try {
                Integer.parseInt(txtEdad.getText());
            } catch (NumberFormatException e) {
                error += "El campo edad tiene que ser numérico\n";
            }
        }
        if (txtMedalla.getText().isEmpty()) {
            error += "El campo medalla no puede estar vacío\n";
        } else {
            if (txtMedalla.getText().length() > 6) {
                error += "El campo iniciales no puede ser mayor que 6 carácteres\n";
            }
        }
        return error;
    }





    public void alerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("Error");
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