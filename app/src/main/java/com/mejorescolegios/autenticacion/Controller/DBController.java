package com.mejorescolegios.autenticacion.Controller;

import com.mejorescolegios.autenticacion.Model.Contacto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBController {

    // URL de conexión de MySQL
    private static final String URL = "jdbc:mysql://192.168.68.3/autenticator?useSSL=false";
    private static final String USER = "remote";
    private static final String PASSWORD = "Lola12102010";

    // Método para obtener la conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para insertar un contacto
    public void insertarContacto(Contacto contacto) {
        String query = "INSERT INTO contacto (id_user) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contacto.getUidUser());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todos los contactos
    public List<Contacto> obtenerContactos() {
        List<Contacto> contactos = new ArrayList<>();
        String query = "SELECT id, id_user FROM contacto";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String idUser = rs.getString("id_user");
                contactos.add(new Contacto(id, idUser));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactos;
    }

    // Método para actualizar un contacto
    public void actualizarContacto(Contacto contacto) {
        String query = "UPDATE contacto SET id_user = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contacto.getUidUser());
            stmt.setInt(2, contacto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un contacto por ID
    public void eliminarContacto(int id) {
        String query = "DELETE FROM contacto WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener los contactos de un usuario
    public List<Contacto> obtenerContactosPorUsuario(String idUser) {
        List<Contacto> contactos = new ArrayList<>();
        String query = "SELECT id, id_user FROM contacto WHERE id_user = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idUser);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String idUsuario = rs.getString("id_user");
                    contactos.add(new Contacto(id, idUsuario));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactos;
    }
}

