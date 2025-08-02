/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.dao;

import java.sql.*;
import java.util.*;
import sistemavuelos.model.Vuelo;
import sistemavuelos.util.Conexion;

/**
 *
 * @author tatia
 */
public class VueloDAO {
    public List<Vuelo> listarVuelos() {
        List<Vuelo> vuelos = new ArrayList<>();
        String sql = "SELECT * FROM vuelo";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vuelo v = new Vuelo(rs.getInt("id"), rs.getString("aerolinea"),
                        rs.getString("origen"), rs.getString("destino"),
                        rs.getDate("fecha"), rs.getTime("hora"),
                        rs.getDouble("precio"), rs.getInt("escalas"), rs.getInt("capacidad"));
                vuelos.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vuelos;
    }

    public void agregarVuelo(Vuelo v) {
        String sql = "INSERT INTO vuelo (aerolinea, origen, destino, fecha, hora, precio, escalas, capacidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getAerolinea());
            ps.setString(2, v.getOrigen());
            ps.setString(3, v.getDestino());
            ps.setDate(4, v.getFecha());
            ps.setTime(5, v.getHora());
            ps.setDouble(6, v.getPrecio());
            ps.setInt(7, v.getEscalas());
            ps.setInt(8, v.getCapacidad());
            ps.executeUpdate();
            System.out.println("✈ Vuelo agregado correctamente.");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
