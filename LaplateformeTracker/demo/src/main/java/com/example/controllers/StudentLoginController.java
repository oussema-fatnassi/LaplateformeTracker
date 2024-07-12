package com.example.controllers;

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
import com.example.demo.StudentDAO;

import java.io.IOException;

public class StudentLoginController {

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    @FXML
    private Button back;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String emailText = email.getText();
        String passwordText = password.getText();

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            showAlert("Invalid Input", "All fields must be filled.");
            return;
        }

        if (StudentDAO.authenticateStudent(emailText, passwordText)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/student-main-menu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root, 1200, 800));
                stage.setTitle("Student Main Menu");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Login Failed", "Incorrect email or password.");
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
