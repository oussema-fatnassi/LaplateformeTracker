package com.example.controllers;

import com.example.demo.StudentDAO;
import com.example.demo.GradeDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

public class AdminAddGradeController {

    @FXML
    private ComboBox<String> year;
    @FXML
    private ComboBox<String> major;
    @FXML
    private ComboBox<String> student;
    @FXML
    private ComboBox<String> subject;
    @FXML
    private TextField grade;
    @FXML
    private Button add;
    @FXML
    private Button back;

    @FXML
    private void initialize() {
        year.getItems().addAll("Bachelor1", "Bachelor2", "Bachelor3", "Master1", "Master2");
        major.getItems().addAll("Web Development", "Software Development", "Cybersecurity", "AI Development", "AR/VR Development");
        subject.getItems().addAll("HTML/CSS Programming", "JavaScript Programming", "Python Programming", "Java Programming", "C++ Programming", "Cybersecurity", "Data Science", "Machine Learning", "Artificial Intelligence", "Augmented Reality", "Virtual Reality", "3D verse", "Unity", "Unreal Engine 5", "English", "French", "Soft Skills");

        year.setOnAction(event -> filterStudents());
        major.setOnAction(event -> filterStudents());
        student.setOnMouseClicked(event -> loadAllStudentsIfNeeded());
    }

    private void filterStudents() {
        String selectedYear = year.getValue();
        String selectedMajor = major.getValue();

        List<String> students;
        if (selectedYear == null && selectedMajor == null) {
            students = StudentDAO.getAllStudentsOrdered();
        } else if (selectedYear != null && selectedMajor == null) {
            students = StudentDAO.getStudentsByYear(selectedYear);
        } else if (selectedYear == null && selectedMajor != null) {
            students = StudentDAO.getStudentsByMajor(selectedMajor);
        } else {
            students = StudentDAO.getFilteredStudents(selectedYear, selectedMajor);
        }

        student.getItems().clear();
        student.getItems().addAll(students);
    }

    private void loadAllStudentsIfNeeded() {
        if (year.getValue() == null && major.getValue() == null) {
            List<String> students = StudentDAO.getAllStudentsOrdered();
            student.getItems().clear();
            student.getItems().addAll(students);
        }
    }

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        String selectedStudent = student.getValue();
        String selectedSubject = subject.getValue();
        String gradeText = grade.getText();

        if (selectedStudent == null || selectedSubject == null || gradeText.isEmpty()) {
            showAlert("Invalid Input", "All fields must be filled.");
            return;
        }

        try {
            double gradeValue = Double.parseDouble(gradeText);
            if (!isValidGrade(gradeValue)) {
                showAlert("Invalid Input", "Grade must be a number between 1.0 and 100.0 with one decimal place.");
                return;
            }
            boolean success = GradeDAO.addGrade(selectedStudent, selectedSubject, gradeValue);
            if (success) {
                showAlert("Success", "Grade added successfully.");
                clearFields();
            } else {
                showAlert("Error", "Failed to add grade.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Grade must be a valid number.");
        }
    }

    private boolean isValidGrade(double grade) {
        return grade >= 1.0 && grade <= 100.0 && (Math.round(grade * 10.0) / 10.0 == grade);
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        year.setValue(null);
        major.setValue(null);
        student.setValue(null);
        subject.setValue(null);
        grade.clear();
    }
}
