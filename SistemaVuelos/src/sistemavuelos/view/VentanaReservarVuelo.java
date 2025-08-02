/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import sistemavuelos.dao.ReservaDAO;
import sistemavuelos.dao.VueloDAO;
import sistemavuelos.model.Vuelo;

/**
 *
 * @author tatia
 */
public class VentanaReservarVuelo extends JFrame {
    private int usuarioId; // El usuario logueado

    public VentanaReservarVuelo(int usuarioId) {
        this.usuarioId = usuarioId;
        setTitle("Reservar Vuelo ✈");
        setSize(700, 400);
        setLocationRelativeTo(null);

        String[] columnas = {"ID", "Aerolínea", "Origen", "Destino", "Fecha", "Hora", "Precio"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        JButton btnReservar = new JButton("Reservar");

        VueloDAO dao = new VueloDAO();
        List<Vuelo> vuelos = dao.listarVuelos();
        for (Vuelo v : vuelos) {
            modelo.addRow(new Object[]{v.getId(), v.getAerolinea(), v.getOrigen(), v.getDestino(), v.getFecha(), v.getHora(), v.getPrecio()});
        }

        btnReservar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int vueloId = (int) modelo.getValueAt(fila, 0);
                ReservaDAO rdao = new ReservaDAO();
                if (rdao.crearReserva(usuarioId, vueloId)) {
                    JOptionPane.showMessageDialog(this, "Reserva realizada con éxito.");
                } else {
                    JOptionPane.showMessageDialog(this, "Ya tienes una reserva para este vuelo.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un vuelo primero.");
            }
        });

        add(scroll, BorderLayout.CENTER);
        add(btnReservar, BorderLayout.SOUTH);
        setVisible(true);
    }
}