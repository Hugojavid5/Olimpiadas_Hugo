package Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deporte;
import BBDD.ConexionBBDD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase donde se ejecuta las consultas para la tabla Deporte
 */
public class DaoDeporte {
    /**
     * Metodo que busca una deporte por medio de su id
     *
     * @param id id del deporte a buscar
     * @return deporte o null
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
                deporte = new Deporte(id_deporte,nombre);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deporte;
    }

    /**
     * Metodo que carga los datos de la tabla Deportes y los devuelve para usarlos en un listado de deportes
     *
     * @return listado de deportes para cargar en un tableview
     */
    public static ObservableList<Deporte> cargarListado() {
        ConexionBBDD connection;
        ObservableList<Deporte> deportes = FXCollections.observableArrayList();
        try{
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deporte,nombre FROM Deporte";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_deporte = rs.getInt("id_deporte");
                String nombre = rs.getString("nombre");
                Deporte deporte = new Deporte(id_deporte,nombre);
                deportes.add(deporte);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deportes;
    }

    /**
     * Metodo que modifica los datos de una deporte en la BD
     *
     * @param deporte		Instancia de la deporte con datos
     * @param deporteNuevo Nuevos datos del deporte a modificar
     * @return			true/false
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
     * Metodo que CREA un nuevo deporte en la BD
     *
     * @param deporte		Instancia del modelo deporte con datos nuevos
     * @return			id/-1
     */
    public  static int insertar(Deporte deporte) {
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
     * Elimina un deporte en función del modelo Deporte que le hayamos pasado
     *
     * @param deporte Deporte a eliminar
     * @return a boolean
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
}