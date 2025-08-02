/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.view;

import gestorproyectos.dao.ProjectDB;
import gestorproyectosmodel.Project;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author tatia
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Gestor de Proyectos");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Proyectos", new ProjectPanel());
        tabs.addTab("Tareas", new TaskPanel());
        tabs.addTab("Usuarios", new UserPanel());

        add(tabs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}