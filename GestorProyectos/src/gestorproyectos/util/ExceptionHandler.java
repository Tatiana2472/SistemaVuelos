/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.util;
import javax.swing.*;
/**
 *
 * @author tatia
 */
public class ExceptionHandler {
    public static void setupGlobalExceptionHandling() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            showErrorDialog(throwable);
        });
    }
    
    public static void showErrorDialog(Throwable throwable) {
        String errorMsg = "Error inesperado:\n" + throwable.getMessage();
        
        // Log detallado para desarrollo
        System.err.println("Error no controlado:");
        throwable.printStackTrace();
        
        // Mensaje amigable para el usuario
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, 
                errorMsg, 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        });
    }
}

