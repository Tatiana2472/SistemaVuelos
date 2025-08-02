/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author tatia
 */
public class ProyectosPanel extends JPanel {
    private JTable tablaProyectos;
    private JButton btnAgregar, btnEditar, btnEliminar;
    
    public ProyectosPanel() {
        setLayout(new BorderLayout());
        
        // Tabla de proyectos
        String[] columnas = {"ID", "Nombre", "Fecha Inicio", "Fecha Fin", "Estado"};
        Object[][] datos = {}; // Aquí irían los datos reales de la BD
        
        tablaProyectos = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tablaProyectos);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);
        
        // Estilo moderno
        aplicarEstilo();
        
        // Configurar eventos
        configurarEventos();
    }
    
    private void aplicarEstilo() {
        // Colores modernos
        Color colorFondo = new Color(240, 240, 240);
        Color colorBoton = new Color(70, 130, 180);
        Color colorTextoBoton = Color.WHITE;
        
        setBackground(colorFondo);
        
        btnAgregar.setBackground(colorBoton);
        btnAgregar.setForeground(colorTextoBoton);
        
        btnEditar.setBackground(colorBoton);
        btnEditar.setForeground(colorTextoBoton);
        
        btnEliminar.setBackground(new Color(220, 20, 60));
        btnEliminar.setForeground(colorTextoBoton);
    }
    
    private void configurarEventos() {
        btnAgregar.addActionListener(e -> {
            // Abrir diálogo para agregar proyecto
           JFrame parentFrame = (JFrame)SwingUtilities.getWindowAncestor(this);
          new ProyectoDialog(parentFrame, null).setVisible(true);
        });
        
        // Similar para editar y eliminar
    }
}
