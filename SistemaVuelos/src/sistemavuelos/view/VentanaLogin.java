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
public class VentanaLogin extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private UsuarioDAO usuarioDAO;

    public VentanaLogin() {
        usuarioDAO = new UsuarioDAO();
        setTitle("Login - Sistema de Reservas ✈");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos
        panel.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panel.add(txtCorreo);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistrar = new JButton("Registrarse");

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistrar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Acción Login
        btnLogin.addActionListener(e -> {
            String correo = txtCorreo.getText();
            String password = new String(txtPassword.getPassword());

            if (correo.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar todos los campos.");
                return;
            }

            Usuario u = usuarioDAO.login(correo, password);
            if (u != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido, " + u.getNombre());
                dispose();
                new VentanaPrincipal(u); // Pasamos el usuario logueado
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            }
        });

        // Acción Registro
        btnRegistrar.addActionListener(e -> new VentanaRegistro());

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaLogin::new);
    }
}
