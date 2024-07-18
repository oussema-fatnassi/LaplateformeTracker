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

public class AdminMainMenuController {
    // FXML fields
    @FXML
    private Button createStudentAccount;
    @FXML
    private Button deleteStudentAccount;
    @FXML
    private Button addGrades;
    @FXML
    private Button studentList;
    @FXML
    private Button sortStudent;
    @FXML
    private Button advancedSearch;
    @FXML
    private Button statistics;
    @FXML
    private Button importExportData;
    @FXML
    private Button Logout;
    // Handle button actions for creating student account
    @FXML
    private void handleStudentAccountCreationButtonAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-student-account-creation.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Admin Account Creation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for deleting student account
    @FXML
    private void handleStudentAccountDeleteButtonAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-delete-student.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Delete Student Account");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for adding grades
    @FXML
    private void handelAddGradesButtonAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-add-grade.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Add Grades");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for logging out
    @FXML
    private void handleLogoutButtonAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/landing-page.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for showing the student list
    @FXML
    private void handleStudentListButtonAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-student-list.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Student List");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for sorting students
    @FXML
    private void handleSortStudentButtonAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-sort-students.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Sort Student");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for advanced search
    @FXML
    private void handleAdvancedSearchButtonAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-advanced-search.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Advanced Search");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for statistics
    @FXML
    private void handleStatisticButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-statistic.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Statistics");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle button actions for import/export data
    @FXML
    private void handleImportExportButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-export-import.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Import/Export Data");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}