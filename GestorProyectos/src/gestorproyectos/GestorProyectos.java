/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestorproyectos;

import gestorproyectos.ui.MainFrame;
import gestorproyectos.util.ExceptionHandler;
import javax.swing.SwingUtilities;

/**
 *
 * @author tatia
 */
public class GestorProyectos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ExceptionHandler.setupGlobalExceptionHandling();
    
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                ExceptionHandler.showErrorDialog(e);
            }
        });
    }
} 
