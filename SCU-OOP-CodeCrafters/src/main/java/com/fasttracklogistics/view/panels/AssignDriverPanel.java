package com.fasttracklogistics.view.panels;

import com.fasttracklogistics.models.Assignment;
import com.fasttracklogistics.service.AssignmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AssignDriverPanel extends JPanel {

    private final AssignmentService service;
    private final JTable table;
    private final DefaultTableModel model;
    private final JTextField shipmentField, driverField;

    public AssignDriverPanel() {
        this.service = new AssignmentService();
        setLayout(new BorderLayout(10, 10));

        // ========== Input Form ==========
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Assign Driver"));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 14);

        shipmentField = new JTextField();
        driverField = new JTextField();
        shipmentField.setFont(inputFont);
        driverField.setFont(inputFont);

        form.add(createLabel("Shipment ID:", labelFont));
        form.add(shipmentField);
        form.add(createLabel("Driver ID:", labelFont));
        form.add(driverField);

        JButton assignBtn = new JButton("Assign");
        JButton removeBtn = new JButton("Remove");
        assignBtn.setFont(labelFont);
        removeBtn.setFont(labelFont);

        assignBtn.addActionListener(e -> assignDriver());
        removeBtn.addActionListener(e -> removeAssignment());

        form.add(assignBtn);
        form.add(removeBtn);
        add(form, BorderLayout.NORTH);

        // ========== Table ==========
        model = new DefaultTableModel(new Object[]{"Assignment ID", "Shipment ID", "Driver ID"}, 0);
        table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Assignments"));
        add(scrollPane, BorderLayout.CENTER);

        loadAssignments();
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private void assignDriver() {
        String shipmentText = shipmentField.getText().trim();
        String driverText = driverField.getText().trim();

        if (shipmentText.isEmpty() || driverText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both fields are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int shipmentId = Integer.parseInt(shipmentText);
            int driverId = Integer.parseInt(driverText);

            Assignment assignment = new Assignment(shipmentId, driverId);
            service.assignDriver(assignment);

            JOptionPane.showMessageDialog(this, "Driver assigned successfully.");
            shipmentField.setText("");
            driverField.setText("");
            loadAssignments();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid numeric IDs.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeAssignment() {
        int selected = table.getSelectedRow();
        if (selected >= 0) {
            int assignmentId = (int) model.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Remove this assignment?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.removeAssignment(assignmentId);
                loadAssignments();
                JOptionPane.showMessageDialog(this, "Assignment removed.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    }

    private void loadAssignments() {
        model.setRowCount(0);
        List<Assignment> list = service.getAllAssignments();
        for (Assignment a : list) {
            model.addRow(new Object[]{a.getId(), a.getShipmentId(), a.getDriverId()});
        }
    }
}
