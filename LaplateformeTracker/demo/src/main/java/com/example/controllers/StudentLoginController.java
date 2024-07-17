package com.example.controllers;

import com.example.utility.StudentAccountDAO;
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

public class StudentLoginController {
    // FXML fields
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button togglePasswordVisibility;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    @FXML
    private Button back;
    // Initialize visibility state for password text field
    @FXML
    private void initialize() {
        passwordTextField.setVisible(false);
        passwordTextField.setEditable(false);
    }
    // Handle button actions for login and toggle password visibility
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String emailText = email.getText();
        String passwordText = password.getText();
        if(passwordTextField.isVisible()){
            passwordText = passwordTextField.getText();
        }

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            showAlert("Invalid Input", "All fields must be filled.");
            return;
        }

        int studentId = StudentAccountDAO.authenticateStudent(emailText, passwordText);
        if (studentId > 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/student-main-menu.fxml"));
                Parent root = loader.load();
                // Pass the student ID to the MainMenuStudentController
                StudentMainMenuController controller = loader.getController();
                controller.setStudentId(studentId);
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
    // Handle button actions for back button to return to the main menu
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
        password.setVisible(isVisible);
        passwordTextField.setManaged(!isVisible);
        password.setManaged(isVisible);

        if(isVisible){
            password.setText(passwordTextField.getText());
            passwordTextField.setText("");
        } else {
            passwordTextField.setText(password.getText());
            password.setText("");
        }
    }
    // Show an alert with the given title and content
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}