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

public class StudentCreationController {

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

    @FXML
    private void initialize() {
        year.getItems().addAll("Bachelor1", "Bachelor2", "Bachelor3", "Master1", "Master2");
        major.getItems().addAll("Web Development", "Software Development", "Cybersecurity", "AI Development", "AR/VR Development");
    }

    @FXML
    private void handleCreateStudentButtonAction(ActionEvent event) {
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String email = this.email.getText();
        String age = this.age.getText();
        String password = this.password.getText();
        String year = this.year.getValue();
        String major = this.major.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || age.isEmpty() || password.isEmpty() || year == null || major == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        if (StudentAccountDAO.emailExists(email)) {
            showAlert("An account with this email already exists. Use another email.");
            return;
        }

        boolean success = StudentAccountDAO.createStudent(firstName, lastName, email, Integer.parseInt(age), password, year, major);
        if (success) {
            showAlert("Student created successfully.");
            clearFields();
        } else {
            showAlert("Failed to create student.");
        }
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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
