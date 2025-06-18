package com.fasttracklogistics.service;

import com.fasttracklogistics.config.DbConnection;
import com.fasttracklogistics.models.ShipmentStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackShipmentService {

    public List<ShipmentStatus> getShipmentsByPhone(String phone) {
        List<ShipmentStatus> results = new ArrayList<>();

        String sql = """
            SELECT s.shipment_id,
                   s.status,
                   s.delivered_at AS delivery_date,
                   '' AS delivery_time_slot
            FROM shipments s
            JOIN customers c ON s.customer_id = c.customer_id
            WHERE c.phone = ?
        """;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("delivery_date");
                    java.sql.Date deliveryDate = ts != null ? new java.sql.Date(ts.getTime()) : null;

                    ShipmentStatus status = new ShipmentStatus(
                            rs.getInt("shipment_id"),
                            rs.getString("status"),
                            deliveryDate,
                            rs.getString("delivery_time_slot")
                    );
                    results.add(status);
                }
            }

        } catch (SQLException e) {
            System.err.println("🚨 Error fetching shipments: " + e.getMessage());
            e.printStackTrace();
        }

        return results;
    }
}
