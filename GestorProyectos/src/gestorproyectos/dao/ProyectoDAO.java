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
    
    // CRUD methods
    public boolean agregar(Proyecto proyecto) {
        String sql = "INSERT INTO proyectos(nombre, descripcion, fecha_inicio, fecha_fin, estado) "
                   + "VALUES(?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setProyectoParameters(pstmt, proyecto);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        proyecto.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al agregar proyecto", ex);
        }
        return false;
    }
    
    public boolean actualizar(Proyecto proyecto) {
        String sql = "UPDATE proyectos SET nombre=?, descripcion=?, fecha_inicio=?, "
                   + "fecha_fin=?, estado=? WHERE id_proyecto=?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setProyectoParameters(pstmt, proyecto);
            pstmt.setInt(6, proyecto.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al actualizar proyecto", ex);
            return false;
        }
    }
    
    public boolean eliminar(int idProyecto) {
        String sql = "DELETE FROM proyectos WHERE id_proyecto=?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idProyecto);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar proyecto", ex);
            return false;
        }
    }
    
    public Proyecto obtenerPorId(int idProyecto) {
        String sql = "SELECT * FROM proyectos WHERE id_proyecto=?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idProyecto);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapProyectoFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al obtener proyecto", ex);
        }
        return null;
    }
    
    public List<Proyecto> listarTodos() {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT * FROM proyectos ORDER BY fecha_inicio DESC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                proyectos.add(mapProyectoFromResultSet(rs));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al listar proyectos", ex);
        }
        return proyectos;
    }
    
    public List<Proyecto> buscarPorNombre(String nombre) {
    List<Proyecto> proyectos = new ArrayList<>();
    String sql = "SELECT * FROM proyectos WHERE nombre LIKE ? ORDER BY fecha_inicio DESC";
    
    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, "%" + nombre + "%");
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                proyectos.add(mapProyectoFromResultSet(rs));
            }
        }
    } catch (SQLException ex) {
        LOGGER.log(Level.SEVERE, "Error al buscar proyectos por nombre", ex);
        }
     return proyectos;
    }
    
    // Métodos auxiliares
    private void setProyectoParameters(PreparedStatement pstmt, Proyecto proyecto) 
            throws SQLException {
        pstmt.setString(1, proyecto.getNombre());
        pstmt.setString(2, proyecto.getDescripcion());
        pstmt.setDate(3, new java.sql.Date(proyecto.getFechaInicio().getTime()));
        pstmt.setDate(4, proyecto.getFechaFin() != null ? 
            new java.sql.Date(proyecto.getFechaFin().getTime()) : null);
        pstmt.setString(5, proyecto.getEstado());
    }
    
    private Proyecto mapProyectoFromResultSet(ResultSet rs) throws SQLException {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(rs.getInt("id_proyecto"));
        proyecto.setNombre(rs.getString("nombre"));
        proyecto.setDescripcion(rs.getString("descripcion"));
        proyecto.setFechaInicio(rs.getDate("fecha_inicio"));
        proyecto.setFechaFin(rs.getDate("fecha_fin"));
        proyecto.setEstado(rs.getString("estado"));
        return proyecto;
    }
}
