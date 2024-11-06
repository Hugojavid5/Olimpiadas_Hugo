package Language;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase dedicada al cambio de idioma de la aplicación. Esta clase se encarga de
 * recargar la interfaz de usuario en el idioma seleccionado por el usuario.
 */
public class LanguageSwitcher {
    private Stage stage;

    /**
     * Constructor de la clase que inicializa el escenario (stage) de la aplicación.
     *
     * @param stage el escenario de la aplicación
     */
    public LanguageSwitcher(Stage stage) {
        this.stage = stage;
    }

    /**
     * Cambia el idioma de la aplicación al actualizar el Locale en LanguageManager y recargar
     * la interfaz en el idioma seleccionado.
     *
     * @param locale el nuevo objeto Locale que especifica el idioma deseado
     */
    public void switchLanguage(Locale locale) {
        // Actualiza el Locale en el LanguageManager
        LanguageManager.getInstance().setLocale(locale);
        // Obtiene el ResourceBundle actualizado
        ResourceBundle bundle = LanguageManager.getInstance().getBundle();
        try {
            // Recarga el archivo FXML con el nuevo ResourceBundle
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Inicio.fxml"), bundle);
            Parent root = loader.load();
            stage.setTitle(bundle.getString("app.name"));
            // Actualiza la escena con la nueva raíz (nuevo idioma)
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
