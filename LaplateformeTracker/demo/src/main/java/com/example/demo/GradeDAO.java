package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeDAO {

    public static boolean addGrade(String studentFullName, String subject, double grade) {
        if (grade < 0.0 || grade > 100.0) {
            return false; // Invalid grade range
        }

        String studentIdQuery = "SELECT id FROM studentAccount WHERE CONCAT(first_name, ' ', last_name) = ?";
        String insertGradeQuery = "INSERT INTO grades (student_id, subject, grade) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement idStatement = connection.prepareStatement(studentIdQuery);
             PreparedStatement gradeStatement = connection.prepareStatement(insertGradeQuery)) {

            // Get student ID
            idStatement.setString(1, studentFullName);
            ResultSet resultSet = idStatement.executeQuery();
            if (resultSet.next()) {
                int studentId = resultSet.getInt("id");

                // Insert grade with student ID
                gradeStatement.setInt(1, studentId);
                gradeStatement.setString(2, subject);
                gradeStatement.setDouble(3, grade);
                return gradeStatement.executeUpdate() > 0;
            } else {
                return false; // Student not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
