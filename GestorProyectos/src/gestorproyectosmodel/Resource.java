/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectosmodel;

/**
 *
 * @author tatia
 */
public class Resource {
     private int id;
    private int taskId;
    private String filePath;

    public Resource() {}

    public Resource(int id, int taskId, String filePath) {
        this.id = id;
        this.taskId = taskId;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
     @Override
    public String toString() {
        return filePath;
    }
}
