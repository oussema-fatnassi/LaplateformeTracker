package com.example.controllers;

import com.example.demo.AccountCreation;
import com.example.demo.AdminAccountDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.regex.Pattern;

public class AdminCreationController {

    @FXML
    private Label ruleLength;
    @FXML
    private Label ruleUpperCase;
    @FXML
    private Label ruleLowerCase;
    @FXML
    private Label ruleDigit;
    @FXML
    private Label ruleSpecialChar;
    @FXML
    private Label ruleEmail;
    @FXML
    private Button togglePasswordVisibility;
    @FXML
    private Button toggleConfirmPasswordVisibility;
    @FXML
    private TextField confirmPasswordTextField;
    @FXML
    private TextField passwordTextField;
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
    private void initialize(){
        // Add listeners to update password rules labels
        password.textProperty().addListener((observable, oldValue, newValue) -> validatePassword(newValue));
        confirmPassword.textProperty().addListener((observable, oldValue, newValue) -> validatePassword(newValue));
        email.textProperty().addListener((observable, oldValue, newValue) -> validateEmail(newValue));


        // Initialize visibility state
        passwordTextField.setVisible(false);
        passwordTextField.setEditable(false);
        confirmPasswordTextField.setVisible(false);
        confirmPasswordTextField.setEditable(false);
        initializePasswordRules();
    }

    @FXML
    private void handleCreateAdminButtonAction(ActionEvent event) {
        String firstNameText = firstName.getText();
        String lastNameText = lastName.getText();
        String emailText = email.getText();
        String passwordText = password.getText();
        String confirmPasswordText = confirmPassword.getText();

        if(passwordTextField.isVisible()){
            passwordText = passwordTextField.getText();
        }
        if(confirmPasswordTextField.isVisible()){
            confirmPasswordText = confirmPasswordTextField.getText();
        }

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

        if(ruleEmail.getTextFill() == Color.RED) {
            showAlert("Invalid Input", "Email must be valid and contain '@'.");
            return;
        }

        if (ruleLength.getTextFill() == Color.RED || ruleUpperCase.getTextFill() == Color.RED ||
                ruleLowerCase.getTextFill() == Color.RED || ruleDigit.getTextFill() == Color.RED ||
                ruleSpecialChar.getTextFill() == Color.RED) {
            showAlert("Invalid Input", "Password must meet all criteria.");
            return;
        }

        if (!passwordText.equals(confirmPasswordText)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        // Check if the email is allowed to create admin account
        if (!AdminAccountDAO.isAdminEmail(emailText)) {
            showAlert("Error", "You are not authorized to create an admin account.");
            return;
        }

        boolean created = AccountCreation.createAdminAccount(firstNameText, lastNameText, emailText, passwordText);
        if (created) {
            showAlert("Success", "Admin account created successfully.");
            clearFields();
        } else {
            showAlert("Error", "Failed to create admin account.");
        }
    }

    private boolean validateName(String name) {
        return name.matches("^[a-zA-Z]+$");
    }

    private void validateEmail(String email) {
        boolean isValidEmail = validateEmailFormat(email);
        updateLabel(ruleEmail, isValidEmail);
    }

    private boolean validateEmailFormat(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    @FXML
    private void validatePassword(String password) {
        boolean isValidLength = password.length() >= 10;
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[@#$%^&+=!?.;].*");

        updateLabel(ruleLength, isValidLength);
        updateLabel(ruleUpperCase, hasUpperCase);
        updateLabel(ruleLowerCase, hasLowerCase);
        updateLabel(ruleDigit, hasDigit);
        updateLabel(ruleSpecialChar, hasSpecialChar);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void initializePasswordRules() {
        ruleLength.setText("Password must be at least 10 characters long");
        ruleUpperCase.setText("Password must contain at least one uppercase letter");
        ruleLowerCase.setText("Password must contain at least one lowercase letter");
        ruleDigit.setText("Password must contain at least one digit");
        ruleSpecialChar.setText("Password must contain at least one special character (@#$%^&+=!?.;)");
        ruleEmail.setText("Email must be valid with '@' and a domain name");
        updateLabel(ruleLength, false);
        updateLabel(ruleUpperCase, false);
        updateLabel(ruleLowerCase, false);
        updateLabel(ruleDigit, false);
        updateLabel(ruleSpecialChar, false);
        updateLabel(ruleEmail, false);
    }

    private void updateLabel(Label label, boolean isValid) {
        if (isValid) {
            label.setTextFill(Color.GREEN);
        } else {
            label.setTextFill(Color.RED);
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/landing-page.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields() {
        firstName.clear();
        lastName.clear();
        email.clear();
        password.clear();
        confirmPassword.clear();
    }

    @FXML
    private void togglePasswordVisibility(ActionEvent event) {
        boolean isVisible = passwordTextField.isVisible();
        passwordTextField.setVisible(!isVisible);
        password.setVisible(isVisible);
        passwordTextField.setManaged(!isVisible);
        password.setManaged(isVisible);

        if (isVisible) {
            password.setText(passwordTextField.getText());
            passwordTextField.setText("");
        } else {
            passwordTextField.setText(password.getText());
            password.setText("");
        }

        // Validate the correct field based on visibility state
        validatePassword(isVisible ? password.getText() : passwordTextField.getText());
    }

    @FXML
    private void toggleConfirmPasswordVisibility(ActionEvent event) {
        boolean isVisible = confirmPasswordTextField.isVisible();
        confirmPasswordTextField.setVisible(!isVisible);
        confirmPassword.setVisible(isVisible);
        confirmPasswordTextField.setManaged(!isVisible);
        confirmPassword.setManaged(isVisible);

        if (isVisible) {
            confirmPassword.setText(confirmPasswordTextField.getText());
            confirmPasswordTextField.setText("");
        } else {
            confirmPasswordTextField.setText(confirmPassword.getText());
            confirmPassword.setText("");
        }

        // Validate the correct field based on visibility state
        validatePassword(isVisible ? confirmPassword.getText() : confirmPasswordTextField.getText());
    }

}
