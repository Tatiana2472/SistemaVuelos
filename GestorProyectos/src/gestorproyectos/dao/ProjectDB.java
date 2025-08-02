/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.dao;

import gestorproyectos.dao.ConexionDB;
import gestorproyectosmodel.Project;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author tatia
 */
public class ProjectDB {
     public List<Project> getAll() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects ORDER BY id";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                projects.add(new Project(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("start_date").toString(),
                    rs.getDate("end_date").toString()
                ));
            }
        } catch (SQLException e) {
            registrarError(e);
        }
        return projects;
    }

    public void insert(Project p) {
        String sql = "INSERT INTO projects (name, description, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getDescription());
            stmt.setDate(3, Date.valueOf(p.getStartDate()));
            stmt.setDate(4, Date.valueOf(p.getEndDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            registrarError(e);
        }
    }

    public void update(Project p) {
        String sql = "UPDATE projects SET name=?, description=?, start_date=?, end_date=? WHERE id=?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getDescription());
            stmt.setDate(3, Date.valueOf(p.getStartDate()));
            stmt.setDate(4, Date.valueOf(p.getEndDate()));
            stmt.setInt(5, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            registrarError(e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM projects WHERE id=?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            registrarError(e);
        }
    }

    private void registrarError(SQLException e) {
        // Aquí puedes insertar el error en una tabla logs, o imprimirlo
        System.err.println("Error en ProjectDB: " + e.getMessage());
    }
}
