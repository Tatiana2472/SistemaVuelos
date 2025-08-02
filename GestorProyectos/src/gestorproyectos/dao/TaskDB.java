/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.dao;

import gestorproyectosmodel.Task;
import gestorproyectos.dao.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tatia
 */
public class TaskDB {
     public List<Task> getByProject(int projectId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE project_id = ? ORDER BY id";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getInt("project_id"),
                        rs.getInt("assigned_to"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("priority"),
                        rs.getInt("progress")
                    ));
                }
            }
        } catch (SQLException e) {
            registrarError(e);
        }
        return tasks;
    }

    public void insert(Task t) {
        String sql = "INSERT INTO tasks (project_id, assigned_to, title, description, status, priority, progress) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, t.getProjectId());
            stmt.setInt(2, t.getAssignedTo());
            stmt.setString(3, t.getTitle());
            stmt.setString(4, t.getDescription());
            stmt.setString(5, t.getStatus());
            stmt.setString(6, t.getPriority());
            stmt.setInt(7, t.getProgress());
            stmt.executeUpdate();
        } catch (SQLException e) {
            registrarError(e);
        }
    }

    public void update(Task t) {
        String sql = "UPDATE tasks SET project_id=?, assigned_to=?, title=?, description=?, status=?, priority=?, progress=? WHERE id=?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, t.getProjectId());
            stmt.setInt(2, t.getAssignedTo());
            stmt.setString(3, t.getTitle());
            stmt.setString(4, t.getDescription());
            stmt.setString(5, t.getStatus());
            stmt.setString(6, t.getPriority());
            stmt.setInt(7, t.getProgress());
            stmt.setInt(8, t.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            registrarError(e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE id=?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            registrarError(e);
        }
    }

    private void registrarError(SQLException e) {
        System.err.println("Error en TaskDB: " + e.getMessage());
    }
}
