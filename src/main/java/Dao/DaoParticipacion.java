package Dao;

import BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deportista;
import model.Equipo;
import model.Evento;
import model.Participacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase encargada de ejecutar las consultas para la tabla Participacion en la base de datos.
 * Permite cargar, modificar, insertar y eliminar registros de participación.
 */
public class DaoParticipacion {

    /**
     * Método que carga los datos de la tabla Participacion y los devuelve como un listado de participaciones.
     * Este listado puede ser utilizado en un TableView para mostrar los datos.
     *
     * @return ObservableList<Participacion> listado de participaciones cargado desde la base de datos
     */
    public static ObservableList<Participacion> cargarListado() {
        ConexionBBDD connection;
        ObservableList<Participacion> participacions = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deportista, id_evento, id_equipo, edad, medalla FROM Participacion";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_deportista = rs.getInt("id_deportista");
                Deportista deportista = DaoDeportista.getDeportista(id_deportista);
                int id_evento = rs.getInt("id_evento");
                Evento evento = DaoEvento.getEvento(id_evento);
                int id_equipo = rs.getInt("id_equipo");
                Equipo equipo = DaoEquipo.getEquipo(id_equipo);
                int edad = rs.getInt("edad");
                String medalla = rs.getString("medalla");
                Participacion participacion = new Participacion(deportista, evento, equipo, edad, medalla);
                participacions.add(participacion);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return participacions;
    }

    /**
     * Método que modifica los datos de una participación en la base de datos.
     *
     * @param participacion      la participación actual con los datos previos
     * @param participacionNuevo los nuevos datos de la participación
     * @return true si la modificación fue exitosa, false en caso contrario
     */
    public static boolean modificar(Participacion participacion, Participacion participacionNuevo) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Participacion SET id_deportista = ?, id_evento = ?, id_equipo = ?, edad = ?, medalla = ? WHERE id_deportista = ? AND id_evento = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, participacionNuevo.getDeportista().getId_deportista());
            pstmt.setInt(2, participacionNuevo.getEvento().getId_evento());
            pstmt.setInt(3, participacionNuevo.getEquipo().getId_equipo());
            pstmt.setInt(4, participacionNuevo.getEdad());
            pstmt.setString(5, participacionNuevo.getMedalla());
            pstmt.setInt(6, participacion.getDeportista().getId_deportista());
            pstmt.setInt(7, participacion.getEvento().getId_evento());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Actualizado participación");
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Método que inserta una nueva participación en la base de datos.
     *
     * @param participacion la nueva participación a insertar en la base de datos
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    public static boolean insertar(Participacion participacion) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Participacion (id_deportista, id_evento, id_equipo, edad, medalla) VALUES (?, ?, ?, ?, ?)";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, participacion.getDeportista().getId_deportista());
            pstmt.setInt(2, participacion.getEvento().getId_evento());
            pstmt.setInt(3, participacion.getEquipo().getId_equipo());
            pstmt.setInt(4, participacion.getEdad());
            pstmt.setString(5, participacion.getMedalla());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Nueva entrada en participación");
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Método que elimina una participación de la base de datos en función de los datos de la participación.
     *
     * @param participacion la participación que se desea eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public static boolean eliminar(Participacion participacion) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Participacion WHERE id_deportista = ? AND id_evento = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, participacion.getDeportista().getId_deportista());
            pstmt.setInt(2, participacion.getEvento().getId_evento());
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

