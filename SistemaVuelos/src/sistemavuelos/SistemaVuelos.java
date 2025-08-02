/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemavuelos;

import javax.swing.SwingUtilities;
import sistemavuelos.view.VentanaLogin;

/**
 *
 * @author tatia
 */
public class SistemaVuelos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Ejecuta la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new VentanaLogin(); // Lanza la pantalla de login
        });
    }
}
