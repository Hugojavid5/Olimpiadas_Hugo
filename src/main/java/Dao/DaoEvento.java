package Dao;

import BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deporte;
import model.Evento;
import model.Olimpiada;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase donde se ejecutan las consultas para la tabla Evento.
 * Esta clase contiene métodos para buscar, cargar, modificar, insertar, eliminar
 * y verificar la eliminación de eventos en la base de datos.
 */
public class DaoEvento {

    /**
     * Metodo que busca un evento por medio de su id.
     *
     * @param id id del evento a buscar
     * @return evento o null si no se encuentra
     */
    public static Evento getEvento(int id) {
        ConexionBBDD connection;
        Evento evento = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_evento,nombre,id_olimpiada,id_deporte FROM Evento WHERE id_evento = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_evento = rs.getInt("id_evento");
                String nombre = rs.getString("nombre");
                int id_olimpiada = rs.getInt("id_olimpiada");
                Olimpiada olimpiada = DaoOlimpiada.getOlimpiada(id_olimpiada);
                int id_deporte = rs.getInt("id_deporte");
                Deporte deporte = DaoDeporte.getDeporte(id_deporte);
                evento = new Evento(id_evento, nombre, olimpiada, deporte);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return evento;
    }

    /**
     * Metodo que carga los datos de la tabla Eventos y los devuelve en un listado
     * para usarlos en un TableView.
     *
     * @return listado de eventos
     */
    public static ObservableList<Evento> cargarListado() {
        ConexionBBDD connection;
        ObservableList<Evento> eventos = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_evento,nombre,id_olimpiada,id_deporte FROM Evento";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_evento = rs.getInt("id_evento");
                String nombre = rs.getString("nombre");
                int id_olimpiada = rs.getInt("id_olimpiada");
                Olimpiada olimpiada = DaoOlimpiada.getOlimpiada(id_olimpiada);
                int id_deporte = rs.getInt("id_deporte");
                Deporte deporte = DaoDeporte.getDeporte(id_deporte);
                Evento evento = new Evento(id_evento, nombre, olimpiada, deporte);
                eventos.add(evento);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return eventos;
    }

    /**
     * Metodo que modifica los datos de un evento en la BD.
     *
     * @param evento     Instancia del evento con datos actuales
     * @param eventoNuevo Nuevos datos del evento a modificar
     * @return true si la actualización fue exitosa, false si hubo un error
     */
    public static boolean modificar(Evento evento, Evento eventoNuevo) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Evento SET nombre = ?,id_olimpiada = ?,id_deporte = ? WHERE id_evento = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, eventoNuevo.getNombre());
            pstmt.setInt(2, eventoNuevo.getOlimpiada().getId_olimpiada());
            pstmt.setInt(3, eventoNuevo.getDeporte().getId_deporte());
            pstmt.setInt(4, evento.getId_evento());
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
     * Metodo que crea un nuevo evento en la BD.
     *
     * @param evento Instancia del modelo evento con datos nuevos
     * @return id del nuevo evento o -1 si hubo un error
     */
    public static int insertar(Evento evento) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Evento (nombre,id_olimpiada,id_deporte) VALUES (?,?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, evento.getNombre());
            pstmt.setInt(2, evento.getOlimpiada().getId_olimpiada());
            pstmt.setInt(3, evento.getDeporte().getId_deporte());
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
     * Elimina un evento de la base de datos.
     *
     * @param evento Evento a eliminar
     * @return true si la eliminación fue exitosa, false si hubo un error
     */
    public static boolean eliminar(Evento evento) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Evento WHERE id_evento = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, evento.getId_evento());
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
     * Verifica si un evento puede ser eliminado de la base de datos.
     * Un evento no puede ser eliminado si tiene participaciones asociadas.
     *
     * @param evento Evento a verificar
     * @return true si el evento puede ser eliminado, false si tiene participaciones asociadas
     */
    public static boolean esEliminable(Evento evento) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT count(*) as cont FROM Participacion WHERE id_evento = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, evento.getId_evento());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int cont = rs.getInt("cont");
                rs.close();
                connection.closeConnection();
                return cont == 0;
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
