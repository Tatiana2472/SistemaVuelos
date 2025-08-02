/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.view;

import javax.swing.*;
import java.awt.*;
import sistemavuelos.dao.UsuarioDAO;
import sistemavuelos.model.Usuario;

/**
 *
 * @author tatia
 */
public class VentanaRegistro  extends JFrame {
    private JTextField txtNombre, txtCorreo;
    private JPasswordField txtPassword;
    private UsuarioDAO usuarioDAO;

    public VentanaRegistro() {
        usuarioDAO = new UsuarioDAO();
        setTitle("Registro de Usuario 📝");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panel.add(txtCorreo);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String password = new String(txtPassword.getPassword());

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            Usuario nuevo = new Usuario(0, nombre, correo, password);
            usuarioDAO.registrarUsuario(nuevo);
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }
}
