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
    private final ProyectoService proyectoService;
    private Proyecto proyecto;
    private boolean guardadoExitoso;
    
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JFormattedTextField txtFechaInicio;
    private JFormattedTextField txtFechaFin;
    private JComboBox<String> cmbEstado;
    
    public ProyectoDialog(Frame owner, Proyecto proyecto) {
        super(owner, "Editar Proyecto", true);
        this.proyectoService = new ProyectoService();
        this.proyecto = proyecto;
        this.guardadoExitoso = false;
        
        initComponents();
        cargarDatosProyecto();
        pack();
        setLocationRelativeTo(owner);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        
        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);
        
        panelForm.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextArea(3, 20);
        panelForm.add(new JScrollPane(txtDescripcion));
        
        panelForm.add(new JLabel("Fecha Inicio:"));
        txtFechaInicio = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        txtFechaInicio.setValue(new Date());
        panelForm.add(txtFechaInicio);
        
        panelForm.add(new JLabel("Fecha Fin:"));
        txtFechaFin = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        panelForm.add(txtFechaFin);
        
        panelForm.add(new JLabel("Estado:"));
        cmbEstado = new JComboBox<>(new String[]{"Pendiente", "En Progreso", "Completado", "Cancelado"});
        panelForm.add(cmbEstado);
        
        add(panelForm, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> guardarProyecto());
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void cargarDatosProyecto() {
        if (proyecto != null) {
            txtNombre.setText(proyecto.getNombre());
            txtDescripcion.setText(proyecto.getDescripcion());
            txtFechaInicio.setValue(proyecto.getFechaInicio());
            txtFechaFin.setValue(proyecto.getFechaFin());
            cmbEstado.setSelectedItem(proyecto.getEstado());
        }
    }
    
    private void guardarProyecto() {
        try {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            Date fechaInicio = (Date) txtFechaInicio.getValue();
            Date fechaFin = txtFechaFin.getValue() != null ? (Date) txtFechaFin.getValue() : null;
            String estado = (String) cmbEstado.getSelectedItem();
            
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del proyecto es requerido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (proyecto == null) {
                proyecto = new Proyecto(nombre, descripcion, fechaInicio);
            } else {
                proyecto.setNombre(nombre);
                proyecto.setDescripcion(descripcion);
                proyecto.setFechaInicio(fechaInicio);
            }
            
            proyecto.setFechaFin(fechaFin);
            proyecto.setEstado(estado);
            
            boolean resultado;
            if (proyecto.getId() == 0) {
                resultado = proyectoService.crearProyecto(proyecto);
            } else {
                resultado = proyectoService.actualizarProyecto(proyecto);
            }
            
            if (resultado) {
                guardadoExitoso = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el proyecto", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }
}
