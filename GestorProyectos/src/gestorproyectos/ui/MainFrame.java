/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.ui;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author tatia
 */
public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    
    public MainFrame() {
        setTitle("Gestor de Proyectos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initUI();
    }
    
    private void initUI() {
        tabbedPane = new JTabbedPane();
        
        // Crear pestañas
        ProyectosPanel proyectosPanel = new ProyectosPanel();
        TareasPanel tareasPanel = new TareasPanel();
        UsuariosPanel usuariosPanel = new UsuariosPanel();
        
        tabbedPane.addTab("Proyectos", proyectosPanel);
        tabbedPane.addTab("Tareas", tareasPanel);
        tabbedPane.addTab("Usuarios", usuariosPanel);
        
        add(tabbedPane);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}