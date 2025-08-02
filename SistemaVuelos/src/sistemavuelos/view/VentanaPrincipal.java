/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import sistemavuelos.model.Usuario;

/**
 *
 * @author tatia
 */
public class VentanaPrincipal extends JFrame {
    private Usuario usuario;

    public VentanaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Bienvenido " + usuario.getNombre() + " - Sistema de Reservas ✈");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnBuscar = new JButton("Buscar Vuelos");
        JButton btnReservar = new JButton("Reservar Vuelo");
        JButton btnItinerario = new JButton("Mi Itinerario");

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(btnBuscar);
        panel.add(btnReservar);
        panel.add(btnItinerario);

        add(panel);
        setVisible(true);

        btnBuscar.addActionListener(e -> new VentanaBuscarVuelos());
        btnReservar.addActionListener(e -> new VentanaReservarVuelo(usuario.getId()));
        btnItinerario.addActionListener(e -> new VentanaItinerario(usuario.getId()));
    }
}
