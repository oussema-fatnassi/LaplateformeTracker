package com.example.controllers;

import com.example.utility.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminExportImportController {
    // FXML fields
    @FXML
    private Button importExport;
    @FXML
    private Button back;
    @FXML
    private ComboBox<String> operation;
    @FXML
    private ComboBox<String> format;
    @FXML
    private ComboBox<String> type;
    // Initialize method to initialize the ComboBoxes with data
    public void initialize() {
        operation.getItems().addAll("Import", "Export");
        format.getItems().addAll("CSV", "JSON");

        operation.setOnAction(event -> handleOperationChange());
        type.getItems().addAll("Student List", "Student Grades");
    }
    // Handle back button action to return to the main menu
    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/admin-main-menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Handle import/export button action to import or export data
    @FXML
    private void handleImportExportButtonAction(ActionEvent event) {
        String selectedOperation = operation.getValue();
        String selectedFormat = format.getValue();
        String selectedType = type.getValue();
    // Validate selected operation, format, and type
        if (selectedOperation == null || selectedFormat == null || selectedType == null) {
            showAlert("Error", "Please select operation, format, and type.");
            return;
        }
        if (selectedOperation.equals("Export")) {
            exportData(selectedFormat, selectedType);
        } else if (selectedOperation.equals("Import")) {
            importData(selectedFormat, selectedType);
        }
    }
    // Handle operation change to update the type ComboBox
    private void handleOperationChange() {
        String selectedOperation = operation.getValue();
        type.getItems().clear();
    // Update the type ComboBox based on the selected operation
        if (selectedOperation.equals("Import")) {
            type.getItems().addAll("Student Accounts", "Student Grades");
        } else if (selectedOperation.equals("Export")) {
            type.getItems().addAll("Student List", "Student Grades", "Student Statistics");
        }
    }
    // Export data based on the selected format and type
    private void exportData(String format, String type) {
        if (type.equals("Student List")) {
            List<StudentAccount> students = StudentAccountDAO.getAllStudents();
            String filePath = ExportDataUtils.exportData(students, format, type);
            if (filePath != null) {
                showAlert("Export Successful", "Data exported to: " + filePath);
            } else {
                showAlert("Export Failed", "Failed to export student list.");
            }
        } else if (type.equals("Student Grades")) {
            List<StudentGrade> grades = GradeDAO.getAllStudentGrades();
            String filePath = ExportDataUtils.exportStudentGrades(grades, format);
            if (filePath != null) {
                showAlert("Export Successful", "Data exported to: " + filePath);
            } else {
                showAlert("Export Failed", "Failed to export student grades.");
            }
        } else {
            showAlert("Error", "Exporting " + type + " in " + format + " format is not supported.");
        }
    }
    // Import data based on the selected format and type
    private void importData(String format, String type) {
        if (format.equalsIgnoreCase("JSON")) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                List<String> errorMessages = new ArrayList<>();

                if (type.equals("Student Accounts")) {
                    errorMessages = ImportDataUtils.importStudentAccountsFromJSON(selectedFile);
                    if (errorMessages.isEmpty()) {
                        showAlert("Import Successful", "Student accounts imported successfully.");
                    } else {
                        showAlert("Import Errors", String.join("\n", errorMessages));
                    }
                } else if (type.equals("Student Grades")) {
                    errorMessages = ImportDataUtils.importStudentGradesFromJSON(selectedFile);
                    if (errorMessages.isEmpty()) {
                        showAlert("Import Successful", "Student grades imported successfully.");
                    } else {
                        showAlert("Import Errors", String.join("\n", errorMessages));
                    }
                } else {
                    showAlert("Error", "Importing " + type + " is not supported.");
                }
            }
        } else {
            showAlert("Error", "Importing in " + format + " format is not supported.");
        }
    }
    // Show alert dialog with the specified title and content
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}