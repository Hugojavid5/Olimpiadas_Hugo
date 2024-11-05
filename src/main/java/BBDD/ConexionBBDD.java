package BBDD;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
/**
 * Clase de conexión a la base de datos
 */
public class ConexionBBDD {
    private final Connection connection;
    /**
     * Es el constructor que se llama al crear un objeto de esta clase, lanzado la conexión
     *
     * @throws java.sql.SQLException Hay que controlar errores de SQL
     */
    public ConexionBBDD() throws SQLException {
        // los parametros de la conexion
        Properties configuracion = getConfiguracion();
        Properties connConfig = new Properties();
        connConfig.setProperty("user", configuracion.getProperty("user"));
        connConfig.setProperty("password", configuracion.getProperty("password"));
        //la conexion en sí
        connection = DriverManager.getConnection("jdbc:mariadb://" + configuracion.getProperty("address") + ":" + configuracion.getProperty("port") + "/" + configuracion.getProperty("database") + "?serverTimezone=Europe/Madrid", connConfig);
        connection.setAutoCommit(true);
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        connection.setAutoCommit(true);
    }
    /**
     * Esta clase devuelve la conexión creada
     *
     * @return una conexión a la BBDD
     */
    public Connection getConnection() {
        return connection;
    }
    /**
     * Metodo de cerrar la conexion con la base de datos
     *
     * @return La conexión cerrada.
     * @throws java.sql.SQLException Se lanza en caso de errores de SQL al cerrar la conexión.
     */
    public Connection closeConnection() throws SQLException{
        connection.close();
        return connection;
    }
    Properties configuracion = getConfiguracion();
    public static Properties getConfiguracion() {
        HashMap<String,String> map = new HashMap<String,String>();
        File f = new File("configuracion.properties");
        Properties properties;
        try {
            FileInputStream configFileReader=new FileInputStream(f);
            properties = new Properties();
            try {
                properties.load(configFileReader);
                configFileReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("configuracion.properties not found at config file path " + f.getPath());
        }
        return properties;
    }

}