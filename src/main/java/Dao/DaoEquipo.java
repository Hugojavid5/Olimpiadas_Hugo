package Dao;
import BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Equipo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase donde se ejecuta las consultas para la tabla Equipo
 */
public class DaoEquipo {
    /**
     * Metodo que busca una equipo por medio de su id
     *
     * @param id id del equipo a buscar
     * @return equipo o null
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
     * Metodo que carga los datos de la tabla Equipos y los devuelve para usarlos en un listado de equipos
     *
     * @return listado de equipos para cargar en un tableview
     */
    public static ObservableList<Equipo> cargarListado() {
        ConexionBBDD connection;
        ObservableList<Equipo> equipos = FXCollections.observableArrayList();
        try{
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
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return equipos;
    }

    /**
     * Metodo que modifica los datos de una equipo en la BD
     *
     * @param equipo		Instancia del equipo con datos
     * @param equipoNuevo Nuevos datos del equipo a modificar
     * @return			true/false
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
            System.out.println("Actualizado equipo");
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Metodo que CREA un nuevo equipo en la BD
     *
     * @param equipo		Instancia del modelo equipo con datos nuevos
     * @return			id/-1
     */
    public  static int insertar(Equipo equipo) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Equipo (nombre,iniciales) VALUES (?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, equipo.getNombre());
            pstmt.setString(2, equipo.getIniciales());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Nueva entrada en equipo");
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
     * Elimina una equipo en función del modelo Equipo que le hayamos pasado
     *
     * @param equipo Equipo a eliminar
     * @return a boolean
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
            System.out.println("Eliminado con éxito");
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}