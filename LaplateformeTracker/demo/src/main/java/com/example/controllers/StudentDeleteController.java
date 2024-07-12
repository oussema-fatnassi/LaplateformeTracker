package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import com.example.demo.StudentDAO;

import java.io.IOException;
import java.util.List;

public class StudentDeleteController {

    @FXML
    private ComboBox<String> year;
    @FXML
    private ComboBox<String> major;
    @FXML
    private ComboBox<String> account;
    @FXML
    private Button delete;
    @FXML
    private Button back;

    @FXML
    private void initialize() {
        year.getItems().addAll("Bachelor1", "Bachelor2", "Bachelor3", "Master1", "Master2");
        major.getItems().addAll("Web Development", "Software Development", "Cybersecurity", "AI Development", "AR/VR Development");

        // Load all student accounts initially
        loadStudentAccounts(null, null);

        // Add listeners to ComboBoxes
        year.setOnAction(event -> filterStudentAccounts());
        major.setOnAction(event -> filterStudentAccounts());
    }

    private void loadStudentAccounts(String selectedYear, String selectedMajor) {
        List<String> students;
        if (selectedYear == null && selectedMajor == null) {
            students = StudentDAO.getAllStudentsOrdered();
        } else {
            students = StudentDAO.getFilteredStudents(selectedYear, selectedMajor);
        }
        account.getItems().clear();
        account.getItems().addAll(students);
    }

    private void filterStudentAccounts() {
        String selectedYear = year.getValue();
        String selectedMajor = major.getValue();
        loadStudentAccounts(selectedYear, selectedMajor);
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        // Your delete logic here
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
