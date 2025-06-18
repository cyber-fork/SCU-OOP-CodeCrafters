package com.fasttracklogistics.dao;

import com.fasttracklogistics.config.DbConnection;
import com.fasttracklogistics.models.Assignment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDao {

    public void assignDriver(Assignment a) {
        String sql = "INSERT INTO assignments (shipment_id, driver_id) VALUES (?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getShipmentId());
            stmt.setInt(2, a.getDriverId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAssignment(int assignmentId) {
        String sql = "DELETE FROM assignments WHERE assignment_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, assignmentId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Assignment> getAllAssignments() {
        List<Assignment> list = new ArrayList<>();
        String sql = "SELECT * FROM assignments";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Assignment a = new Assignment(
                        rs.getInt("assignment_id"),
                        rs.getInt("shipment_id"),
                        rs.getInt("driver_id")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
