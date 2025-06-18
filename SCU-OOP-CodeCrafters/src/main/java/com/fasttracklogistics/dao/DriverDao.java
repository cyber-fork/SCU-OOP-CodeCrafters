// dao/DriverDao.java
package com.fasttracklogistics.dao;

import com.fasttracklogistics.config.DbConnection;
import com.fasttracklogistics.models.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDao {
    public void addDriver(Driver driver) throws SQLException {
        Connection conn = DbConnection.getConnection();
        String sql = "INSERT INTO drivers (driver_id, name, email, phone, license_no) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, driver.getDriverId());
        ps.setString(2, driver.getName());
        ps.setString(3, driver.getEmail());
        ps.setString(4, driver.getPhone());
        ps.setString(5, driver.getLicenseNo());
        ps.executeUpdate();
    }

    public void deleteDriver(String driverId) throws SQLException {
        Connection conn = DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM drivers WHERE driver_id=?");
        ps.setString(1, driverId);
        ps.executeUpdate();
    }

    public List<Driver> getAllDrivers() throws SQLException {
        List<Driver> list = new ArrayList<>();
        Connection conn = DbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM drivers");

        while (rs.next()) {
            list.add(new Driver(
                    rs.getString("driver_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("license_no")
            ));
        }
        return list;
    }

    // Add updateDriver(...) if needed
}
