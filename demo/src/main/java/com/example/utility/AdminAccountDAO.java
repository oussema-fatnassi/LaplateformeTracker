package com.example.utility;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AdminAccountDAO {

    private static final String ADMIN_ACCOUNTS_TABLE = "adminAccounts";

    public static boolean isAdminEmail(String email) {
        String sql = "SELECT email FROM " + ADMIN_ACCOUNTS_TABLE;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String hashedEmail = resultSet.getString("email");
                if (BCrypt.checkpw(email, hashedEmail)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static String hashEmail(String email) {
        return BCrypt.hashpw(email, BCrypt.gensalt());
    }

    public static void addAdminEmails(List<String> emails) {
        String sql = "INSERT INTO adminAccounts (email) VALUES (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (String email : emails) {
                String hashedEmail = hashEmail(email);
                statement.setString(1, hashedEmail);
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> emails = Arrays.asList("test@laplateforme.io");
        addAdminEmails(emails);
    }
}