package Language;

import BBDD.ConexionBBDD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de manejar los idiomas en la aplicación. Proporciona métodos para
 * cargar el archivo de propiedades de idioma y establecer el idioma actual.
 */
public class LanguageManager {
    private static LanguageManager instance;
    private Locale locale = new Locale.Builder().setLanguage(LanguageManager.getLanguage()).build();
    private ResourceBundle bundle;

    /**
     * Constructor privado de la clase que carga el recurso de idioma (bundle).
     * Utilizado en el patrón Singleton para asegurar una única instancia de la clase.
     */
    private LanguageManager() {
        loadResourceBundle();
    }

    /**
     * Obtiene el idioma configurado en el archivo de propiedades "lang.properties".
     *
     * @return el código de idioma configurado, obtenido del archivo de propiedades.
     * @throws RuntimeException si el archivo de propiedades no se encuentra.
     */
    public static String getLanguage() {
        HashMap<String, String> map = new HashMap<>();
        File f = new File("lang.properties");
        Properties properties;
        try {
            FileInputStream configFileReader = new FileInputStream(f);
            properties = new Properties();
            try {
                properties.load(configFileReader);
                configFileReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("lang.properties not found at config file path " + f.getPath());
        }
        return properties.getProperty("language");
    }

    /**
     * Crea y devuelve la instancia única de LanguageManager.
     *
     * @return la instancia de LanguageManager
     */
    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    /**
     * Carga el recurso de idioma (bundle) en función del idioma configurado en locale.
     */
    private void loadResourceBundle() {
        bundle = ResourceBundle.getBundle("languages/lang", locale);
    }

    /**
     * Establece un nuevo valor para locale y recarga el recurso de idioma en base a este.
     *
     * @param locale el nuevo objeto Locale a establecer
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        loadResourceBundle();
    }

    /**
     * Obtiene el recurso de idioma actual.
     *
     * @return el ResourceBundle que contiene los textos en el idioma configurado
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Obtiene el objeto Locale actual.
     *
     * @return el objeto Locale configurado
     */
    public Locale getLocale() {
        return locale;
    }
}
