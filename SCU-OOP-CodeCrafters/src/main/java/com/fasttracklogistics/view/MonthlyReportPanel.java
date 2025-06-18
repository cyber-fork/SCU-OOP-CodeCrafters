package com.fasttracklogistics.view.panels;

import com.fasttracklogistics.service.MonthlyReportService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class MonthlyReportPanel extends JPanel {

    private final JComboBox<Integer> yearCombo;
    private final JComboBox<String> monthCombo;
    private final JCheckBox shipmentTableCheck;
    private final JCheckBox driverTableCheck;
    private final JCheckBox customerTableCheck;
    private final JCheckBox assignmentTableCheck;
    private final JButton generateBtn;
    private final MonthlyReportService reportService;
    private final JPanel reportContainer;

    public MonthlyReportPanel() {
        setLayout(new BorderLayout(10, 10));
        reportService = new MonthlyReportService();

        JPanel filterPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Select Tables & Month"));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 14);

        yearCombo = new JComboBox<>();
        for (int y = 2020; y <= YearMonth.now().getYear(); y++) yearCombo.addItem(y);
        yearCombo.setFont(inputFont);

        monthCombo = new JComboBox<>();
        for (int m = 1; m <= 12; m++) monthCombo.addItem(String.format("%02d", m));
        monthCombo.setFont(inputFont);

        shipmentTableCheck = new JCheckBox("shipments", true);
        driverTableCheck   = new JCheckBox("drivers");
        customerTableCheck = new JCheckBox("customers");
        assignmentTableCheck = new JCheckBox("assignments");

        for (JCheckBox cb : new JCheckBox[]{shipmentTableCheck, driverTableCheck, customerTableCheck, assignmentTableCheck})
            cb.setFont(inputFont);

        filterPanel.add(new JLabel("Year:") {{ setFont(labelFont); }});
        filterPanel.add(yearCombo);
        filterPanel.add(new JLabel("Month:") {{ setFont(labelFont); }});
        filterPanel.add(monthCombo);
        filterPanel.add(new JLabel("Tables:") {{ setFont(labelFont); }});

        JPanel cbPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbPanel.add(shipmentTableCheck);
        cbPanel.add(driverTableCheck);
        cbPanel.add(customerTableCheck);
        cbPanel.add(assignmentTableCheck);
        filterPanel.add(cbPanel);

        add(filterPanel, BorderLayout.NORTH);

        reportContainer = new JPanel();
        reportContainer.setLayout(new BoxLayout(reportContainer, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(reportContainer);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Generated Report"));
        add(scrollPane, BorderLayout.CENTER);

        generateBtn = new JButton("Generate Report");
        generateBtn.setFont(labelFont);
        generateBtn.addActionListener(e -> generateReport());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(generateBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void generateReport() {
        int year = (int) yearCombo.getSelectedItem();
        int month = Integer.parseInt((String) monthCombo.getSelectedItem());
        YearMonth ym = YearMonth.of(year, month);

        List<String> selectedTables = new java.util.ArrayList<>();
        if (shipmentTableCheck.isSelected()) selectedTables.add("shipments");
        if (driverTableCheck.isSelected()) selectedTables.add("drivers");
        if (customerTableCheck.isSelected()) selectedTables.add("customers");
        if (assignmentTableCheck.isSelected()) selectedTables.add("assignments");

        if (selectedTables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one table.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        reportContainer.removeAll();

        Map<String, List<String>> columnsMap = reportService.getColumnsForTables(selectedTables);
        Map<String, List<List<Object>>> groupedData = reportService.getGroupedReportData(ym, selectedTables);

        for (String table : selectedTables) {
            JLabel sectionTitle = new JLabel("📊 " + table.toUpperCase() + " REPORT");
            sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
            reportContainer.add(sectionTitle);

            List<String> cols = columnsMap.getOrDefault(table, List.of());
            List<List<Object>> rows = groupedData.getOrDefault(table, List.of());

            DefaultTableModel model = new DefaultTableModel(cols.toArray(), 0);
            for (List<Object> row : rows) {
                model.addRow(row.toArray());
            }

            JTable tableComp = new JTable(model);
            tableComp.setFont(new Font("SansSerif", Font.PLAIN, 13));
            tableComp.setRowHeight(22);
            tableComp.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
            JScrollPane tableScroll = new JScrollPane(tableComp);
            tableScroll.setPreferredSize(new Dimension(1000, 150));

            reportContainer.add(Box.createVerticalStrut(10));
            reportContainer.add(tableScroll);
        }

        reportContainer.revalidate();
        reportContainer.repaint();
    }
}
