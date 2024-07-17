package com.example.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
// This class is used to create and authenticate admin accounts
public class AdminDAO {
    // Create a new admin account
    public static boolean createAdmin(String firstName, String lastName, String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String sql = "INSERT INTO admin (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, hashedPassword);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Authenticate an admin account
    public static boolean authenticateAdmin(String email, String password) {
        String sql = "SELECT password FROM admin WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                return BCrypt.checkpw(password, hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}