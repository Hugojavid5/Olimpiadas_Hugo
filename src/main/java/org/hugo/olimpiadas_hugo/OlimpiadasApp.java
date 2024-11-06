package org.hugo.olimpiadas_hugo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Language.LanguageManager;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import  BBDD.ConexionBBDD;

import java.io.IOException;

/**
 * Clase donde se ejecuta la aplicación principal
 *
 * @author alesandroquirosgobbato
 */
public class OlimpiadasApp extends Application {
    /**
     * {@inheritDoc}
     * <p>
     * Función donde se carga y se muestra la ventana de la aplicación
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el idioma
        ResourceBundle bundle = LanguageManager.getInstance().getBundle();
        // Lanzar aplicación
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Inicio.fxml"), bundle);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Imagenes/olimpiadas.png")));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inicio - Olimpiadas");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch();
    }
}
