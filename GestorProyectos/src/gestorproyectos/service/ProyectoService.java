/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.service;

import gestorproyectos.dao.ProyectoDAO;
import gestorproyectos.dao.TareaDAO;
import gestorproyectos.model.Proyecto;
import gestorproyectos.model.Tarea;
import java.util.List;

/**
 *
 * @author tatia
 */
public class ProyectoService {
   private final ProyectoDAO proyectoDAO;
    private final TareaDAO tareaDAO;
    
    public ProyectoService() {
        this.proyectoDAO = new ProyectoDAO();
        this.tareaDAO = new TareaDAO();
    }
    
    public boolean crearProyecto(Proyecto proyecto) {
        if (!validarProyecto(proyecto)) return false;
        return proyectoDAO.agregar(proyecto);
    }
    
    public boolean actualizarProyecto(Proyecto proyecto) {
        if (!validarProyecto(proyecto)) return false;
        return proyectoDAO.actualizar(proyecto);
    }
    
    public boolean eliminarProyecto(int idProyecto) {
        // Primero eliminamos las tareas asociadas
        tareaDAO.eliminarPorProyecto(idProyecto);
        return proyectoDAO.eliminar(idProyecto);
    }
    
    public Proyecto obtenerProyecto(int idProyecto) {
        Proyecto proyecto = proyectoDAO.obtenerPorId(idProyecto);
        if (proyecto != null) {
            proyecto.setTareas(tareaDAO.listarPorProyecto(idProyecto));
        }
        return proyecto;
    }
    
    public List<Proyecto> listarProyectos() {
        List<Proyecto> proyectos = proyectoDAO.listarTodos();
        for (Proyecto p : proyectos) {
            p.setTareas(tareaDAO.listarPorProyecto(p.getId()));
        }
        return proyectos;
    }
    
    public List<Proyecto> buscarProyectosPorNombre(String nombre) {
        List<Proyecto> proyectos = proyectoDAO.buscarPorNombre(nombre);
        for (Proyecto p : proyectos) {
            p.setTareas(tareaDAO.listarPorProyecto(p.getId()));
        }
        return proyectos;
    }
    
    private boolean validarProyecto(Proyecto proyecto) {
        if (proyecto.getNombre() == null || proyecto.getNombre().trim().isEmpty()) {
            return false;
        }
        if (proyecto.getFechaInicio() == null) {
            return false;
        }
        if (proyecto.getFechaFin() != null && 
            proyecto.getFechaFin().before(proyecto.getFechaInicio())) {
            return false;
        }
        return true;
    }
}
