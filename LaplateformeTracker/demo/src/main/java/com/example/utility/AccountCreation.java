package com.example.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class AccountCreation {
    // Create a new admin account
    public static boolean createAdminAccount(String firstName, String lastName, String email, String password) {
        String hashedPassword = hashPassword(password);
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO admin (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.setString(4, hashedPassword);
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Hash the password using BCrypt
    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    // Authenticate an admin account
    public static boolean authenticateAdmin(String email, String password) {
        return AdminDAO.authenticateAdmin(email, password);
    }
}