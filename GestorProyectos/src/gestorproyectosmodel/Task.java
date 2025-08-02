/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectosmodel;

/**
 *
 * @author tatia
 */
public class Task {
    private int id;
    private int projectId;
    private int assignedTo;
    private String title;
    private String description;
    private String status; // Pendiente, En Progreso, Completada
    private String priority; // Alta, Media, Baja
    private int progress; // 0 a 100

    public Task() {}

    public Task(int id, int projectId, int assignedTo, String title, String description,
                String status, String priority, int progress) {
        this.id = id;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    
   @Override
    public String toString() {
        return title + " (" + progress + "%)";
    }
}