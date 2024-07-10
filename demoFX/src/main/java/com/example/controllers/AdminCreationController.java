package com.example.controllers;

import com.example.demofx.AccountCreation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;

public class AdminCreationController {

    @FXML
    private TextArea firstName;
    @FXML
    private TextArea lastName;
    @FXML
    private TextArea email;
    @FXML
    private TextArea password;
    @FXML
    private TextArea confirmPassword;
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

        if (passwordText.equals(confirmPasswordText)) {
            boolean created = AccountCreation.createAdminAccount(firstNameText, lastNameText, emailText, passwordText);
            if (created) {
                showAlert("Success", "Admin account created successfully.");
                // Redirect to another scene if needed
            } else {
                showAlert("Error", "Failed to create admin account.");
            }
        } else {
            showAlert("Error", "Passwords do not match.");
        }
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
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demofx/main-menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
