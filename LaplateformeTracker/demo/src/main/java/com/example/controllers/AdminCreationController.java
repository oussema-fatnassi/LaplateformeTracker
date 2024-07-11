package com.example.controllers;

import com.example.demo.AccountCreation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;

public class AdminCreationController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button createAdmin;
    @FXML
    private Button backButton;

    @FXML
    private void handleCreateAdminButtonAction(ActionEvent event) {
        String firstNameText = firstName.getText();
        String lastNameText = lastName.getText();
        String emailText = email.getText();
        String passwordText = password.getText();
        String confirmPasswordText = confirmPassword.getText();

        if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
            showAlert("Invalid Input", "All fields must be filled.");
            return;
        }

        if (!validateName(firstNameText)) {
            showAlert("Invalid Input", "First name must not contain numbers or symbols.");
            return;
        }

        if (!validateName(lastNameText)) {
            showAlert("Invalid Input", "Last name must not contain numbers or symbols.");
            return;
        }

        if (!validateEmail(emailText)) {
            showAlert("Invalid Input", "Email must be valid and contain '@'.");
            return;
        }

        if (!validatePassword(passwordText)) {
            showAlert("Invalid Input", "Password must be at least 10 characters long, contain one uppercase letter, one lowercase letter, one number, and one symbol.");
            return;
        }

        if (!passwordText.equals(confirmPasswordText)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        boolean created = AccountCreation.createAdminAccount(firstNameText, lastNameText, emailText, passwordText);
        if (created) {
            showAlert("Success", "Admin account created successfully.");
        } else {
            showAlert("Error", "Failed to create admin account.");
        }
    }

    private boolean validateName(String name) {
        return name.matches("^[a-zA-Z]+$");
    }

    private boolean validateEmail(String email) {
        return email.contains("@");
    }

    private boolean validatePassword(String password) {
        boolean isValidLength = password.length() >= 10;
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[@#$%^&+=?.;].*");

        System.out.println("Password: " + password);
        System.out.println("Valid Length: " + isValidLength);
        System.out.println("Has Upper Case: " + hasUpperCase);
        System.out.println("Has Lower Case: " + hasLowerCase);
        System.out.println("Has Digit: " + hasDigit);
        System.out.println("Has Special Char: " + hasSpecialChar);

        return isValidLength && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/main-menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}