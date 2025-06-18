package com.fasttracklogistics.dao;

import com.fasttracklogistics.config.DbConnection;
import com.fasttracklogistics.models.CustomerNotification;

import java.sql.*;
import java.util.*;

public class CustomerNotificationDao {

    public void saveNotification(CustomerNotification cn) {
        String sql = "INSERT INTO customer_notifications (customer_id, customer_name, email, message) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cn.getCustomerId());
            stmt.setString(2, cn.getCustomerName());
            stmt.setString(3, cn.getEmail());
            stmt.setString(4, cn.getMessage());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CustomerNotification> getAllNotifications() {
        List<CustomerNotification> list = new ArrayList<>();
        String sql = "SELECT * FROM customer_notifications";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new CustomerNotification(
                        rs.getString("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("email"),
                        rs.getString("message")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteNotification(String customerId, String email) {
        String sql = "DELETE FROM customer_notifications WHERE customer_id = ? AND email = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}