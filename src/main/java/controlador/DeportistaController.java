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
    private TextField txt_Altura;
    @FXML
    private TextField txt_Nombre;
    @FXML
    private TextField txt_Peso;
    @FXML
    private Button btt_FotoBorrar;

    @FXML
    private ResourceBundle resources;

    /**
     * Inicializa el controlador. Si ya existe un deportista, se cargan sus datos en los campos.
     * Si el deportista tiene una imagen, se muestra en el ImageView correspondiente.
     *
     * @param url URL de la vista FXML.
     * @param resourceBundle Recursos para la internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        this.imagen = null;
        if (deportista != null) {
            txt_Nombre.setText(deportista.getNombre());
            if (deportista.getSexo() == 'F') {
                rbFemale.setSelected(true);
                rbMale.setSelected(false);
            } else {
                rbMale.setSelected(true);
                rbFemale.setSelected(false);
            }
            txt_Peso.setText(deportista.getPeso() + "");
            txt_Altura.setText(deportista.getAltura() + "");
            if (deportista.getFoto() != null) {
                this.imagen = deportista.getFoto();
                try {
                    InputStream imagen = deportista.getFoto().getBinaryStream();
                    foto.setImage(new Image(imagen));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                btt_FotoBorrar.setDisable(false);
            }
        }
    }

    /**
     * Maneja el evento de eliminación de la foto seleccionada.
     * Al invocar este metodo, la variable de imagen se establece en nulo
     * y se reemplaza la imagen actual por una imagen predeterminada del recurso.
     * También desactiva el botón de eliminación de la foto.
     *
     * @param event el evento que desencadena la acción de borrar la foto.
     */
    @FXML
    void borrarFoto(ActionEvent event) {
        imagen = null;
        foto.setImage(new Image(getClass().getResourceAsStream("/Imagenes/persona.jpg")));
        btt_FotoBorrar.setDisable(true);
    }

    /**
     * Maneja el evento de eliminación del deportista. Cierra la ventana actual.
     *
     * @param event el evento de eliminación.
     */
    @FXML
    void eliminar(ActionEvent event) {
        Stage stage = (Stage) txt_Nombre.getScene().getWindow();
        stage.close();
    }

    /**
     * Guarda los datos del deportista en la base de datos. Si es un nuevo deportista, lo inserta,
     * y si es un deportista existente, lo actualiza. Si ocurre algún error, muestra un mensaje de alerta.
     *
     * @param event el evento de guardar.
     */
    @FXML
    void guardar(ActionEvent event) {
        String error = validar();
        if (!error.isEmpty()) {
            alerta(error);
        } else {
            Deportista nuevo = new Deportista();
            nuevo.setNombre(txt_Nombre.getText());
            if (rbFemale.isSelected()) {
                nuevo.setSexo(nuevo.getSexCategory('F'));
            } else {
                nuevo.setSexo(nuevo.getSexCategory('M'));
            }
            nuevo.setPeso(Integer.parseInt(txt_Peso.getText()));
            nuevo.setAltura(Integer.parseInt(txt_Altura.getText()));
            nuevo.setFoto(this.imagen);
            if (this.deportista == null) {
                int id = DaoDeportista.insertar(nuevo);
                if (id == -1) {
                    alerta(resources.getString("save.fail"));
                } else {
                    confirmacion(resources.getString("save.athlete"));
                    Stage stage = (Stage) txt_Nombre.getScene().getWindow();
                    stage.close();
                }
            } else {
                if (DaoDeportista.modificar(this.deportista, nuevo)) {
                    confirmacion(resources.getString("update.athlete"));
                    Stage stage = (Stage) txt_Nombre.getScene().getWindow();
                    stage.close();
                } else {
                    alerta(resources.getString("save.fail"));
                }
            }
        }
    }

    /**
     * Valida los datos del formulario de deportista. Si algún dato está vacío o es incorrecto,
     * se retorna un mensaje de error.
     *
     * @return un string con los posibles errores encontrados.
     */
    private String validar() {
        String error = "";
        if (txt_Nombre.getText().isEmpty()) {
            error = resources.getString("validate.athlete.name") + "\n";
        }
        if (txt_Peso.getText().isEmpty()) {
            error += resources.getString("validate.athlete.weight") + "\n";
        } else {
            try {
                Integer.parseInt(txt_Peso.getText());
            } catch (NumberFormatException e) {
                error += resources.getString("validate.athlete.weight.num") + "\n";
            }
        }
        if (txt_Altura.getText().isEmpty()) {
            error += resources.getString("validate.athlete.height") + "\n";
        } else {
            try {
                Integer.parseInt(txt_Altura.getText());
            } catch (NumberFormatException e) {
                error += resources.getString("validate.athlete.height.num") + "\n";
            }
        }
        return error;
    }

    /**
     * Abre un FileChooser para seleccionar una imagen de perfil para el deportista. Si la imagen
     * seleccionada tiene un tamaño mayor al permitido, muestra una alerta. Si la imagen es válida,
     * la carga en el ImageView.
     *
     * @param event el evento de selección de imagen.
     */
    @FXML
    void seleccionImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resources.getString("athlete.photo.chooser"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog(null);

        if (file == null) {
            return;  // Si no se selecciona ningún archivo, salimos del metodo
        }

        try {
            // Verificación del tamaño del archivo
            double kbs = (double) file.length() / 1024;
            if (kbs > 64) {
                alerta(resources.getString("athlete.photo.chooser.size"));
            } else if (file.length() == 0) {
                alerta("El archivo seleccionado está vacío.");
            } else {
                // Usamos InputStream para cargar la imagen
                InputStream imagen = new FileInputStream(file);

                // Verificamos que la imagen se pueda cargar correctamente
                try {
                    Image image = new Image(imagen);
                    foto.setImage(image);  // Cargamos la imagen en el control ImageView
                    this.imagen = DaoDeportista.convertFileToBlob(file);  // Si necesitas el Blob
                    btt_FotoBorrar.setDisable(false);
                } catch (IllegalArgumentException | IOException e) {
                    alerta("No se pudo procesar la imagen. Asegúrate de que el archivo sea una imagen válida.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            alerta(resources.getString("athlete.photo.chooser.fail"));
        } catch (SQLException e) {
            e.printStackTrace();
            alerta(resources.getString("athlete.photo.chooser.fail"));
        }
    }



    /**
     * Constructor con deportista existente. Inicializa el controlador con un deportista específico.
     *
     * @param deportista el deportista que se desea cargar.
     */
    public DeportistaController(Deportista deportista) {
        this.deportista = deportista;
    }

    /**
     * Constructor sin deportista. Inicializa el controlador para un deportista nuevo.
     */
    public DeportistaController() {
        this.deportista = null;
    }

    /**
     * Muestra una alerta de error al usuario.
     *
     * @param texto el mensaje que se mostrará en la alerta.
     */
    public void alerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("Error");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    /**
     * Muestra un mensaje de confirmación al usuario.
     *
     * @param texto el mensaje que se mostrará en la alerta.
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }
}
