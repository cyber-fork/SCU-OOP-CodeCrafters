package com.fasttracklogistics.view.panels;

import com.fasttracklogistics.models.DriverNotification;
import com.fasttracklogistics.service.DriverNotificationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class DriverNotificationPanel extends JPanel {
    private DriverNotificationService service;
    private JTable table;
    private DefaultTableModel model;
    private JTextField driverIdField, driverNameField, emailField, messageField;

    public DriverNotificationPanel() {
        this.service = new DriverNotificationService();
        setLayout(new BorderLayout(10, 10));

        // Top input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Send Notification"));

        Font fieldFont = new Font("SansSerif", Font.PLAIN, 16);
        Font labelFont = new Font("SansSerif", Font.BOLD, 16);

        driverIdField = new JTextField();
        driverIdField.setFont(fieldFont);
        driverNameField = new JTextField();
        driverNameField.setFont(fieldFont);
        emailField = new JTextField();
        emailField.setFont(fieldFont);
        messageField = new JTextField();
        messageField.setFont(fieldFont);

        inputPanel.add(createLabel("Driver ID:", labelFont));
        inputPanel.add(driverIdField);
        inputPanel.add(createLabel("Driver Name:", labelFont));
        inputPanel.add(driverNameField);
        inputPanel.add(createLabel("Email:", labelFont));
        inputPanel.add(emailField);
        inputPanel.add(createLabel("Message:", labelFont));
        inputPanel.add(messageField);

        JButton sendButton = new JButton("Send Notification");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        sendButton.addActionListener(this::handleSend);
        inputPanel.add(sendButton);

        JButton deleteButton = new JButton("Delete Notification");
        deleteButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        deleteButton.addActionListener(this::handleDelete);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table panel
        model = new DefaultTableModel(new Object[]{"Driver ID", "Driver Name", "Email", "Message"}, 0);
        table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Notification History"));
        add(scrollPane, BorderLayout.CENTER);

        loadNotifications();
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private void loadNotifications() {
        model.setRowCount(0);
        List<DriverNotification> notifications = service.getAllNotifications();
        for (DriverNotification dn : notifications) {
            model.addRow(new Object[]{dn.getDriverId(), dn.getDriverName(), dn.getEmail(), dn.getMessage()});
        }
    }

    private void handleSend(ActionEvent e) {
        String driverId = driverIdField.getText().trim();
        String driverName = driverNameField.getText().trim();
        String email = emailField.getText().trim();
        String message = messageField.getText().trim();

        if (driverId.isEmpty() || driverName.isEmpty() || email.isEmpty() || message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DriverNotification dn = new DriverNotification(driverId, driverName, email, message);
        service.sendNotification(dn);
        JOptionPane.showMessageDialog(this, "Message sent to driver.", "Success", JOptionPane.INFORMATION_MESSAGE);
        loadNotifications();
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String driverId = (String) model.getValueAt(selectedRow, 0);
            String email = (String) model.getValueAt(selectedRow, 2);
            service.deleteNotification(driverId, email);
            JOptionPane.showMessageDialog(this, "Notification deleted.", "Info", JOptionPane.INFORMATION_MESSAGE);
            loadNotifications();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
