package com.fasttracklogistics.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/logistics";  // Make sure 'logistics' database exists
    private static final String USER = "root";       // Update if your MySQL username is different
    private static final String PASS = "1234";           // Add password if your MySQL has one

    private static Connection connection;

    // Private constructor to prevent instantiation
    private DbConnection() {}

    // Public method to get the database connection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }
}
