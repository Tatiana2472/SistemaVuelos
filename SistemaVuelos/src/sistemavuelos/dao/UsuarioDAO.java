/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.dao;

import java.sql.*;
import java.util.*;
import sistemavuelos.model.Usuario;
import sistemavuelos.util.Conexion;
/**
 *
 * @author tatia
 */
public class UsuarioDAO {
    // Crear usuario
    public void registrarUsuario(Usuario u) {
        String sql = "INSERT INTO usuario (nombre, correo, password) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getPassword());
            ps.executeUpdate();
            System.out.println("✅ Usuario registrado con éxito.");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Autenticación (Login)
    public Usuario login(String correo, String password) {
        String sql = "SELECT * FROM usuario WHERE correo=? AND password=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo"), rs.getString("password"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
