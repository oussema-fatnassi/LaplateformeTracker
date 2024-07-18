package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;

public class LandingPageController {
    // FXML fields
    @FXML
    private Button createAdmin;
    @FXML
    private Button loginAdmin;
    @FXML
    private Button loginStudent;
    @FXML
    private Button exit;
    // Handle button actions for creating admin account
    @FXML
    private void handleCreateAdminButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-account-creation.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Admin Account Creation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for logging in as admin
    @FXML
    private void handelLoginAdminButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Admin Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for logging in as student
    @FXML
    private void handleLoginStudentButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/student-login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Student Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for exiting the application
    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
}