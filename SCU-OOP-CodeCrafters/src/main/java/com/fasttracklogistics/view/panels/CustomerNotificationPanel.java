package com.fasttracklogistics.view.panels;

import com.fasttracklogistics.models.CustomerNotification;
import com.fasttracklogistics.service.CustomerNotificationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerNotificationPanel extends JPanel {

    private CustomerNotificationService service;
    private JTable table;
    private DefaultTableModel model;
    private JTextField customerIdField, customerNameField, emailField, messageField;

    public CustomerNotificationPanel() {
        this.service = new CustomerNotificationService();
        setLayout(new BorderLayout(10, 10));

        // Top input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Send Customer Notification"));

        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);
        Font labelFont = new Font("SansSerif", Font.BOLD, 14);

        customerIdField = new JTextField();
        customerNameField = new JTextField();
        emailField = new JTextField();
        messageField = new JTextField();

        customerIdField.setFont(fieldFont);
        customerNameField.setFont(fieldFont);
        emailField.setFont(fieldFont);
        messageField.setFont(fieldFont);

        inputPanel.add(createLabel("Customer ID:", labelFont));
        inputPanel.add(customerIdField);
        inputPanel.add(createLabel("Customer Name:", labelFont));
        inputPanel.add(customerNameField);
        inputPanel.add(createLabel("Email:", labelFont));
        inputPanel.add(emailField);
        inputPanel.add(createLabel("Message:", labelFont));
        inputPanel.add(messageField);

        JButton sendBtn = new JButton("Send Notification");
        JButton deleteBtn = new JButton("Delete Notification");

        sendBtn.setFont(labelFont);
        deleteBtn.setFont(labelFont);

        sendBtn.addActionListener(e -> sendNotification());
        deleteBtn.addActionListener(e -> deleteNotification());

        inputPanel.add(sendBtn);
        inputPanel.add(deleteBtn);

        add(inputPanel, BorderLayout.NORTH);

        // Table section
        model = new DefaultTableModel(new Object[]{"Customer ID", "Customer Name", "Email", "Message"}, 0);
        table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

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

    private void sendNotification() {
        String id = customerIdField.getText().trim();
        String name = customerNameField.getText().trim();
        String email = emailField.getText().trim();
        String message = messageField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        CustomerNotification cn = new CustomerNotification(id, name, email, message);
        service.sendNotification(cn);
        JOptionPane.showMessageDialog(this, "Message sent.");
        loadNotifications();
    }

    private void deleteNotification() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String id = (String) model.getValueAt(row, 0);
            String email = (String) model.getValueAt(row, 2);
            service.deleteNotification(id, email);
            JOptionPane.showMessageDialog(this, "Notification deleted.");
            loadNotifications();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
        }
    }

    private void loadNotifications() {
        model.setRowCount(0);
        List<CustomerNotification> list = service.getAllNotifications();
        for (CustomerNotification cn : list) {
            model.addRow(new Object[]{cn.getCustomerId(), cn.getCustomerName(), cn.getEmail(), cn.getMessage()});
        }
    }
}
