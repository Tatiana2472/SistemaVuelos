/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.ui;

import gestorproyectos.model.Proyecto;
import gestorproyectos.service.ProyectoService;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author tatia
 */
public class ProyectoDialog extends JDialog {
    private final JTextField txtNombre;
    private final JTextArea txtDescripcion;
    private final JTextField txtFechaInicio;
    private final JTextField txtFechaFin;
    private final JComboBox<String> cmbEstado;
    private final ProyectoService proyectoService;
    private Proyecto proyectoEdicion;
    
    public ProyectoDialog(JFrame parent, Proyecto proyecto) {
        super(parent, proyecto == null ? "Nuevo Proyecto" : "Editar Proyecto", true);
        this.proyectoEdicion = proyecto;
        this.proyectoService = new ProyectoService();
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        
        // Componentes
        txtNombre = new JTextField(20);
        txtDescripcion = new JTextArea(5, 20);
        txtFechaInicio = new JTextField(10);
        txtFechaFin = new JTextField(10);
        cmbEstado = new JComboBox<>(new String[]{"Pendiente", "En progreso", "Completado", "Cancelado"});
        
        initUI();
        cargarDatosProyecto();
    }
    
    private void initUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(txtNombre, gbc);
        
        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(new JLabel("Descripción:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panelFormulario.add(new JScrollPane(txtDescripcion), gbc);
        
        // Fechas
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0.0;
        panelFormulario.add(new JLabel("Fecha Inicio (dd/mm/aaaa):"), gbc);
        
        gbc.gridx = 1;
        panelFormulario.add(txtFechaInicio, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Fecha Fin (dd/mm/aaaa):"), gbc);
        
        gbc.gridx = 1;
        panelFormulario.add(txtFechaFin, gbc);
        
        // Estado
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Estado:"), gbc);
        
        gbc.gridx = 1;
        panelFormulario.add(cmbEstado, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        // Estilo
        aplicarEstilo(btnGuardar, btnCancelar);
        
        // Eventos
        btnGuardar.addActionListener(e -> guardarProyecto());
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        // Ensamblar
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }
    
    private void cargarDatosProyecto() {
        if (proyectoEdicion != null) {
            txtNombre.setText(proyectoEdicion.getNombre());
            txtDescripcion.setText(proyectoEdicion.getDescripcion());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txtFechaInicio.setText(sdf.format(proyectoEdicion.getFechaInicio()));
            txtFechaFin.setText(sdf.format(proyectoEdicion.getFechaFin()));
            
            cmbEstado.setSelectedItem(proyectoEdicion.getEstado());
        }
    }
    
    private void guardarProyecto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // Validación estricta de fechas
            
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            Date fechaInicio = sdf.parse(txtFechaInicio.getText().trim());
            Date fechaFin = sdf.parse(txtFechaFin.getText().trim());
            String estado = (String) cmbEstado.getSelectedItem();
            
            Proyecto proyecto = proyectoEdicion != null ? proyectoEdicion : new Proyecto();
            proyecto.setNombre(nombre);
            proyecto.setDescripcion(descripcion);
            proyecto.setFechaInicio(fechaInicio);
            proyecto.setFechaFin(fechaFin);
            proyecto.setEstado(estado);
            
            boolean exito;
            if (proyectoEdicion == null) {
                exito = proyectoService.crearProyecto(proyecto);
            } else {
                exito = proyectoService.actualizarProyecto(proyecto);
            }
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Proyecto guardado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo guardar el proyecto", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Formato de fecha inválido. Use dd/mm/aaaa", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void aplicarEstilo(JButton btnGuardar, JButton btnCancelar) {
        // Estilos consistentes
        Color colorFondo = new Color(245, 245, 245);
        Color colorBotonGuardar = new Color(76, 175, 80);
        Color colorBotonCancelar = new Color(244, 67, 54);
        Color colorTextoBoton = Color.WHITE;
        
        getContentPane().setBackground(colorFondo);
        
        btnGuardar.setBackground(colorBotonGuardar);
        btnGuardar.setForeground(colorTextoBoton);
        
        btnCancelar.setBackground(colorBotonCancelar);
        btnCancelar.setForeground(colorTextoBoton);
        
        // Fuentes
        Font fuente = new Font("Segoe UI", Font.PLAIN, 12);
        btnGuardar.setFont(fuente);
        btnCancelar.setFont(fuente);
    }
}
