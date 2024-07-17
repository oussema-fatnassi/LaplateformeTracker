package com.example.controllers;

import com.example.utility.AdminDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {
    // FXML fields
    @FXML
    private Button loginAdmin;
    @FXML
    private Button backButton;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button togglePasswordVisibility;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    // Initialize visibility state
    @FXML
    private void initialize() {
        passwordTextField.setVisible(false);
        passwordTextField.setEditable(false);
    }
    // Handle button actions for login
    @FXML
    private void handleLoginAdminButtonAction(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if(passwordTextField.isVisible()){
            password = passwordTextField.getText();
        }
        if (AdminDAO.authenticateAdmin(email, password)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-main-menu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root, 1200, 800));
                stage.setTitle("Main Menu");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Login Failed", "Invalid email or password.");
        }
    }
    // Handle button actions for back button
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
    // Handle button actions for password visibility
    @FXML
    private void togglePasswordVisibility(ActionEvent event) {
        boolean isVisible = passwordTextField.isVisible();
        passwordTextField.setVisible(!isVisible);
        passwordField.setVisible(isVisible);
        passwordTextField.setManaged(!isVisible);
        passwordField.setManaged(isVisible);
        if (isVisible) {
            passwordField.setText(passwordTextField.getText());
            passwordTextField.setText("");
        } else {
            passwordTextField.setText(passwordField.getText());
            passwordField.setText("");
        }
    }
    // Show alert message for invalid login
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}