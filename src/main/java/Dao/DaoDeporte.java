package Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deporte;
import BBDD.ConexionBBDD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase donde se ejecutan las consultas para la tabla Deporte.
 */
public class DaoDeporte {

    /**
     * Método que busca un deporte por medio de su id.
     *
     * @param id El id del deporte a buscar.
     * @return El objeto Deporte con los datos correspondientes o null si no se encuentra.
     */
    public static Deporte getDeporte(int id) {
        ConexionBBDD connection;
        Deporte deporte = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deporte,nombre FROM Deporte WHERE id_deporte = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_deporte = rs.getInt("id_deporte");
                String nombre = rs.getString("nombre");
                deporte = new Deporte(id_deporte, nombre);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deporte;
    }

    /**
     * Método que carga los datos de la tabla Deporte y los devuelve como una lista.
     * Los datos se utilizan para cargar en un TableView en la interfaz gráfica.
     *
     * @return Un ObservableList de deportes para cargar en un TableView.
     */
    public static ObservableList<Deporte> cargarListado() {
        ConexionBBDD connection;
        ObservableList<Deporte> deportes = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deporte,nombre FROM Deporte";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_deporte = rs.getInt("id_deporte");
                String nombre = rs.getString("nombre");
                Deporte deporte = new Deporte(id_deporte, nombre);
                deportes.add(deporte);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deportes;
    }

    /**
     * Método que modifica los datos de un deporte en la base de datos.
     *
     * @param deporte El deporte con los datos actuales.
     * @param deporteNuevo El deporte con los nuevos datos a modificar.
     * @return true si la actualización fue exitosa, false si ocurrió un error.
     */
    public static boolean modificar(Deporte deporte, Deporte deporteNuevo) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Deporte SET nombre = ? WHERE id_deporte = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, deporteNuevo.getNombre());
            pstmt.setInt(2, deporte.getId_deporte());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Actualizado deporte");
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Método que crea un nuevo deporte en la base de datos.
     *
     * @param deporte El objeto Deporte con los datos a insertar.
     * @return El id del nuevo deporte insertado, o -1 si ocurrió un error.
     */
    public static int insertar(Deporte deporte) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Deporte (nombre) VALUES (?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, deporte.getNombre());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Nueva entrada en deporte");
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    pstmt.close();
                    connection.closeConnection();
                    return id;
                }
            }
            pstmt.close();
            connection.closeConnection();
            return -1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Método que elimina un deporte de la base de datos utilizando el modelo Deporte.
     *
     * @param deporte El deporte a eliminar.
     * @return true si el deporte fue eliminado con éxito, false si ocurrió un error.
     */
    public static boolean eliminar(Deporte deporte) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Deporte WHERE id_deporte = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, deporte.getId_deporte());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            connection.closeConnection();
            System.out.println("Eliminado con éxito");
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Método que verifica si un deporte es eliminable, es decir, si no tiene eventos asociados.
     *
     * @param deporte El deporte a comprobar.
     * @return true si el deporte puede eliminarse (sin eventos asociados), false en caso contrario.
     */
    public static boolean esEliminable(Deporte deporte) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT count(*) as cont FROM Evento WHERE id_deporte = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, deporte.getId_deporte());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int cont = rs.getInt("cont");
                rs.close();
                connection.closeConnection();
                return (cont == 0);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
