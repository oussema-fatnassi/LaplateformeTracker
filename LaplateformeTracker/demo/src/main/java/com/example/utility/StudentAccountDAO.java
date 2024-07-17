package com.example.utility;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAccountDAO {
    // Create a new student account
    public static boolean createStudent(String firstName, String lastName, String email, int age, String password, String year, String major) {
        String hashedPassword = hashPassword(password);
        String sql = "INSERT INTO studentAccount (first_name, last_name, email, age, password, year, major) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setInt(4, age);
            statement.setString(5, hashedPassword);
            statement.setString(6, year);
            statement.setString(7, major);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Hash the password using BCrypt
    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    // Authenticate a student account
    public static int authenticateStudent(String email, String password) {
        String sql = "SELECT id, password FROM studentAccount WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                int studentId = resultSet.getInt("id");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    return studentId; // Return student ID if authentication is successful
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if authentication fails
    }
    // Get all students ordered by last name
    public static List<String> getAllStudentsOrdered() {
        List<String> students = new ArrayList<>();
        String sql = "SELECT first_name, last_name FROM studentAccount ORDER BY last_name ASC";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                students.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    // Get all students ordered by first name
    public static List<String> getFilteredStudents(String selectedYear, String selectedMajor) {
        List<String> students = new ArrayList<>();
        String sql = "SELECT first_name, last_name FROM studentAccount WHERE year = ? AND major = ? ORDER BY last_name ASC";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, selectedYear);
            statement.setString(2, selectedMajor);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                students.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    // Get all students ordered by year
    public static List<String> getStudentsByYear(String selectedYear) {
        List<String> students = new ArrayList<>();
        String sql = "SELECT first_name, last_name FROM studentAccount WHERE year = ? ORDER BY last_name ASC";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, selectedYear);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                students.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    // Get all students ordered by major
    public static List<String> getStudentsByMajor(String selectedMajor) {
        List<String> students = new ArrayList<>();
        String sql = "SELECT first_name, last_name FROM studentAccount WHERE major = ? ORDER BY last_name ASC";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, selectedMajor);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                students.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    // Get all students ordered by age
    public static List<String> getStudentFirstName() {
        List<String> firstNames = new ArrayList<>();
        String sql = "SELECT first_name FROM studentAccount";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                firstNames.add(resultSet.getString("first_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstNames;
    }
    // Get all students ordered by age
    public static List<String> getStudentLastName() {
        List<String> lastNames = new ArrayList<>();
        String sql = "SELECT last_name FROM studentAccount";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                lastNames.add(resultSet.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastNames;
    }
    // Get all students ordered by age
    public static List<String> getStudentEmail() {
        List<String> emails = new ArrayList<>();
        String sql = "SELECT email FROM studentAccount";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                emails.add(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    // Get all students ordered by age
    public static List<String> getStudentMajor() {
        List<String> majors = new ArrayList<>();
        String sql = "SELECT major FROM studentAccount";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                majors.add(resultSet.getString("major"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return majors;
    }
    // Get all students ordered by age
    public static List<String> getStudentYear() {
        List<String> years = new ArrayList<>();
        String sql = "SELECT year FROM studentAccount";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                years.add(resultSet.getString("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return years;
    }
    //  Get list of all majors
    public static List<String> getAllMajors() {
        List<String> majors = new ArrayList<>();
        String sql = "SELECT DISTINCT major FROM studentAccount ORDER BY major ASC";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                majors.add(resultSet.getString("major"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return majors;
    }
    // Get list of all students
    public static List<StudentAccount> getAllStudents() {
        List<StudentAccount> students = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, email,age, major, year FROM studentAccount";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                String major = resultSet.getString("major");
                String year = resultSet.getString("year");
                students.add(new StudentAccount(id, firstName, lastName, email,age, major, year));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    // Delete a student account by ID
    public static void deleteStudent(int id) {
        String sql = "DELETE FROM studentAccount WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Search for students by a specific parameter
    public static List<StudentAccount> searchStudents(String parameter, String value) {
        List<StudentAccount> students = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, email,age, major, year FROM studentAccount WHERE ";

        switch (parameter) {
            case "First Name":
                sql += "first_name LIKE ?";
                break;
            case "Last Name":
                sql += "last_name LIKE ?";
                break;
            case "Email":
                sql += "email LIKE ?";
                break;
            case "Age":
                sql += "age LIKE ?";
                break;
            case "Major":
                sql += "major LIKE ?";
                break;
            case "Year":
                sql += "year LIKE ?";
                break;
            default:
                return students;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + value + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                String major = resultSet.getString("major");
                String year = resultSet.getString("year");
                students.add(new StudentAccount(id, firstName, lastName, email,age, major, year));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    // Check if a student account exists by email
    public static boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM studentAccount WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Get student account by Major
    public static Map<String, Integer> getStudentCountByMajor() {
        List<StudentAccount> students = getAllStudents();
        Map<String, Integer> countByMajor = new HashMap<>();
        for (StudentAccount student : students) {
            countByMajor.put(student.getMajor(), countByMajor.getOrDefault(student.getMajor(), 0) + 1);
        }
        return countByMajor;
    }
    // Get student account by Year
    public static Map<String, Integer> getStudentCountByYear() {
        List<StudentAccount> students = getAllStudents();
        Map<String, Integer> countByYear = new HashMap<>();
        for (StudentAccount student : students) {
            countByYear.put(student.getYear(), countByYear.getOrDefault(student.getYear(), 0) + 1);
        }
        return countByYear;
    }
    // Get student account by Age Distribution
    public static Map<String, Integer> getStudentAgeDistribution() {
        List<StudentAccount> students = getAllStudents();
        Map<String, Integer> ageDistribution = new HashMap<>();
        for (StudentAccount student : students) {
            String ageCategory = AgeUtils.calculateAgeCategory(student.getAge());
            ageDistribution.put(ageCategory, ageDistribution.getOrDefault(ageCategory, 0) + 1);
        }
        return ageDistribution;
    }
    // Check if a student account exists by full name
    public static boolean studentExists(String fullName) {
        String query = "SELECT COUNT(*) AS count FROM studentAccount WHERE CONCAT(first_name, ' ', last_name) = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fullName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Change the password for a student account
    public static boolean changePassword(int studentId, String currentPassword, String newPassword) {
        String query = "SELECT password FROM studentAccount WHERE id = ?";
        String updateQuery = "UPDATE studentAccount SET password = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                // Check if the current password is correct
                if (BCrypt.checkpw(currentPassword, hashedPassword)) {
                    // Hash the new password
                    String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                    updateStatement.setString(1, newHashedPassword);
                    updateStatement.setInt(2, studentId);
                    updateStatement.executeUpdate();
                    return true;
                } else {
                    return false; // Current password is incorrect
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Authenticate a student account by ID
    public static boolean authenticateStudentById(int studentId, String password) {
        String query = "SELECT password FROM studentAccount WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, studentId);
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