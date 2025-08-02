/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestorproyectos;

import gestorproyectos.service.ProyectoService;
import gestorproyectos.ui.MainFrame;
import javax.swing.*;

public class GestorProyectos {
    public static void main(String[] args) {
        // Verificar conexión a BD
        ProyectoService proyectoService = new ProyectoService();
        try {
            proyectoService.listarProyectos(); // Test de conexión
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al conectar con la base de datos:\n" + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        // Iniciar interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
