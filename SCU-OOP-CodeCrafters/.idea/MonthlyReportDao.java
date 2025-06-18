package com.fasttracklogistics.dao;

import com.fasttracklogistics.config.DbConnection;

import java.sql.*;
import java.time.YearMonth;
import java.util.*;

public class MonthlyReportDao {

    /**
     * Builds and executes multiple SQL queries to return grouped data by table.
     */
    public Map<String, List<List<Object>>> fetchGroupedReportData(YearMonth ym, List<String> tables, Map<String, List<String>> tableColumns) {
        Map<String, List<List<Object>>> result = new LinkedHashMap<>();

        try (Connection conn = DbConnection.getConnection()) {
            for (String table : tables) {
                List<String> columns = tableColumns.getOrDefault(table, new ArrayList<>());
                if (columns.isEmpty()) continue;

                String selectClause = String.join(", ", columns);
                StringBuilder sql = new StringBuilder("SELECT " + selectClause + " FROM " + table);

                if (table.equals("shipments")) {
                    sql.append(" WHERE MONTH(created_at) = ? AND YEAR(created_at) = ?");
                }

                try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                    if (table.equals("shipments")) {
                        stmt.setInt(1, ym.getMonthValue());
                        stmt.setInt(2, ym.getYear());
                    }

                    ResultSet rs = stmt.executeQuery();
                    List<List<Object>> tableData = new ArrayList<>();

                    while (rs.next()) {
                        List<Object> row = new ArrayList<>();
                        for (String col : columns) {
                            String alias = col;
                            if (alias.toLowerCase().contains(" as ")) {
                                alias = alias.substring(alias.toLowerCase().indexOf(" as ") + 4).trim();
                            } else if (alias.contains(".")) {
                                alias = alias.substring(alias.indexOf('.') + 1);
                            }
                            row.add(rs.getObject(alias));
                        }
                        tableData.add(row);
                    }

                    result.put(table, tableData);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
