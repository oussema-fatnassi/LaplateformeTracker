package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentMainMenuController {
    // FXML fields
    @FXML
    private Button changePassword;
    @FXML
    private Button showGrades;
    @FXML
    private Button statistics;
    @FXML
    private Button export;
    @FXML
    private Button logout;

    private int studentId;
    // Set studentId for the controller
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    // Handle button actions for showing grades
    @FXML
    private void handleShowGradesAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/student-show-grades.fxml"));
            Parent root = loader.load();
            // Pass the student ID to the StudentGradeController
            StudentGradeController controller = loader.getController();
            controller.setStudentId(studentId);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Student Grades");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for logging out
    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/landing-page.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for exporting grades
    @FXML
    private void handleStatisticsButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/student-statistic.fxml"));
            Parent root = loader.load();
            // Pass the student ID to the StudentStatisticController
            StudentStatisticController controller = loader.getController();
            controller.setStudentId(studentId);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Student Statistics");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for exporting grades
    @FXML
    private void handleChangePasswordButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/student-change-password.fxml"));
            Parent root = loader.load();
            // Pass the student ID to the StudentChangePasswordController
            StudentChangePasswordController controller = loader.getController();
            controller.setStudentId(studentId);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Change Password");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}