/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.view;

import gestorproyectos.dao.TaskDB;
import gestorproyectosmodel.Task;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 *
 * @author tatia
 */
public class TaskPanel extends JPanel {
    private final TaskDB taskDB = new TaskDB();
    private JTable tblTasks;
    private DefaultTableModel model;

    public TaskPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel header = new JPanel();
        header.setBackground(new Color(76, 175, 80));
        JLabel lbl = new JLabel("Gestión de Tareas");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        header.add(lbl);
        add(header, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Título", "Estado", "Prioridad", "Progreso"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblTasks = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int col) {
                if (col == 4) {
                    int progress = (int) getValueAt(row, col);
                    JProgressBar bar = new JProgressBar(0, 100);
                    bar.setValue(progress);
                    bar.setString(progress + "%");
                    bar.setStringPainted(true);
                    bar.setForeground(new Color(76, 175, 80));
                    return bar;
                }
                return super.prepareRenderer(renderer, row, col);
            }
        };
        tblTasks.setRowHeight(30);
        tblTasks.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(tblTasks);
        add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnAdd = styledButton("➕ Agregar");
        JButton btnEdit = styledButton("✏️ Editar");
        JButton btnDelete = styledButton("🗑️ Eliminar");

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addTask());
        btnEdit.addActionListener(e -> editTask());
        btnDelete.addActionListener(e -> deleteTask());

        loadTasks();
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(76, 175, 80));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadTasks() {
        model.setRowCount(0);
        int projectId = 1; // Cambia esto según contexto real
        List<Task> tasks = taskDB.getByProject(projectId);
        for (Task t : tasks) {
            model.addRow(new Object[]{
                    t.getId(),
                    t.getTitle(),
                    t.getStatus(),
                    t.getPriority(),
                    t.getProgress()
            });
        }
    }

    private void addTask() {
        JTextField txtTitle = new JTextField();
        JTextArea txtDesc = new JTextArea(4, 20);
        JTextField txtStatus = new JTextField("Pendiente");
        JTextField txtPriority = new JTextField("Alta");
        JSpinner spnProgress = new JSpinner(new SpinnerNumberModel(0, 0, 100, 5));

        JScrollPane scrollDesc = new JScrollPane(txtDesc);

        Object[] fields = {
                "Título:", txtTitle,
                "Descripción:", scrollDesc,
                "Estado:", txtStatus,
                "Prioridad:", txtPriority,
                "Progreso:", spnProgress
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Agregar Tarea", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Task t = new Task(0,
                    1, // projectId
                    1, // assignedTo
                    txtTitle.getText().trim(),
                    txtDesc.getText().trim(),
                    txtStatus.getText().trim(),
                    txtPriority.getText().trim(),
                    (int) spnProgress.getValue());
            taskDB.insert(t);
            loadTasks();
        }
    }

    private void editTask() {
        int row = tblTasks.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una tarea para editar.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        JTextField txtTitle = new JTextField((String) model.getValueAt(row, 1));
        JTextArea txtDesc = new JTextArea(4, 20);
        JTextField txtStatus = new JTextField((String) model.getValueAt(row, 2));
        JTextField txtPriority = new JTextField((String) model.getValueAt(row, 3));
        JSpinner spnProgress = new JSpinner(new SpinnerNumberModel((int) model.getValueAt(row, 4), 0, 100, 5));

        JScrollPane scrollDesc = new JScrollPane(txtDesc);

        Object[] fields = {
                "Título:", txtTitle,
                "Descripción:", scrollDesc,
                "Estado:", txtStatus,
                "Prioridad:", txtPriority,
                "Progreso:", spnProgress
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Editar Tarea", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Task t = new Task(id,
                    1, // projectId
                    1, // assignedTo
                    txtTitle.getText().trim(),
                    txtDesc.getText().trim(),
                    txtStatus.getText().trim(),
                    txtPriority.getText().trim(),
                    (int) spnProgress.getValue());
            taskDB.update(t);
            loadTasks();
        }
    }

    private void deleteTask() {
        int row = tblTasks.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una tarea para eliminar.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar tarea?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            taskDB.delete(id);
            loadTasks();
        }
    }
}
