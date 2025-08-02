/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.dao;

import java.sql.*;
import java.util.*;
import sistemavuelos.model.Reserva;
import sistemavuelos.util.Conexion;

/**
 *
 * @author tatia
 */
public class ReservaDAO {
     // Crear reserva (con validación para evitar duplicados)
    public boolean crearReserva(int usuarioId, int vueloId) {
        if (existeReserva(usuarioId, vueloId)) {
            System.out.println("⚠ El usuario ya tiene una reserva para este vuelo.");
            return false;
        }
        String sql = "INSERT INTO reserva (usuario_id, vuelo_id) VALUES (?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, vueloId);
            ps.executeUpdate();
            System.out.println("✅ Reserva creada correctamente.");
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // Verificar si ya existe una reserva para evitar duplicados
    private boolean existeReserva(int usuarioId, int vueloId) {
        String sql = "SELECT COUNT(*) FROM reserva WHERE usuario_id=? AND vuelo_id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, vueloId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // Listar reservas por usuario (itinerario)
    public List<Reserva> listarPorUsuario(int usuarioId) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reserva WHERE usuario_id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reservas.add(new Reserva(rs.getInt("id"), rs.getInt("usuario_id"), rs.getInt("vuelo_id"), rs.getTimestamp("fecha_reserva")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return reservas;
    }

    // Cancelar reserva
    public void cancelarReserva(int reservaId) {
        String sql = "DELETE FROM reserva WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservaId);
            ps.executeUpdate();
            System.out.println("🗑 Reserva cancelada.");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}

