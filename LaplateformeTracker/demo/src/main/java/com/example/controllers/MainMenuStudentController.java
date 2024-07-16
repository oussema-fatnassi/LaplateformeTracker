package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuStudentController {

    @FXML
    private Button showGrades;
    @FXML
    private Button statistics;
    @FXML
    private Button export;
    @FXML
    private Button logout;

    private int studentId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @FXML
    private void handleShowGradesAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/student-show-grades.fxml"));
            Parent root = loader.load();

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


    @FXML
    private void handleStatisticsButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/student-statistic.fxml"));
            Parent root = loader.load();

            StudentStatisticController controller = loader.getController();
            controller.setStudentId(studentId); // Pass studentId to StudentStatisticController

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Student Statistics");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
