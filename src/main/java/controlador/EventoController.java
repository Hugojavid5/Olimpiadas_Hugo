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

    public EventoController(Evento evento) {
        this.evento = evento;
    }

    public EventoController() {
        this.evento = null;
    }
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
    public void cargarListas() {
        ObservableList<Olimpiada> olimpiadas = DaoOlimpiada.cargarListado();
        lstOlimpiada.getItems().addAll(olimpiadas);
        ObservableList<Deporte> deportes = DaoDeporte.cargarListado();
        lstDeporte.getItems().addAll(deportes);
    }
    /**
     * Función que se ejecuta cuando se pulsa el botón "Cancelar". Cierra la ventana
     *
     * @param event
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)txtNombre.getScene().getWindow();
        stage.close();
    }
    /**
     * Función que se ejecuta cuando se pulsa el botón "Guardar". Válida y procesa los datos
     *
     * @param event
     */
    @FXML
    void guardar(ActionEvent event) {
        String error = "";
        if (txtNombre.getText().isEmpty()) {
            error = "El campo nombre no puede estar vacío\n";
        }
        if (lstOlimpiada.getSelectionModel().getSelectedItems() == null) {
            error += "Una olimpiada de la lista tiene que estar seleccionada\n";
        }
        if (lstDeporte.getSelectionModel().getSelectedItems() == null) {
            error += "Un deporte de la lista tiene que estar seleccionada\n";
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
                    alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                } else {
                    confirmacion("Evento creado correctamente");
                    Stage stage = (Stage)txtNombre.getScene().getWindow();
                    stage.close();
                }
            } else {
                if (DaoEvento.modificar(evento, nuevo)) {
                    confirmacion("Evento actualizado correctamente");
                    Stage stage = (Stage)txtNombre.getScene().getWindow();
                    stage.close();
                } else {
                    alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                }
            }
        }
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
