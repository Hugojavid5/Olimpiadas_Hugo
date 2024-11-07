package Dao;

import BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Olimpiada;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase donde se ejecutan las consultas relacionadas con la tabla Olimpiada en la base de datos.
 */
public class DaoOlimpiada {

    /**
     * Metodo que busca una olimpiada por medio de su ID.
     *
     * @param id ID de la olimpiada a buscar.
     * @return Olipiada encontrada o null si no se encuentra.
     */
    public static Olimpiada getOlimpiada(int id) {
        ConexionBBDD connection;
        Olimpiada olimpiada = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_olimpiada, nombre, anio, temporada, ciudad FROM Olimpiada WHERE id_olimpiada = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_olimpiada = rs.getInt("id_olimpiada");
                String nombre = rs.getString("nombre");
                int anio = rs.getInt("anio");
                String temporada = rs.getString("temporada");
                String ciudad = rs.getString("ciudad");
                olimpiada = new Olimpiada(id_olimpiada, nombre, anio, temporada, ciudad);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return olimpiada;
    }

    /**
     * Metodo que carga los datos de todas las olimpiadas y los devuelve como una lista observable para un TableView.
     *
     * @return Lista observable de olimpiadas.
     */
    public static ObservableList<Olimpiada> cargarListado() {
        ConexionBBDD connection;
        ObservableList<Olimpiada> olimpiadas = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_olimpiada, nombre, anio, temporada, ciudad FROM Olimpiada";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_olimpiada = rs.getInt("id_olimpiada");
                String nombre = rs.getString("nombre");
                int anio = rs.getInt("anio");
                String temporada = rs.getString("temporada");
                String ciudad = rs.getString("ciudad");
                Olimpiada olimpiada = new Olimpiada(id_olimpiada, nombre, anio, temporada, ciudad);
                olimpiadas.add(olimpiada);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return olimpiadas;
    }

    /**
     * Metodo que modifica los datos de una olimpiada en la base de datos.
     *
     * @param olimpiada      Instancia de la olimpiada con los datos actuales.
     * @param olimpiadaNuevo Nuevos datos de la olimpiada a modificar.
     * @return true si la modificación fue exitosa, false en caso contrario.
     */
    public static boolean modificar(Olimpiada olimpiada, Olimpiada olimpiadaNuevo) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Olimpiada SET nombre = ?, anio = ?, temporada = ?, ciudad = ? WHERE id_olimpiada = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, olimpiadaNuevo.getNombre());
            pstmt.setInt(2, olimpiadaNuevo.getAnio());
            pstmt.setString(3, olimpiadaNuevo.getTemporada().toString());
            pstmt.setString(4, olimpiadaNuevo.getCiudad());
            pstmt.setInt(5, olimpiada.getId_olimpiada());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Actualizado olimpiada");
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Metodo que crea una nueva olimpiada en la base de datos.
     *
     * @param olimpiada Instancia del modelo de olimpiada con los datos nuevos.
     * @return ID de la nueva olimpiada si la inserción fue exitosa, -1 en caso contrario.
     */
    public static int insertar(Olimpiada olimpiada) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Olimpiada (nombre, anio, temporada, ciudad) VALUES (?, ?, ?, ?)";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, olimpiada.getNombre());
            pstmt.setInt(2, olimpiada.getAnio());
            pstmt.setString(3, olimpiada.getTemporada().toString());
            pstmt.setString(4, olimpiada.getCiudad());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Nueva entrada en olimpiada");
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
     * Elimina una olimpiada en función del modelo Olimpiada proporcionado.
     *
     * @param olimpiada Olimpiada a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public static boolean eliminar(Olimpiada olimpiada) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Olimpiada WHERE id_olimpiada = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, olimpiada.getId_olimpiada());
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
     * Verifica si una olimpiada es eliminable, es decir, si no tiene eventos asociados.
     *
     * @param olimpiada La olimpiada a verificar.
     * @return true si la olimpiada no tiene eventos asociados y puede ser eliminada, false en caso contrario.
     */
    public static boolean esEliminable(Olimpiada olimpiada) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT count(*) as cont FROM Evento WHERE id_olimpiada = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, olimpiada.getId_olimpiada());
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
