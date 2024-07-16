package com.example.controllers;

import com.example.demo.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class AdminExportImportController {

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

    public void initialize() {
        operation.getItems().addAll("Import", "Export");
        format.getItems().addAll("CSV", "JSON", "XML");

        operation.setOnAction(event -> handleOperationChange());
        type.getItems().addAll("Student List", "Student Grades", "Student Statistics");
    }

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

    @FXML
    private void handleImportExportButtonAction(ActionEvent event) {
        String selectedOperation = operation.getValue();
        String selectedFormat = format.getValue();
        String selectedType = type.getValue();

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

    private void handleOperationChange() {
        String selectedOperation = operation.getValue();
        type.getItems().clear();

        if (selectedOperation.equals("Import")) {
            type.getItems().addAll("Student Accounts", "Student Grades");
        } else if (selectedOperation.equals("Export")) {
            type.getItems().addAll("Student List", "Student Grades", "Student Statistics");
        }
    }

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

    private void importData(String format, String type) {
        if (type.equals("Student Accounts") && format.equalsIgnoreCase("JSON")) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                List<String> errorMessages = ImportDataUtils.importStudentAccountsFromJSON(selectedFile);
                if (errorMessages.isEmpty()) {
                    showAlert("Import Successful", "Student accounts imported successfully.");
                } else {
                    StringBuilder errorMessageBuilder = new StringBuilder();
                    for (String errorMessage : errorMessages) {
                        errorMessageBuilder.append(errorMessage).append("\n");
                    }
                    showAlert("Import Errors", errorMessageBuilder.toString());
                }
            }
        } else {
            showAlert("Error", "Importing " + type + " in " + format + " format is not supported.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
