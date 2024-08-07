package com.example.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ImportDataUtils {
    // List of valid subjects for student grades
    private static final List<String> VALID_SUBJECTS = Arrays.asList(
            "HTML/CSS Programming", "JavaScript Programming", "Python Programming", "Java Programming",
            "C++ Programming", "Cybersecurity", "Data Science", "Machine Learning", "Artificial Intelligence",
            "Augmented Reality", "Virtual Reality", "3D verse", "Unity", "Unreal Engine 5",
            "English", "French", "Soft Skills"
    );
    // Import student accounts from JSON file
    public static List<String> importStudentAccountsFromJSON(File file) {
        List<String> errorMessages = new ArrayList<>();
        // Read the file and parse the JSON data
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type studentListType = new TypeToken<List<StudentAccount>>() {}.getType();
            List<StudentAccount> students = gson.fromJson(reader, studentListType);
            // Iterate over the list of students
            for (StudentAccount student : students) {
                String validationMessage = getValidationMessage(student);
                if (validationMessage == null) {
                    if (!StudentAccountDAO.emailExists(student.getEmail())) {
                        boolean success = StudentAccountDAO.createStudent(
                                student.getFirstName(),
                                student.getLastName(),
                                student.getEmail(),
                                student.getAge(),
                                student.getPassword(),
                                student.getYear(),
                                student.getMajor()
                        );

                        if (!success) {
                            errorMessages.add("Failed to create student account for email: " + student.getEmail());
                        }
                    } else {
                        errorMessages.add("Email already exists: " + student.getEmail());
                    }
                } else {
                    errorMessages.add("Invalid student data for email: " + student.getEmail() + ". " + validationMessage);
                }
            }
        } catch (IOException e) {
            errorMessages.add("Failed to read the file: " + e.getMessage());
        }
        return errorMessages;
    }
    // Import student grades from JSON file
    public static List<String> importStudentGradesFromJSON(File file) {
        List<String> errorMessages = new ArrayList<>();
        // Read the file and parse the JSON data
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type gradeListType = new TypeToken<List<StudentGrade>>() {}.getType();
            List<StudentGrade> grades = gson.fromJson(reader, gradeListType);

            for (StudentGrade grade : grades) {
                // Validate student existence
                boolean studentExists = StudentAccountDAO.studentExists(grade.getFullName());
                if (!studentExists) {
                    errorMessages.add("Student not registered: " + grade.getFirstName() + " " + grade.getLastName());
                    continue; // Skip further validation for this grade
                }
                // Validate subject
                if (!isValidSubject(grade.getSubject())) {
                    errorMessages.add("Invalid subject: " + grade.getSubject() + " for student: " + grade.getFirstName() + " " + grade.getLastName());
                    continue; // Skip further validation for this grade
                }
                // Validate grade
                try {
                    double parsedGrade = grade.getGrade();
                    if (parsedGrade < 0.0 || parsedGrade > 100.0) {
                        errorMessages.add("Invalid grade: " + parsedGrade + " for student: " + grade.getFirstName() + " " + grade.getLastName() + ", subject: " + grade.getSubject());
                        continue; // Skip further validation for this grade
                    }
                } catch (NumberFormatException e) {
                    errorMessages.add("Invalid grade format: " + grade.getGrade() + " for student: " + grade.getFirstName() + " " + grade.getLastName() + ", subject: " + grade.getSubject());
                    continue; // Skip further validation for this grade
                }
                // If all validations pass, add the grade
                boolean success = GradeDAO.addGrade(
                        grade.getFullName(),
                        grade.getSubject(),
                        grade.getGrade()
                );
                if (!success) {
                    errorMessages.add("Failed to add grade for student: " + grade.getFirstName() + " " + grade.getLastName() + ", subject: " + grade.getSubject());
                }
            }
        } catch (IOException e) {
            errorMessages.add("Failed to read or parse the file: " + e.getMessage());
        }
        return errorMessages;
    }
    // Import student accounts from CSV file
    private static boolean isValidSubject(String subject) {
        return VALID_SUBJECTS.contains(subject);
    }
    // Validate student account data before importing to the database
    private static String getValidationMessage(StudentAccount student) {
        List<String> validationErrors = new ArrayList<>();

        if (student.getFirstName() == null || student.getFirstName().isEmpty()) {
            validationErrors.add("Missing or empty first name.");
        } else if (!validateName(student.getFirstName())) {
            validationErrors.add("Invalid first name. It must not contain numbers or symbols.");
        }

        if (student.getLastName() == null || student.getLastName().isEmpty()) {
            validationErrors.add("Missing or empty last name.");
        } else if (!validateName(student.getLastName())) {
            validationErrors.add("Invalid last name. It must not contain numbers or symbols.");
        }

        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            validationErrors.add("Missing or empty email.");
        } else if (!validateEmail(student.getEmail())) {
            validationErrors.add("Invalid email format.");
        }

        if (student.getAge() <= 0) {
            validationErrors.add("Invalid or missing age. Age must be a positive integer.");
        }

        if (student.getPassword() == null || student.getPassword().isEmpty()) {
            validationErrors.add("Missing or empty password.");
        } else if (!validatePassword(student.getPassword())) {
            validationErrors.add("Invalid password. Password must be at least 10 characters long, contain one uppercase letter, one lowercase letter, one number, and one symbol.");
        }

        if (student.getYear() == null || student.getYear().isEmpty()) {
            validationErrors.add("Missing or empty year.");
        }

        if (student.getMajor() == null || student.getMajor().isEmpty()) {
            validationErrors.add("Missing or empty major.");
        }

        if (!validationErrors.isEmpty()) {
            return String.join(" ", validationErrors);
        } else {
            return null;
        }
    }
    // Validate name format
    private static boolean validateName(String name) {
        return name.matches("^[a-zA-Z]+$");
    }
    // Validate email format
    private static boolean validateEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    // Validate password format
    private static boolean validatePassword(String password) {
        boolean isValidLength = password.length() >= 10;
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[@#$%^&+=!?.;].*");

        return isValidLength && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
    // Show alert dialog with the import results
    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}