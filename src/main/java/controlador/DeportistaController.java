package controlador;

import Dao.DaoDeportista;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Deportista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeportistaController implements Initializable {
    private Deportista deportista;
    private Blob imagen;


    @FXML
    private ImageView foto;
    @FXML
    private RadioButton rbFemale;
    @FXML
    private RadioButton rbMale;
    @FXML
    private ToggleGroup tgSexo;
    @FXML
    private TextField txtAltura;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPeso;
    @FXML
    private Button btnFotoBorrar;

    @FXML
    private ResourceBundle resources;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        this.imagen = null;
        if (deportista != null) {
            txtNombre.setText(deportista.getNombre());
            if (deportista.getSexo() == 'F') {
                rbFemale.setSelected(true);
                rbMale.setSelected(false);
            } else {
                rbMale.setSelected(true);
                rbFemale.setSelected(false);
            }
            txtPeso.setText(deportista.getPeso() + "");
            txtAltura.setText(deportista.getAltura() + "");
            if (deportista.getFoto() != null) {
                System.out.println("Has image");
                this.imagen = deportista.getFoto();
                try {
                    InputStream imagen = deportista.getFoto().getBinaryStream();
                    foto.setImage(new Image(imagen));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                btnFotoBorrar.setDisable(false);
            }
        }
    }
    /**
     * Maneja el evento de eliminación de la foto seleccionada.
     * Al invocar este método, la variable de imagen se establece en nulo
     * y se reemplaza la imagen actual por una imagen predeterminada del recurso.
     * También desactiva el botón de eliminación de la foto.
     *
     * @param event el evento que desencadena la acción de borrar la foto.
     */

    @FXML
    void borrarFoto(ActionEvent event) {
        imagen = null;
        foto.setImage(new Image(getClass().getResourceAsStream("/images/deportista.png")));
        btnFotoBorrar.setDisable(true);
    }

    @FXML
    void cancelar(ActionEvent event) {
    }
    @FXML
    void eliminar(ActionEvent event) {
        Stage stage = (Stage)txtNombre.getScene().getWindow();
        stage.close();
    }
    @FXML
    void guardar(ActionEvent event) {
        String error = validar();
        if (!error.isEmpty()) {
            alerta(error);
        } else {
            Deportista nuevo = new Deportista();
            nuevo.setNombre(txtNombre.getText());
            if (rbFemale.isSelected()) {
                nuevo.setSexo(nuevo.getSexCategory('F'));
            } else {
                nuevo.setSexo(nuevo.getSexCategory('M'));
            }
            nuevo.setPeso(Integer.parseInt(txtPeso.getText()));
            nuevo.setAltura(Integer.parseInt(txtAltura.getText()));
            nuevo.setFoto(this.imagen);
            if (this.deportista == null) {
                int id = DaoDeportista.insertar(nuevo);
                if (id == -1) {
                    alerta(resources.getString("save.fail"));
                } else {
                    confirmacion(resources.getString("save.athlete"));
                    Stage stage = (Stage)txtNombre.getScene().getWindow();
                    stage.close();
                }
            } else {
                if (DaoDeportista.modificar(this.deportista,nuevo)) {
                    confirmacion(resources.getString("update.athlete"));
                    Stage stage = (Stage)txtNombre.getScene().getWindow();
                    stage.close();
                } else {
                    alerta(resources.getString("save.fail"));
                }
            }
        }
    }

    /**
     * Válida los datos del formulario
     *
     * @return string con posibles errores
     */
    private String validar() {
        String error = "";
        if (txtNombre.getText().isEmpty()) {
            error = resources.getString("validate.athlete.name") + "\n";
        }
        if (txtPeso.getText().isEmpty()) {
            error += resources.getString("validate.athlete.weight") + "\n";
        } else {
            try {
                Integer.parseInt(txtPeso.getText());
            } catch (NumberFormatException e) {
                error += resources.getString("validate.athlete.weight.num") + "\n";
            }
        }
        if (txtAltura.getText().isEmpty()) {
            error += resources.getString("validate.athlete.height") + "\n";
        } else {
            try {
                Integer.parseInt(txtAltura.getText());
            } catch (NumberFormatException e) {
                error += resources.getString("validate.athlete.height.num") + "\n";
            }
        }
        return error;
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Seleccionar". Abre un FileChooser para seleccionar una imagen
     *
     * @param event
     */
    @FXML
    void seleccionImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resources.getString("athlete.photo.chooser"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg","*.png"));
        File file = fileChooser.showOpenDialog(null);
        try {
            InputStream imagen = new FileInputStream(file);
            Blob blob = DaoDeportista.convertFileToBlob(file);
            this.imagen = blob;
            foto.setImage(new Image(imagen));
            btnFotoBorrar.setDisable(false);
        } catch (IOException|NullPointerException e) {
            System.out.println("Imagen no seleccionada");
        } catch (SQLException e) {
            e.printStackTrace();
            alerta(resources.getString("athlete.photo.chooser.fail"));
        }

    }
    public DeportistaController(Deportista deportista) {
        this.deportista = deportista;
    }

    public DeportistaController() {
        this.deportista = null;
    }

    /**
     * Función que muestra un mensaje de alerta al usuario
     *
     * @param texto contenido de la alerta
     */
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