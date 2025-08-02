/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.view;

import gestorproyectos.dao.ProjectDB;
import gestorproyectosmodel.Project;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author tatia
 */
public class ProjectPanel extends JPanel {
    private final ProjectDB projectDB = new ProjectDB();
    private JTable tblProjects;
    private DefaultTableModel model;

    public ProjectPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel header = new JPanel();
        header.setBackground(new Color(0, 102, 204));
        JLabel lbl = new JLabel("Gestión de Proyectos");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        header.add(lbl);
        add(header, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripción", "Fecha Inicio", "Fecha Fin"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblProjects = new JTable(model);
        tblProjects.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblProjects.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tblProjects);
        add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnAdd = styledButton("➕ Agregar");
        JButton btnEdit = styledButton("✏️ Editar");
        JButton btnDelete = styledButton("🗑️ Eliminar");

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addProject());
        btnEdit.addActionListener(e -> editProject());
        btnDelete.addActionListener(e -> deleteProject());

        loadProjects();
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(0, 102, 204));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadProjects() {
        model.setRowCount(0);
        List<Project> projects = projectDB.getAll();
        for (Project p : projects) {
            model.addRow(new Object[]{
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getStartDate(),
                p.getEndDate()
            });
        }
    }

    private void addProject() {
        JTextField txtName = new JTextField();
        JTextArea txtDesc = new JTextArea(4, 20);
        JTextField txtStart = new JTextField("2025-08-01");
        JTextField txtEnd = new JTextField("2025-12-31");
        JScrollPane scrollDesc = new JScrollPane(txtDesc);

        Object[] fields = {
            "Nombre:", txtName,
            "Descripción:", scrollDesc,
            "Fecha Inicio (YYYY-MM-DD):", txtStart,
            "Fecha Fin (YYYY-MM-DD):", txtEnd
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Agregar Proyecto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Project p = new Project(0,
                    txtName.getText().trim(),
                    txtDesc.getText().trim(),
                    txtStart.getText().trim(),
                    txtEnd.getText().trim());
            projectDB.insert(p);
            loadProjects();
        }
    }

    private void editProject() {
        int row = tblProjects.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proyecto para editar.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);

        JTextField txtName = new JTextField((String) model.getValueAt(row, 1));
        JTextArea txtDesc = new JTextArea((String) model.getValueAt(row, 2), 4, 20);
        JTextField txtStart = new JTextField((String) model.getValueAt(row, 3));
        JTextField txtEnd = new JTextField((String) model.getValueAt(row, 4));
        JScrollPane scrollDesc = new JScrollPane(txtDesc);

        Object[] fields = {
            "Nombre:", txtName,
            "Descripción:", scrollDesc,
            "Fecha Inicio (YYYY-MM-DD):", txtStart,
            "Fecha Fin (YYYY-MM-DD):", txtEnd
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Editar Proyecto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Project p = new Project(id,
                    txtName.getText().trim(),
                    txtDesc.getText().trim(),
                    txtStart.getText().trim(),
                    txtEnd.getText().trim());
            projectDB.update(p);
            loadProjects();
        }
    }

    private void deleteProject() {
        int row = tblProjects.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proyecto para eliminar.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar proyecto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            projectDB.delete(id);
            loadProjects();
        }
    }
}
