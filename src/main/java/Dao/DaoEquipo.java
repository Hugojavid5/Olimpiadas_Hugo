package Dao;
import BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Equipo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que contiene los métodos para ejecutar consultas a la base de datos para la tabla Equipo.
 * Permite obtener, insertar, modificar, eliminar equipos y comprobar si un equipo es eliminable.
 */
public class DaoEquipo {

    /**
     * Busca un equipo en la base de datos por su ID.
     *
     * @param id El ID del equipo que se desea buscar.
     * @return El objeto Equipo correspondiente o null si no se encuentra.
     */
    public static Equipo getEquipo(int id) {
        ConexionBBDD connection;
        Equipo equipo = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_equipo,nombre,iniciales FROM Equipo WHERE id_equipo = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_equipo = rs.getInt("id_equipo");
                String nombre = rs.getString("nombre");
                String iniciales = rs.getString("iniciales");
                equipo = new Equipo(id_equipo,nombre,iniciales);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return equipo;
    }

    /**
     * Carga todos los equipos desde la base de datos y devuelve una lista observable para usar en un TableView.
     *
     * @return Un ObservableList de objetos Equipo.
     */
    public static ObservableList<Equipo> cargarListado() {
        ConexionBBDD connection;
        ObservableList<Equipo> equipos = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_equipo,nombre,iniciales FROM Equipo";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_equipo = rs.getInt("id_equipo");
                String nombre = rs.getString("nombre");
                String iniciales = rs.getString("iniciales");
                Equipo equipo = new Equipo(id_equipo,nombre,iniciales);
                equipos.add(equipo);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return equipos;
    }

    /**
     * Modifica los datos de un equipo en la base de datos.
     *
     * @param equipo El equipo a modificar, que contiene los datos antiguos.
     * @param equipoNuevo Un objeto Equipo con los nuevos datos a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public static boolean modificar(Equipo equipo, Equipo equipoNuevo) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Equipo SET nombre = ?,iniciales = ? WHERE id_equipo = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, equipoNuevo.getNombre());
            pstmt.setString(2, equipoNuevo.getIniciales());
            pstmt.setInt(3, equipo.getId_equipo());
            int filasAfectadas = pstmt.executeUpdate();

            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Inserta un nuevo equipo en la base de datos.
     *
     * @param equipo El objeto Equipo que contiene los datos a insertar.
     * @return El ID generado del nuevo equipo si la inserción fue exitosa, -1 en caso contrario.
     */
    public static int insertar(Equipo equipo) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Equipo (nombre,iniciales) VALUES (?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, equipo.getNombre());
            pstmt.setString(2, equipo.getIniciales());
            int filasAfectadas = pstmt.executeUpdate();
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
     * Elimina un equipo de la base de datos.
     *
     * @param equipo El objeto Equipo a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public static boolean eliminar(Equipo equipo) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Equipo WHERE id_equipo = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, equipo.getId_equipo());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un equipo se puede eliminar. Un equipo solo es eliminable si no tiene participaciones asociadas.
     *
     * @param equipo El equipo a verificar.
     * @return true si el equipo no tiene participaciones asociadas y puede ser eliminado, false en caso contrario.
     */
    public static boolean esEliminable(Equipo equipo) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT count(*) as cont FROM Participacion WHERE id_equipo = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, equipo.getId_equipo());
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
