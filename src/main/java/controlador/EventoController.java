package controlador;

import Dao.DaoDeporte;
import Dao.DaoEvento;
import Dao.DaoOlimpiada;
import model.Deporte;
import model.Evento;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Olimpiada;

import java.net.URL;
import java.util.ResourceBundle;

public class EventoController implements Initializable {
    private Evento evento;

    @FXML
    private ListView<Deporte> lstDeporte;

    @FXML
    private ListView<Olimpiada> lstOlimpiada;

    @FXML
    private TextField txtNombre;

    @FXML
    private ResourceBundle resources;

    /**
     * Constructor que permite inicializar el controlador con un evento específico.
     *
     * @param evento el evento que se va a editar, o null si se quiere crear uno nuevo.
     */
    public EventoController(Evento evento) {
        this.evento = evento;
    }

    /**
     * Constructor por defecto para crear un nuevo evento.
     */
    public EventoController() {
        this.evento = null;
    }

    /**
     * Método que se ejecuta al inicializar el controlador. Carga las listas de olimpiadas y deportes
     * y prellena los campos si el evento ya existe.
     *
     * @param url la URL del archivo FXML.
     * @param resourceBundle el ResourceBundle para la internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        cargarListas();
        if (this.evento != null) {
            txtNombre.setText(evento.getNombre());
            lstOlimpiada.getSelectionModel().select(evento.getOlimpiada());
            lstDeporte.getSelectionModel().select(evento.getDeporte());
        }
    }

    /**
     * Carga las listas de olimpiadas y deportes desde la base de datos y las muestra en los ListView.
     */
    public void cargarListas() {
        ObservableList<Olimpiada> olimpiadas = DaoOlimpiada.cargarListado();
        lstOlimpiada.getItems().addAll(olimpiadas);
        ObservableList<Deporte> deportes = DaoDeporte.cargarListado();
        lstDeporte.getItems().addAll(deportes);
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Cancelar". Cierra la ventana.
     *
     * @param event el evento de acción.
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Guardar". Valida los datos introducidos
     * y guarda el evento en la base de datos, ya sea nuevo o actualizado.
     *
     * @param event el evento de acción.
     */
    @FXML
    void guardar(ActionEvent event) {
        String error = "";
        if (txtNombre.getText().isEmpty()) {
            error = resources.getString("validate.event.name") + "\n";
        }
        if (lstOlimpiada.getSelectionModel().getSelectedItem() == null) {
            error += resources.getString("validate.event.olympic") + "\n";
        }
        if (lstDeporte.getSelectionModel().getSelectedItem() == null) {
            error += resources.getString("validate.event.sport") + "\n";
        }
        if (!error.isEmpty()) {
            alerta(error);
        } else {
            Evento nuevo = new Evento();
            nuevo.setNombre(txtNombre.getText());
            nuevo.setOlimpiada(lstOlimpiada.getSelectionModel().getSelectedItem());
            nuevo.setDeporte(lstDeporte.getSelectionModel().getSelectedItem());
            if (this.evento == null) {
                int id = DaoEvento.insertar(nuevo);
                if (id == -1) {
                    alerta(resources.getString("save.fail"));
                } else {
                    confirmacion(resources.getString("save.events"));
                    Stage stage = (Stage) txtNombre.getScene().getWindow();
                    stage.close();
                }
            } else {
                if (DaoEvento.modificar(evento, nuevo)) {
                    confirmacion(resources.getString("update.events"));
                    Stage stage = (Stage) txtNombre.getScene().getWindow();
                    stage.close();
                } else {
                    alerta(resources.getString("save.fail"));
                }
            }
        }
    }

    /**
     * Muestra una alerta de error con el mensaje proporcionado.
     *
     * @param texto el contenido de la alerta de error.
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
     * @param texto el contenido del mensaje de confirmación.
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }
}
