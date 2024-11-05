package Language;

import BBDD.ConexionBBDD;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de manejar los idiomas
 */
public class LanguageManager {
    private static LanguageManager instance;
    private Locale locale = new Locale.Builder().setLanguage(ConexionBBDD.getConfiguracion().getProperty("language")).build();
    private ResourceBundle bundle;
    /**
     * Constructor de la clase que carga el bundle
     */
    private LanguageManager() {
        loadResourceBundle();
    }
    /**
     * Crea una instancia de LanguageManager y la devuelve
     *
     * @return instancia de LanguageManager
     */
    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }
    /**
     * Función que carga el bundle
     */
    private void loadResourceBundle() {
        bundle = ResourceBundle.getBundle("languages/lang", locale);
    }
    /**
     * Setter de locale
     *
     * @param locale nuevo
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        loadResourceBundle();
    }
    /**
     * Getter de bundle
     *
     * @return bundle
     */
    public ResourceBundle getBundle() {
        return bundle;
    }
    /**
     * Getter de locale
     *
     * @return locale
     */
    public Locale getLocale() {
        return locale;
    }
}
