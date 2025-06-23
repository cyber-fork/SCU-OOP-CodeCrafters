package com.fasttracklogistics.view.panels;

import com.fasttracklogistics.config.DbConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SheduelingPanel extends JPanel {
    private final JTextField nameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JComboBox<String> timeSlotBox = new JComboBox<>(new String[]{
            "09:00-11:00", "11:00-13:00", "14:00-16:00", "16:00-18:00"
    });
    private final JButton bookButton = new JButton("Book Delivery");

    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final JTable table = new JTable(tableModel);

    public SheduelingPanel() {
        setLayout(new BorderLayout(15, 15));

        // Header
        JPanel headerPanel = new JPanel();
        JLabel headerLabel = new JLabel("Schedule Your Delivery");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Time Slot:"));
        formPanel.add(timeSlotBox);
        formPanel.add(new JLabel(""));
        formPanel.add(bookButton);

        Font inputFont = new Font("SansSerif", Font.PLAIN, 18);
        for (JComponent comp : new JComponent[]{nameField, emailField, phoneField, timeSlotBox, bookButton}) {
            comp.setFont(inputFont);
        }

        add(formPanel, BorderLayout.CENTER);

        // Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel.setColumnIdentifiers(new Object[]{"Shipment ID", "Customer", "Phone", "Status", "Scheduled At"});
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        // Action
        bookButton.addActionListener(e -> bookDelivery());

        // Load table initially
        loadScheduledDeliveries();
    }

    private void bookDelivery() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String timeSlot = (String) timeSlotBox.getSelectedItem();
        Date today = new Date(System.currentTimeMillis());

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try (Connection conn = DbConnection.getConnection()) {

            // Insert customer
            String insertCustomer = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement ps1 = conn.prepareStatement(insertCustomer, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, name);
            ps1.setString(2, email);
            ps1.setString(3, phone);

            int rowsAffected = ps1.executeUpdate();
            if (rowsAffected == 0) {
                JOptionPane.showMessageDialog(this, "Failed to create customer.");
                return;
            }

            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                int customerId = rs.getInt(1);

                // Insert shipment
                String insertShipment = "INSERT INTO shipments (customer_id, sender, receiver, contents, status, delivered_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps2 = conn.prepareStatement(insertShipment);
                ps2.setInt(1, customerId);
                ps2.setString(2, "Company Dispatch");
                ps2.setString(3, name);
                ps2.setString(4, "Auto-Scheduled Delivery");
                ps2.setString(5, "Scheduled");
                ps2.setDate(6, today);

                if (ps2.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Delivery scheduled successfully!");
                    nameField.setText("");
                    emailField.setText("");
                    phoneField.setText("");
                    timeSlotBox.setSelectedIndex(0);
                    loadScheduledDeliveries(); // reload the table
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to schedule delivery.");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void loadScheduledDeliveries() {
        tableModel.setRowCount(0);

        String sql = """
            SELECT s.shipment_id, c.name AS customer_name, c.phone, s.status, s.created_at
            FROM shipments s
            JOIN customers c ON s.customer_id = c.customer_id
            ORDER BY s.shipment_id DESC
        """;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("shipment_id"),
                        rs.getString("customer_name"),
                        rs.getString("phone"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
