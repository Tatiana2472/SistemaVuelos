/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.PrintWriter;
import java.util.List;
import sistemavuelos.dao.ReservaDAO;
import sistemavuelos.model.Reserva;
/**
 *
 * @author tatia
 */
public class VentanaItinerario extends JFrame {
    private int usuarioId;
    private List<Reserva> reservas;
    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaItinerario(int usuarioId) {
        this.usuarioId = usuarioId;
        setTitle("Mi Itinerario 🧳");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Tabla
        String[] columnas = {"ID Reserva", "Vuelo ID", "Fecha Reserva"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        // Botones
        JButton btnCancelar = new JButton("Cancelar Reserva");
        JButton btnExportarTXT = new JButton("Exportar a TXT");
        JButton btnExportarCSV = new JButton("Exportar a CSV");

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnCancelar);
        panelBotones.add(btnExportarTXT);
        panelBotones.add(btnExportarCSV);

        // Llenar tabla
        ReservaDAO dao = new ReservaDAO();
        reservas = dao.listarPorUsuario(usuarioId);
        actualizarTabla();

        // Cancelar reserva
        btnCancelar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int reservaId = (int) modelo.getValueAt(fila, 0);
                dao.cancelarReserva(reservaId);
                reservas.remove(fila);
                modelo.removeRow(fila);
                JOptionPane.showMessageDialog(this, "Reserva cancelada con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una reserva para cancelar.");
            }
        });

        // Exportar TXT
        btnExportarTXT.addActionListener(e -> exportarItinerarioTXT());

        // Exportar CSV
        btnExportarCSV.addActionListener(e -> exportarItinerarioCSV());

        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (Reserva r : reservas) {
            modelo.addRow(new Object[]{r.getId(), r.getVueloId(), r.getFechaReserva()});
        }
    }

    private void exportarItinerarioTXT() {
        try (PrintWriter writer = new PrintWriter("Itinerario.txt")) {
            writer.println("Itinerario de Reservas:");
            for (Reserva r : reservas) {
                writer.println("Reserva " + r.getId() + " | Vuelo: " + r.getVueloId() + " | Fecha: " + r.getFechaReserva());
            }
            JOptionPane.showMessageDialog(this, "Itinerario exportado como TXT.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al exportar TXT.");
        }
    }

    private void exportarItinerarioCSV() {
        try (PrintWriter writer = new PrintWriter("Itinerario.csv")) {
            writer.println("ID Reserva,Vuelo ID,Fecha Reserva");
            for (Reserva r : reservas) {
                writer.println(r.getId() + "," + r.getVueloId() + "," + r.getFechaReserva());
            }
            JOptionPane.showMessageDialog(this, "Itinerario exportado como CSV.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al exportar CSV.");
        }
    }
}
