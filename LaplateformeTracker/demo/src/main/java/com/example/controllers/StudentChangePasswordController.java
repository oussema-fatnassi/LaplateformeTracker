package com.example.controllers;

import com.example.demo.StudentAccountDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StudentChangePasswordController {

    @FXML
    private TextField currentPasswordTextField;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private TextField confirmPasswordTextField;
    @FXML
    private Button toggleCurrentPasswordVisibility;
    @FXML
    private Button toggleNewPasswordVisibility;
    @FXML
    private Button toggleConfirmPasswordVisibility;
    @FXML
    private PasswordField currentPassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button password;
    @FXML
    private Button back;
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

    private int studentId;

    @FXML
    private void initialize() {
        // Add listeners to update password rules labels
        newPassword.textProperty().addListener((observable, oldValue, newValue) -> updatePasswordRules(newValue));
        confirmPassword.textProperty().addListener((observable, oldValue, newValue) -> updatePasswordRules(newValue));

        // Initialize visibility state
        currentPasswordTextField.setVisible(false);
        currentPasswordTextField.setEditable(false);
        newPasswordTextField.setVisible(false);
        newPasswordTextField.setEditable(false);
        confirmPasswordTextField.setVisible(false);
        confirmPasswordTextField.setEditable(false);

        initializePasswordRules();
    }

    @FXML
    private void handleChangePasswordAction(ActionEvent event) {
        String currentPass = currentPassword.getText();
        String newPass = newPassword.getText();
        String confirmPass = confirmPassword.getText();

        if (!StudentAccountDAO.authenticateStudentById(studentId, currentPass)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Current password is incorrect.");
            return;
        }

        if (!validateNewPassword(newPass)) {
            showAlert(Alert.AlertType.ERROR, "Error", "New password does not follow the rules.");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showAlert(Alert.AlertType.ERROR, "Error", "New password and confirm password do not match.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change your password?");
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (StudentAccountDAO.changePassword(studentId, currentPass, newPass)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Password changed successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to change password.");
                }
            }
        });
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/student-main-menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    private boolean validateNewPassword(String password) {
        boolean isValidLength = password.length() >= 10;
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[@#$%^&+=!?.;].*");

        return isValidLength && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    private void updatePasswordRules(String password) {
        updateLabel(ruleLength, password.length() >= 10);
        updateLabel(ruleUpperCase, password.matches(".*[A-Z].*"));
        updateLabel(ruleLowerCase, password.matches(".*[a-z].*"));
        updateLabel(ruleDigit, password.matches(".*\\d.*"));
        updateLabel(ruleSpecialChar, password.matches(".*[@#$%^&+=!?.;].*"));
    }

    private void initializePasswordRules() {
        ruleLength.setText("Password must be at least 10 characters long");
        ruleUpperCase.setText("Password must contain at least one uppercase letter");
        ruleLowerCase.setText("Password must contain at least one lowercase letter");
        ruleDigit.setText("Password must contain at least one digit");
        ruleSpecialChar.setText("Password must contain at least one special character (@#$%^&+=!?.;)");
        updateLabel(ruleLength, false);
        updateLabel(ruleUpperCase, false);
        updateLabel(ruleLowerCase, false);
        updateLabel(ruleDigit, false);
        updateLabel(ruleSpecialChar, false);
    }

    private void updateLabel(Label label, boolean isValid) {
        if (isValid) {
            label.setTextFill(Color.GREEN);
        } else {
            label.setTextFill(Color.RED);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void toggleCurrentPasswordVisibility(ActionEvent event) {
        boolean isVisible = currentPasswordTextField.isVisible();
        currentPasswordTextField.setVisible(!isVisible);
        currentPassword.setVisible(isVisible);
        currentPasswordTextField.setManaged(!isVisible);
        currentPassword.setManaged(isVisible);

        if (isVisible) {
            currentPassword.setText(currentPasswordTextField.getText());
            currentPasswordTextField.setText("");
        } else {
            currentPasswordTextField.setText(currentPassword.getText());
            currentPassword.setText("");
        }

        updatePasswordRules(isVisible ? currentPassword.getText() : currentPasswordTextField.getText());
    }

    @FXML
    private void toggleNewPasswordVisibility(ActionEvent event) {
        boolean isVisible = newPasswordTextField.isVisible();
        newPasswordTextField.setVisible(!isVisible);
        newPassword.setVisible(isVisible);
        newPasswordTextField.setManaged(!isVisible);
        newPassword.setManaged(isVisible);

        if (isVisible) {
            newPassword.setText(newPasswordTextField.getText());
            newPasswordTextField.setText("");
        } else {
            newPasswordTextField.setText(newPassword.getText());
            newPassword.setText("");
        }

        updatePasswordRules(isVisible ? newPassword.getText() : newPasswordTextField.getText());
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

        updatePasswordRules(isVisible ? confirmPassword.getText() : confirmPasswordTextField.getText());
    }
}
