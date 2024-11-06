package Dao;

import BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deportista;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

/**
 * Clase donde se ejecuta las consultas para la tabla Deportista
 */
public class DaoDeportista {
    /**
     * Metodo que busca un deportista por medio de su id
     *
     * @param id id del deportista a buscar
     * @return deportista o null
     */
    public static Deportista getDeportista(int id) {
        ConexionBBDD connection;
        Deportista deportista = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deportista,nombre,sexo,peso,altura,foto FROM Deportista WHERE id_deportista = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_deportista = rs.getInt("id_deportista");
                String nombre = rs.getString("nombre");
                char sexo = rs.getString("sexo").charAt(0);
                int peso = rs.getInt("peso");
                int altura = rs.getInt("altura");
                Blob foto = rs.getBlob("foto");
                deportista = new Deportista(id_deportista,nombre,sexo,peso,altura,foto);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deportista;
    }

    /**
     * Metodo que carga los datos de la tabla Deportistas y los devuelve para usarlos en un listado de deportistas
     *
     * @return listado de deportistas para cargar en un tableview
     */
    public static ObservableList<Deportista> cargarListado() {
        ConexionBBDD connection;
        ObservableList<Deportista> deportistas = FXCollections.observableArrayList();
        try{
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deportista,nombre,sexo,peso,altura,foto FROM Deportista";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_deportista = rs.getInt("id_deportista");
                String nombre = rs.getString("nombre");
                char sexo = rs.getString("sexo").charAt(0);
                int peso = rs.getInt("peso");
                int altura = rs.getInt("altura");
                Blob foto = rs.getBlob("foto");
                Deportista deportista = new Deportista(id_deportista,nombre,sexo,peso,altura,foto);
                deportistas.add(deportista);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deportistas;
    }

    /**
     * Metodo que modifica los datos de un deportista en la BD
     *
     * @param deportista		Instancia del deportista con datos
     * @param deportistaNuevo Nuevos datos del deportista a modificar
     * @return			true/false
     */
    public static boolean modificar(Deportista deportista, Deportista deportistaNuevo) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Deportista SET nombre = ?,sexo = ?,peso = ?,altura = ?,foto = ? WHERE id_deportista = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, deportistaNuevo.getNombre());
            pstmt.setString(2, deportistaNuevo.getSexo() + "");
            pstmt.setInt(3, deportistaNuevo.getPeso());
            pstmt.setInt(4, deportistaNuevo.getAltura());
            pstmt.setBlob(5, deportistaNuevo.getFoto());
            pstmt.setInt(6, deportista.getId_deportista());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Actualizado deportista");
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Metodo que CREA un nuevo deportista en la BD
     *
     * @param deportista		Instancia del modelo deportista con datos nuevos
     * @return			id/-1
     */
    public  static int insertar(Deportista deportista) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Deportista (nombre,sexo,peso,altura,fotos) VALUES (?,?,?,?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, deportista.getNombre());
            pstmt.setString(2, deportista.getSexo() + "");
            pstmt.setInt(3, deportista.getPeso());
            pstmt.setInt(4, deportista.getAltura());
            pstmt.setBlob(5, deportista.getFoto());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Nueva entrada en deportista");
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
     * Elimina un deportista en función del modelo Deportista que le hayamos pasado
     *
     * @param deportista Deportista a eliminar
     * @return a boolean
     */
    public static boolean eliminar(Deportista deportista) {
        ConexionBBDD connection;
        PreparedStatement pstmt;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Deportista WHERE id_deportista = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, deportista.getId_deportista());
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
    public static boolean esEliminable(Deportista deportista) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT count(*) as cont FROM Participacion WHERE id_deportista = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, deportista.getId_deportista());
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
    public static Blob convertFileToBlob(File file) throws SQLException, IOException {
        ConexionBBDD connection = new ConexionBBDD();
        // Open a connection to the database
        try (Connection conn = connection.getConnection();
             FileInputStream inputStream = new FileInputStream(file)) {
            // Create Blob
            Blob blob = conn.createBlob();
            // Write the file's bytes to the Blob
            byte[] buffer = new byte[1024];
            int bytesRead;
            try (var outputStream = blob.setBinaryStream(1)) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            return blob;
        }
    }

}