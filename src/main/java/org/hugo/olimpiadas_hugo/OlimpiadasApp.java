package org.hugo.olimpiadas_hugo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Language.LanguageManager;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import BBDD.ConexionBBDD;

import java.io.IOException;

/**
 * Clase principal que lanza la aplicación de la Olimpiada.
 * Se encarga de cargar la interfaz gráfica, gestionar el idioma de la aplicación y mostrar la ventana principal.
 *
 *
 */
public class OlimpiadasApp extends Application {

    /**
     * Función heredada de {@link Application#start(Stage)} que inicializa y muestra la ventana principal de la aplicación.
     * Carga el archivo FXML de inicio, establece el idioma de la aplicación,
     * y configura los parámetros básicos de la ventana como el icono, el título y el tamaño.
     *
     * @param stage la ventana principal de la aplicación
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el idioma de la aplicación
        ResourceBundle bundle = LanguageManager.getInstance().getBundle();

        // Cargar la interfaz de usuario desde el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Inicio.fxml"), bundle);

        // Establecer el icono de la ventana
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Imagenes/olimpiadas.png")));

        // Crear la escena y asignarla al escenario (ventana)
        Scene scene = new Scene(fxmlLoader.load());

        // Establecer el título de la ventana usando el recurso del idioma
        stage.setTitle(bundle.getString("app.name"));

        // Establecer las dimensiones mínimas de la ventana
        stage.setMinWidth(550);
        stage.setMinHeight(300);

        // Asignar la escena al escenario y mostrar la ventana
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal que inicia la aplicación.
     * Llama a {@link Application#launch(String...)} para arrancar la aplicación JavaFX.
     *
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Application.launch();
    }
}
