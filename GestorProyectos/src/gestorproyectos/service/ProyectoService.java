/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.service;

import gestorproyectos.dao.ProyectoDAO;
import gestorproyectos.model.Proyecto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tatia
 */
public class ProyectoService {
   private final ProyectoDAO proyectoDAO;
    
    public ProyectoService() {
        this.proyectoDAO = new ProyectoDAO();
    }
    
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoDAO.obtenerTodos();
    }
    
    public Proyecto obtenerProyectoPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de proyecto inválido");
        }
        return proyectoDAO.obtenerPorId(id);
    }
    
    public boolean crearProyecto(Proyecto proyecto) {
        validarProyecto(proyecto);
        return proyectoDAO.agregar(proyecto);
    }
    
    public boolean actualizarProyecto(Proyecto proyecto) {
        validarProyecto(proyecto);
        if (proyecto.getId() <= 0) {
            throw new IllegalArgumentException("Proyecto debe tener un ID válido para actualización");
        }
        return proyectoDAO.actualizar(proyecto);
    }
    
    public boolean eliminarProyecto(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de proyecto inválido");
        }
        return proyectoDAO.eliminar(id);
    }
    
    private void validarProyecto(Proyecto proyecto) {
        if (proyecto == null) {
            throw new IllegalArgumentException("El proyecto no puede ser nulo");
        }
        
        if (proyecto.getNombre() == null || proyecto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proyecto es requerido");
        }
        
        if (proyecto.getFechaInicio() == null) {
            throw new IllegalArgumentException("La fecha de inicio es requerida");
        }
        
        if (proyecto.getFechaFin() == null) {
            throw new IllegalArgumentException("La fecha de fin es requerida");
        }
        
        if (proyecto.getFechaInicio().after(proyecto.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        
        // Validar que la fecha de inicio no sea en el pasado (opcional)
        Date hoy = new Date();
        if (proyecto.getFechaInicio().before(hoy)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser en el pasado");
        }
    }
}
