package com.fasttracklogistics.service;

import com.fasttracklogistics.dao.MonthlyReportDao;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileWriter;
import java.time.YearMonth;
import java.util.*;

public class MonthlyReportService {

    private final MonthlyReportDao dao = new MonthlyReportDao();

    public Map<String, List<String>> getColumnsForTables(List<String> tables) {
        Map<String, List<String>> cols = new LinkedHashMap<>();
        if (tables.contains("shipments")) {
            cols.put("shipments", Arrays.asList(
                    "shipment_id", "sender", "receiver", "status", "contents"
            ));
        }
        if (tables.contains("drivers")) {
            cols.put("drivers", Arrays.asList(
                    "driver_id", "name", "email"
            ));
        }
        if (tables.contains("customers")) {
            cols.put("customers", Arrays.asList(
                    "customer_id", "name", "email"
            ));
        }
        if (tables.contains("assignments")) {
            cols.put("assignments", Arrays.asList(
                    "assignment_id", "shipment_id", "driver_id"
            ));
        }
        return cols;
    }

    public Map<String, List<List<Object>>> getGroupedReportData(YearMonth ym, List<String> selectedTables) {
        Map<String, List<String>> tableCols = getColumnsForTables(selectedTables);
        return dao.fetchGroupedReportData(ym, selectedTables, tableCols);
    }

    public void exportToCSV(JTable table, String path) {
        try (FileWriter fw = new FileWriter(path)) {
            TableModel mdl = table.getModel();
            for (int c = 0; c < mdl.getColumnCount(); c++) {
                fw.append(mdl.getColumnName(c)).append(c == mdl.getColumnCount() - 1 ? "\n" : ",");
            }
            for (int r = 0; r < mdl.getRowCount(); r++) {
                for (int c = 0; c < mdl.getColumnCount(); c++) {
                    Object val = mdl.getValueAt(r, c);
                    fw.append(val == null ? "" : val.toString())
                            .append(c == mdl.getColumnCount() - 1 ? "\n" : ",");
                }
            }
            JOptionPane.showMessageDialog(null, "CSV exported to: " + path);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "CSV export failed: " + ex.getMessage());
        }
    }
}
