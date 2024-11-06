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
 * Clase donde se ejecuta las consultas para la tabla Evento
 */
public class DaoEvento {
    /**
     * Metodo que busca un evento por medio de su id
     *
     * @param id id del evento a buscar
     * @return evento o null
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
                evento = new Evento(id_evento,nombre,olimpiada,deporte);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return evento;
    }

    /**
     * Metodo que carga los datos de la tabla Eventos y los devuelve para usarlos en un listado de eventos
     *
     * @return listado de eventos para cargar en un tableview
     */
    public static ObservableList<Evento> cargarListado() {
        ConexionBBDD connection;
        ObservableList<Evento> eventos = FXCollections.observableArrayList();
        try{
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
                Evento evento = new Evento(id_evento,nombre,olimpiada,deporte);
                eventos.add(evento);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return eventos;
    }

    /**
     * Metodo que modifica los datos de un evento en la BD
     *
     * @param evento		Instancia del evento con datos
     * @param eventoNuevo Nuevos datos del evento a modificar
     * @return			true/false
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
            System.out.println("Actualizado evento");
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Metodo que CREA un nuevo evento en la BD
     *
     * @param evento		Instancia del modelo evento con datos nuevos
     * @return			id/-1
     */
    public  static int insertar(Evento evento) {
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
            System.out.println("Nueva entrada en evento");
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
     * Elimina un evento en función del modelo Evento que le hayamos pasado
     *
     * @param evento Evento a eliminar
     * @return a boolean
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
            System.out.println("Eliminado con éxito");
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
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
                return (cont==0);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}