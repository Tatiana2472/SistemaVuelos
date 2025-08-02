/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.view;

import gestorproyectos.dao.UserDB;
import gestorproyectosmodel.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author tatia
 */
public class UserPanel extends JPanel {
   private final UserDB userDB = new UserDB();
    private JTable tblUsers;
    private DefaultTableModel model;

    public UserPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(153, 102, 255));
        JLabel lbl = new JLabel("Gestión de Usuarios");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        header.add(lbl);
        add(header, BorderLayout.NORTH);

        // Tabla
        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Rol"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsers = new JTable(model);
        tblUsers.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblUsers.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tblUsers);
        add(scroll, BorderLayout.CENTER);

        // Botones
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnAdd = styledButton("➕ Agregar");
        JButton btnEdit = styledButton("✏️ Editar");
        JButton btnDelete = styledButton("🗑️ Eliminar");

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.SOUTH);

        // Eventos
        btnAdd.addActionListener(e -> addUser());
        btnEdit.addActionListener(e -> editUser());
        btnDelete.addActionListener(e -> deleteUser());

        loadUsers();
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(153, 102, 255));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadUsers() {
        model.setRowCount(0);
        List<User> users = userDB.getAll();
        for (User u : users) {
            model.addRow(new Object[]{
                    u.getId(),
                    u.getName(),
                    u.getEmail(),
                    u.getRole()
            });
        }
    }

    private void addUser() {
        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtRole = new JTextField("Colaborador");

        Object[] fields = {
                "Nombre:", txtName,
                "Email:", txtEmail,
                "Rol:", txtRole
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Agregar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            User u = new User(0,
                    txtName.getText().trim(),
                    txtEmail.getText().trim(),
                    txtRole.getText().trim());
            userDB.insert(u);
            loadUsers();
        }
    }

    private void editUser() {
        int row = tblUsers.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para editar.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        JTextField txtName = new JTextField((String) model.getValueAt(row, 1));
        JTextField txtEmail = new JTextField((String) model.getValueAt(row, 2));
        JTextField txtRole = new JTextField((String) model.getValueAt(row, 3));

        Object[] fields = {
                "Nombre:", txtName,
                "Email:", txtEmail,
                "Rol:", txtRole
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Editar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            User u = new User(id,
                    txtName.getText().trim(),
                    txtEmail.getText().trim(),
                    txtRole.getText().trim());
            userDB.update(u);
            loadUsers();
        }
    }

    private void deleteUser() {
        int row = tblUsers.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para eliminar.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userDB.delete(id);
            loadUsers();
        }
    }
}