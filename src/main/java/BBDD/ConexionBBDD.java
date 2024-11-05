package BBDD;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        Properties connConfig = new Properties();
        connConfig.setProperty("user", "root");
        connConfig.setProperty("password", "mypass");
        //la conexion en sí
        connection = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:33066/olimpiadas?serverTimezone=Europe/Madrid", connConfig);
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
}