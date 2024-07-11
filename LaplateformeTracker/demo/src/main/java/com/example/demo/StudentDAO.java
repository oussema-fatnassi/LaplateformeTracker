package com.example.demo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class StudentDAO {

    Connection connection = null;

    public void addStudent(String firstName, String lastName, String email, int age, double grade, String subject) {
        String query = "INSERT INTO student (first_name, last_name, email, age, grade, subject) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, age);
            preparedStatement.setDouble(5, grade);
            preparedStatement.setString(6, subject);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public void updateStudent(int id, String firstName, String lastName, String email, int age, double grade, String subject) {
        String query = "UPDATE student SET first_name = ?, last_name = ?, email = ?, age = ?, grade = ?, subject = ? WHERE id = ?";
        try {
            connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, age);
            preparedStatement.setDouble(5, grade);
            preparedStatement.setString(6, subject);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public void deleteStudent(int id) {
        String query = "DELETE FROM student WHERE id = ?";
        try {
            connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try {
            connection = DatabaseConnection.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                double grade = rs.getDouble("grade");
                String subject = rs.getString("subject");

                Student student = new Student(id, firstName, lastName, email, age, grade, subject);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return students;
    }

    public Student getStudentById(int id) {
        Student student = null;
        String sql = "SELECT * FROM student WHERE id = ?";

        try {
            connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email = rs.getString("email");
                    int age = rs.getInt("age");
                    double grade = rs.getDouble("grade");
                    String subject = rs.getString("subject");

                    student = new Student(id, firstName, lastName, email, age, grade, subject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return student;
    }

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

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}