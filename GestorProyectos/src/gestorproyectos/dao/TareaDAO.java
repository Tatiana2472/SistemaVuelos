/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.dao;

import gestorproyectos.model.Tarea;
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
public class TareaDAO {
    private static final Logger LOGGER = Logger.getLogger(TareaDAO.class.getName());
    
    // Método para agregar una nueva tarea
    public boolean agregar(Tarea tarea) {
        String sql = "INSERT INTO tareas(id_proyecto, nombre, descripcion, fecha_inicio, "
                   + "fecha_fin, prioridad, estado, porcentaje_completado) "
                   + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setTareaParameters(pstmt, tarea);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        tarea.setIdTarea(rs.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al agregar tarea", ex);
        }
        return false;
    }
    
    // Método para actualizar una tarea existente
    public boolean actualizar(Tarea tarea) {
        String sql = "UPDATE tareas SET id_proyecto=?, nombre=?, descripcion=?, "
                   + "fecha_inicio=?, fecha_fin=?, prioridad=?, estado=?, "
                   + "porcentaje_completado=? WHERE id_tarea=?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setTareaParameters(pstmt, tarea);
            pstmt.setInt(9, tarea.getIdTarea());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al actualizar tarea", ex);
            return false;
        }
    }
    
    // Método para eliminar una tarea por ID
    public boolean eliminar(int idTarea) {
        String sql = "DELETE FROM tareas WHERE id_tarea=?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idTarea);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar tarea", ex);
            return false;
        }
    }
    
    // Método para eliminar todas las tareas de un proyecto
    public boolean eliminarPorProyecto(int idProyecto) {
        String sql = "DELETE FROM tareas WHERE id_proyecto=?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idProyecto);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar tareas del proyecto", ex);
            return false;
        }
    }
    
    // Método para obtener una tarea por ID
    public Tarea obtenerPorId(int idTarea) {
        String sql = "SELECT * FROM tareas WHERE id_tarea=?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idTarea);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapTareaFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al obtener tarea", ex);
        }
        return null;
    }
    
    // Método para listar todas las tareas de un proyecto
    public List<Tarea> listarPorProyecto(int idProyecto) {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM tareas WHERE id_proyecto=? ORDER BY fecha_inicio";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idProyecto);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tareas.add(mapTareaFromResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al listar tareas del proyecto", ex);
        }
        return tareas;
    }
    
    // Método para listar tareas asignadas a un usuario
    public List<Tarea> listarPorUsuario(int idUsuario) {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT t.* FROM tareas t "
                   + "JOIN asignaciones a ON t.id_tarea = a.id_tarea "
                   + "WHERE a.id_usuario=? ORDER BY t.fecha_inicio";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tareas.add(mapTareaFromResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al listar tareas del usuario", ex);
        }
        return tareas;
    }
    
    // Método para buscar tareas por nombre o descripción
    public List<Tarea> buscar(String criterio) {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM tareas WHERE nombre LIKE ? OR descripcion LIKE ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + criterio + "%");
            pstmt.setString(2, "%" + criterio + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tareas.add(mapTareaFromResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar tareas", ex);
        }
        return tareas;
    }
    
    // Método para actualizar el progreso de una tarea
    public boolean actualizarProgreso(int idTarea, int porcentaje) {
        String sql = "UPDATE tareas SET porcentaje_completado=?, "
                   + "estado=CASE WHEN ?>=100 THEN 'Completado' ELSE estado END "
                   + "WHERE id_tarea=?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, porcentaje);
            pstmt.setInt(2, porcentaje);
            pstmt.setInt(3, idTarea);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al actualizar progreso de tarea", ex);
            return false;
        }
    }
    
    // Métodos auxiliares privados
    private void setTareaParameters(PreparedStatement pstmt, Tarea tarea) 
            throws SQLException {
        pstmt.setInt(1, tarea.getIdProyecto());
        pstmt.setString(2, tarea.getNombre());
        pstmt.setString(3, tarea.getDescripcion());
        pstmt.setDate(4, new java.sql.Date(tarea.getFechaInicio().getTime()));
        pstmt.setDate(5, tarea.getFechaFin() != null ? 
            new java.sql.Date(tarea.getFechaFin().getTime()) : null);
        pstmt.setString(6, tarea.getPrioridad());
        pstmt.setString(7, tarea.getEstado());
        pstmt.setInt(8, tarea.getPorcentajeCompletado());
    }
    
    private Tarea mapTareaFromResultSet(ResultSet rs) throws SQLException {
        Tarea tarea = new Tarea();
        tarea.setIdTarea(rs.getInt("id_tarea"));
        tarea.setIdProyecto(rs.getInt("id_proyecto"));
        tarea.setNombre(rs.getString("nombre"));
        tarea.setDescripcion(rs.getString("descripcion"));
        tarea.setFechaInicio(rs.getDate("fecha_inicio"));
        tarea.setFechaFin(rs.getDate("fecha_fin"));
        tarea.setPrioridad(rs.getString("prioridad"));
        tarea.setEstado(rs.getString("estado"));
        tarea.setPorcentajeCompletado(rs.getInt("porcentaje_completado"));
        return tarea;
    }
}
