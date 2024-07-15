package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getGradesByStudentId(int studentId) {
        List<String> grades = new ArrayList<>();
        String sql = "SELECT subject, grade FROM grades WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String subject = resultSet.getString("subject");
                double grade = resultSet.getDouble("grade");
                grades.add(subject + ": " + grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public static List<StudentGrade> getAllStudentGrades() {
        List<StudentGrade> grades = new ArrayList<>();
        String sql = "SELECT first_name, last_name, subject, grade FROM studentAccount " +
                "JOIN grades ON studentAccount.id = grades.student_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String subject = resultSet.getString("subject");
                String grade = resultSet.getString("grade");
                grades.add(new StudentGrade(firstName, lastName, subject, grade));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }
}
