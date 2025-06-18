package com.fasttracklogistics.view.panels;

import com.fasttracklogistics.dao.DriverDao;
import com.fasttracklogistics.models.Driver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DriverManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private final DriverDao dao = new DriverDao();

    public DriverManagementPanel() {
        setLayout(new BorderLayout(10, 10));

        // Table setup
        model = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "License"}, 0);
        table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        loadDrivers();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Driver List"));
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JButton addBtn = new JButton("Add Driver");
        JButton delBtn = new JButton("Delete Driver");

        addBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        delBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        addBtn.addActionListener(e -> showAddDriverDialog());
        delBtn.addActionListener(e -> deleteSelectedDriver());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottom.add(addBtn);
        bottom.add(delBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadDrivers() {
        model.setRowCount(0);
        try {
            List<Driver> list = dao.getAllDrivers();
            for (Driver d : list) {
                model.addRow(new Object[]{
                        d.getDriverId(), d.getName(), d.getEmail(), d.getPhone(), d.getLicenseNo()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAddDriverDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField licenseField = new JTextField();

        Font inputFont = new Font("SansSerif", Font.PLAIN, 14);
        idField.setFont(inputFont);
        nameField.setFont(inputFont);
        emailField.setFont(inputFont);
        phoneField.setFont(inputFont);
        licenseField.setFont(inputFont);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Driver ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("License No:"));
        panel.add(licenseField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Driver", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Driver d = new Driver(
                        idField.getText().trim(),
                        nameField.getText().trim(),
                        emailField.getText().trim(),
                        phoneField.getText().trim(),
                        licenseField.getText().trim()
                );
                dao.addDriver(d);
                loadDrivers();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void deleteSelectedDriver() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String driverId = model.getValueAt(selectedRow, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Delete driver " + driverId + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    dao.deleteDriver(driverId);
                    loadDrivers();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting driver: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a driver to delete.");
        }
    }
}
