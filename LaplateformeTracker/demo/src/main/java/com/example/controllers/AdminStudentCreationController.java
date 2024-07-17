package com.example.controllers;

import com.example.demo.StudentAccountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class AdminStudentCreationController {
    // FXML fields
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField age;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<String> year;
    @FXML
    private ComboBox<String> major;
    @FXML
    private Button createStudent;
    @FXML
    private Button backButton;
    // Initialize the ComboBoxes
    @FXML
    private void initialize() {
        year.getItems().addAll("Bachelor1", "Bachelor2", "Bachelor3", "Master1", "Master2");
        major.getItems().addAll("Web Development", "Software Development", "Cybersecurity", "AI Development", "AR/VR Development");
    }
    // Handle button actions for creating student account
    @FXML
    private void handleCreateStudentButtonAction(ActionEvent event) {
        String firstNameText = this.firstName.getText();
        String lastNameText = this.lastName.getText();
        String emailText = this.email.getText();
        String ageText = this.age.getText();
        String passwordText = this.password.getText();
        String yearValue = this.year.getValue();
        String majorValue = this.major.getValue();
        // Check if all fields are filled
        if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() || ageText.isEmpty() || passwordText.isEmpty() || yearValue == null || majorValue == null) {
            showAlert("Invalid Input", "All fields must be filled.");
            return;
        }
        // Validate the first name input fields
        if (!validateName(firstNameText)) {
            showAlert("Invalid Input", "First name must not contain numbers or symbols.");
            return;
        }
        // Validate the last name input fields
        if (!validateName(lastNameText)) {
            showAlert("Invalid Input", "Last name must not contain numbers or symbols.");
            return;
        }
        // Validate the email input fields
        if (!validateEmail(emailText)) {
            showAlert("Invalid Input", "Email must be valid and contain '@'.");
            return;
        }
        // Validate the age input fields
        if (!validateAge(ageText)) {
            showAlert("Invalid Input", "Age must be a positive integer.");
            return;
        }
        // Validate the password input fields
        if (!validatePassword(passwordText)) {
            showAlert("Invalid Input", "Password must be at least 10 characters long, contain one uppercase letter, one lowercase letter, one number, and one symbol.");
            return;
        }
        // Check if the email already exists
        if (StudentAccountDAO.emailExists(emailText)) {
            showAlert("Error", "An account with this email already exists. Use another email.");
            return;
        }
        // Create the student account
        boolean success = StudentAccountDAO.createStudent(firstNameText, lastNameText, emailText, Integer.parseInt(ageText), passwordText, yearValue, majorValue);
        if (success) {
            showAlert("Success", "Student created successfully.");
            clearFields();
        } else {
            showAlert("Error", "Failed to create student.");
        }
    }
    // Validate the first name input fields
    private boolean validateName(String name) {
        return name.matches("^[a-zA-Z]+$");
    }
    // Validate the email input fields
    private boolean validateEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    // Validate the age input fields
    private boolean validateAge(String age) {
        try {
            int ageValue = Integer.parseInt(age);
            return ageValue > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    // Validate the password input fields
    private boolean validatePassword(String password) {
        boolean isValidLength = password.length() >= 10;
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[@#$%^&+=!?.;].*");

        return isValidLength && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
    // Show an alert with the given title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Handle button actions for back button
    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-main-menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Clear all input fields
    private void clearFields() {
        firstName.clear();
        lastName.clear();
        email.clear();
        age.clear();
        password.clear();
        year.setValue(null);
        major.setValue(null);
    }
}