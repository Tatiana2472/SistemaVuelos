/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import sistemavuelos.dao.VueloDAO;
import sistemavuelos.model.Vuelo;

/**
 *
 * @author tatia
 */
public class VentanaBuscarVuelos extends JFrame {
    public VentanaBuscarVuelos() {
        setTitle("Buscar Vuelos Disponibles");
        setSize(700, 400);
        setLocationRelativeTo(null);

        String[] columnas = {"ID", "Aerolínea", "Origen", "Destino", "Fecha", "Hora", "Precio", "Escalas", "Capacidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        VueloDAO dao = new VueloDAO();
        List<Vuelo> vuelos = dao.listarVuelos();
        for (Vuelo v : vuelos) {
            modelo.addRow(new Object[]{v.getId(), v.getAerolinea(), v.getOrigen(), v.getDestino(), v.getFecha(), v.getHora(), v.getPrecio(), v.getEscalas(), v.getCapacidad()});
        }

        add(scroll, BorderLayout.CENTER);
        setVisible(true);
    }
}