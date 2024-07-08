package source;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void addStudent(String firstName, String lastName, String email, int age, double grade, String subject) {
        String query = "INSERT INTO student (first_name, last_name, email, age, grade, subject) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, age);
            preparedStatement.setDouble(5, grade);
            preparedStatement.setString(6, subject);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(int id, String firstName, String lastName, String email, int age, double grade, String subject) {
        String query = "UPDATE student SET first_name = ?, last_name = ?, email = ?, age = ?, grade = ?, subject = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
        }
    }

    public void deleteStudent(int id) {
        String query = "DELETE FROM student WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
        }
        return students;
    }

    public Student getStudentById(int id) {
        Student student = null;
        String sql = "SELECT * FROM student WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
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
        }
        return student;
    }

    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.addStudent("John", "Doe", "ciao", 25, 8.5, "Math");
        studentDAO.updateStudent(1, "Jane", "Doe", "hello", 30, 9.5, "Science");
        DatabaseConnection.closeConnection(conn);
    }
}
