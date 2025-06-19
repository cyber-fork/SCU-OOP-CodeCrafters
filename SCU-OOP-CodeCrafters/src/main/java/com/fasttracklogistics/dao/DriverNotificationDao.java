package com.fasttracklogistics.dao;

import com.fasttracklogistics.config.DbConnection;
import com.fasttracklogistics.models.DriverNotification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverNotificationDao {

    public void saveNotification(DriverNotification dn) {
        String sql = "INSERT INTO driver_notifications (driver_id, driver_name, email, message) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dn.getDriverId());
            stmt.setString(2, dn.getDriverName());
            stmt.setString(3, dn.getEmail());
            stmt.setString(4, dn.getMessage());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNotification(String driverId, String email) {
        String sql = "DELETE FROM driver_notifications WHERE driver_id = ? AND email = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, driverId);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DriverNotification> fetchAllNotifications() {
        List<DriverNotification> list = new ArrayList<>();
        String sql = "SELECT driver_id, driver_name, email, message FROM driver_notifications";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DriverNotification dn = new DriverNotification(
                        rs.getString("driver_id"),
                        rs.getString("driver_name"),
                        rs.getString("email"),
                        rs.getString("message")
                );
                list.add(dn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
