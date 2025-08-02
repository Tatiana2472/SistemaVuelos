/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.dao;

import gestorproyectosmodel.User;
import gestorproyectos.dao.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tatia
 */
public class UserDB {
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            registrarError(e);
        }
        return users;
    }

    public void insert(User u) {
        String sql = "INSERT INTO users (name, email, role) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            registrarError(e);
        }
    }

    public void update(User u) {
        String sql = "UPDATE users SET name=?, email=?, role=? WHERE id=?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getRole());
            stmt.setInt(4, u.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            registrarError(e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            registrarError(e);
        }
    }

    private void registrarError(SQLException e) {
        System.err.println("Error en UserDB: " + e.getMessage());
    }
}
