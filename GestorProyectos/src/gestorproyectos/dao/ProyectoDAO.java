/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.dao;

import gestorproyectos.model.Proyecto;
import gestorproyectos.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tatia
 */
public class ProyectoDAO {
    private static final Logger LOGGER = Logger.getLogger(ProyectoDAO.class.getName());
    
    private static final String SQL_SELECT = "SELECT id, nombre, descripcion, fecha_inicio, fecha_fin, estado FROM proyectos";
    private static final String SQL_INSERT = "INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE proyectos SET nombre=?, descripcion=?, fecha_inicio=?, fecha_fin=?, estado=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM proyectos WHERE id=?";
    
    public List<Proyecto> obtenerTodos() {
        List<Proyecto> proyectos = new ArrayList<>();
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Proyecto proyecto = mapearProyecto(rs);
                proyectos.add(proyecto);
            }
            conn.commit();
        } catch (SQLException e) {
            ConexionBD.rollback();
            LOGGER.log(Level.SEVERE, "Error al obtener proyectos", e);
            throw new RuntimeException("Error al obtener proyectos de la base de datos", e);
        }
        return proyectos;
    }
    
    public Proyecto obtenerPorId(int id) {
        String sql = SQL_SELECT + " WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    conn.commit();
                    return mapearProyecto(rs);
                }
            }
        } catch (SQLException e) {
            ConexionBD.rollback();
            LOGGER.log(Level.SEVERE, "Error al obtener proyecto con ID: " + id, e);
            throw new RuntimeException("Error al obtener proyecto de la base de datos", e);
        }
        return null;
    }
    
    public boolean agregar(Proyecto proyecto) {
        if (proyecto == null) {
            throw new IllegalArgumentException("El proyecto no puede ser nulo");
        }
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            setParametrosProyecto(pstmt, proyecto);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La creación del proyecto falló, ninguna fila afectada");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    proyecto.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La creación del proyecto falló, no se obtuvo ID");
                }
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            ConexionBD.rollback();
            LOGGER.log(Level.SEVERE, "Error al agregar proyecto", e);
            throw new RuntimeException("Error al guardar proyecto en la base de datos", e);
        }
    }
    
    public boolean actualizar(Proyecto proyecto) {
        if (proyecto == null || proyecto.getId() <= 0) {
            throw new IllegalArgumentException("Proyecto inválido para actualización");
        }
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {
            
            setParametrosProyecto(pstmt, proyecto);
            pstmt.setInt(6, proyecto.getId());
            
            int affectedRows = pstmt.executeUpdate();
            conn.commit();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            ConexionBD.rollback();
            LOGGER.log(Level.SEVERE, "Error al actualizar proyecto con ID: " + proyecto.getId(), e);
            throw new RuntimeException("Error al actualizar proyecto en la base de datos", e);
        }
    }
    
    public boolean eliminar(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de proyecto inválido");
        }
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {
            
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            conn.commit();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            ConexionBD.rollback();
            LOGGER.log(Level.SEVERE, "Error al eliminar proyecto con ID: " + id, e);
            throw new RuntimeException("Error al eliminar proyecto de la base de datos", e);
        }
    }
    
    private Proyecto mapearProyecto(ResultSet rs) throws SQLException {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(rs.getInt("id"));
        proyecto.setNombre(rs.getString("nombre"));
        proyecto.setDescripcion(rs.getString("descripcion"));
        proyecto.setFechaInicio(rs.getDate("fecha_inicio"));
        proyecto.setFechaFin(rs.getDate("fecha_fin"));
        proyecto.setEstado(rs.getString("estado"));
        return proyecto;
    }
    
    private void setParametrosProyecto(PreparedStatement pstmt, Proyecto proyecto) throws SQLException {
        pstmt.setString(1, proyecto.getNombre());
        pstmt.setString(2, proyecto.getDescripcion());
        pstmt.setDate(3, new java.sql.Date(proyecto.getFechaInicio().getTime()));
        pstmt.setDate(4, new java.sql.Date(proyecto.getFechaFin().getTime()));
        pstmt.setString(5, proyecto.getEstado());
    }
}
