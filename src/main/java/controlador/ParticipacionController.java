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

/**
 * Controlador para gestionar la participación de un deportista en un evento.
 */
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

    /**
     * Constructor de la clase con un objeto de participación.
     *
     * @param participacion objeto de tipo Participacion
     */
    public ParticipacionController(Participacion participacion) {
        this.participacion = participacion;
    }

    /**
     * Constructor de la clase sin parámetros.
     */
    public ParticipacionController() {
        this.participacion = null;
    }

    /**
     * Inicializa los componentes de la ventana, carga las listas y prellena los campos si se pasa una participación existente.
     *
     * @param url la URL de la vista FXML
     * @param resourceBundle el recurso de idioma de la aplicación
     */
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
     * Carga las listas de deportistas, equipos y eventos.
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
     * Función que se ejecuta cuando se pulsa el botón "Cancelar". Cierra la ventana.
     *
     * @param event el evento generado por el botón "Cancelar"
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) txtEdad.getScene().getWindow();
        stage.close();
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Guardar". Valida y guarda los datos de la participación.
     *
     * @param event el evento generado por el botón "Guardar"
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
                    confirmacion(resources.getString("save.participation"));
                    Stage stage = (Stage) txtEdad.getScene().getWindow();
                    stage.close();
                } else {
                    alerta(resources.getString("save.fail"));
                }
            } else {
                if (DaoParticipacion.modificar(participacion, nuevo)) {
                    confirmacion(resources.getString("update.participation"));
                    Stage stage = (Stage) txtEdad.getScene().getWindow();
                    stage.close();
                } else {
                    alerta(resources.getString("save.fail"));
                }
            }
        }
    }

    /**
     * Valida los datos ingresados en los campos de la ventana.
     *
     * @return un string con los mensajes de error de validación, o un string vacío si no hay errores
     */
    public String validar() {
        String error = "";
        if (lstDeportista.getSelectionModel().getSelectedItem() == null) {
            error = resources.getString("validate.participation.athlete") + "\n";
        }
        if (lstEvento.getSelectionModel().getSelectedItem() == null) {
            error += resources.getString("validate.participation.event") + "\n";
        }
        if (lstEquipo.getSelectionModel().getSelectedItem() == null) {
            error += resources.getString("validate.participation.team") + "\n";
        }
        if (txtEdad.getText().isEmpty()) {
            error += resources.getString("validate.participation.age") + "\n";
        } else {
            try {
                Integer.parseInt(txtEdad.getText());
            } catch (NumberFormatException e) {
                error += resources.getString("validate.participation.age.num") + "\n";
            }
        }
        if (txtMedalla.getText().isEmpty()) {
            error += resources.getString("validate.participation.medal") + "\n";
        } else {
            if (txtMedalla.getText().length() > 6) {
                error += resources.getString("validate.participation.medal.num") + "\n";
            }
        }
        return error;
    }

    /**
     * Muestra una alerta con el mensaje de error.
     *
     * @param texto el texto a mostrar en la alerta
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
     * @param texto el contenido del mensaje de confirmación
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }
}
