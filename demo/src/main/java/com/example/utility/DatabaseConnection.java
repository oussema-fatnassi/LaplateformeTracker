package com.example.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// DatabaseConnection class to connect to the database
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/laplateformeTracker";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    // Connect to the database and return the connection
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return connection;
    }
    // Close the connection to the database
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}