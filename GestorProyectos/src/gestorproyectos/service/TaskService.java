/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.service;

import gestorproyectos.dao.TaskDB;
import gestorproyectosmodel.Task;
import java.util.List;

/**
 *
 * @author tatia
 */
public class TaskService {
   private final TaskDB taskDB = new TaskDB();

    public void asignarTarea(Task t) {
        if (t.getProgress() < 0 || t.getProgress() > 100) {
            throw new IllegalArgumentException("El progreso debe estar entre 0 y 100.");
        }
        taskDB.insert(t);
    }

    public List<Task> obtenerTareasPorProyecto(int projectId) {
        return taskDB.getByProject(projectId);
    }
}
