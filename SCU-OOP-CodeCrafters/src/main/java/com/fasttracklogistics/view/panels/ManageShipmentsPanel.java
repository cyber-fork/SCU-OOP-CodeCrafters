package com.fasttracklogistics.view.panels;

import com.fasttracklogistics.models.Shipment;
import com.fasttracklogistics.service.ShipmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.UUID;

public class ManageShipmentsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private ShipmentService service;

    public ManageShipmentsPanel() {
        service = new ShipmentService();
        setLayout(new BorderLayout(10, 10));

        model = new DefaultTableModel(new Object[]{"ID", "Sender", "Receiver", "Contents", "Status"}, 0);
        table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Shipment List"));
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton removeBtn = new JButton("Remove");

        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        addBtn.setFont(buttonFont);
        editBtn.setFont(buttonFont);
        removeBtn.setFont(buttonFont);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(removeBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addBtn.addActionListener(e -> openForm(null));
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                Shipment s = getShipmentFromRow(row);
                openForm(s);
            } else {
                JOptionPane.showMessageDialog(this, "Select a shipment to edit.");
            }
        });
        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String id = (String) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Delete shipment " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    service.delete(id);
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a shipment to delete.");
            }
        });
    }

    private Shipment getShipmentFromRow(int row) {
        return new Shipment(
                (String) model.getValueAt(row, 0),
                (String) model.getValueAt(row, 1),
                (String) model.getValueAt(row, 2),
                (String) model.getValueAt(row, 3),
                (String) model.getValueAt(row, 4)
        );
    }

    private void openForm(Shipment s) {
        JTextField senderField = new JTextField(s != null ? s.getSender() : "");
        JTextField receiverField = new JTextField(s != null ? s.getReceiver() : "");
        JTextField contentField = new JTextField(s != null ? s.getContents() : "");
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Pending", "In Transit", "Delivered"});

        Font inputFont = new Font("SansSerif", Font.PLAIN, 14);
        senderField.setFont(inputFont);
        receiverField.setFont(inputFont);
        contentField.setFont(inputFont);
        statusBox.setFont(inputFont);

        if (s != null) statusBox.setSelectedItem(s.getStatus());

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Sender:"));
        panel.add(senderField);
        panel.add(new JLabel("Receiver:"));
        panel.add(receiverField);
        panel.add(new JLabel("Contents:"));
        panel.add(contentField);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);

        int result = JOptionPane.showConfirmDialog(this, panel,
                s == null ? "Add Shipment" : "Edit Shipment",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Shipment newShipment = new Shipment(
                    s == null ? UUID.randomUUID().toString() : s.getId(),
                    senderField.getText().trim(),
                    receiverField.getText().trim(),
                    contentField.getText().trim(),
                    (String) statusBox.getSelectedItem()
            );
            if (s == null) service.add(newShipment);
            else service.update(newShipment);
            refreshTable();
        }
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Shipment> list = service.getAll();
        for (Shipment s : list) {
            model.addRow(new Object[]{s.getId(), s.getSender(), s.getReceiver(), s.getContents(), s.getStatus()});
        }
    }
}
