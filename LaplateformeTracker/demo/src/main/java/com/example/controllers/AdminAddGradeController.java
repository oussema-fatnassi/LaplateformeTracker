package com.example.controllers;

import com.example.demo.StudentAccount;
import com.example.demo.StudentAccountDAO;
import com.example.demo.GradeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

public class AdminAddGradeController {

    @FXML
    private TextField grade;
    @FXML
    private ListView<String> firstName;
    @FXML
    private ListView<String> lastName;
    @FXML
    private ListView<String> email;
    @FXML
    private ListView<String> major;
    @FXML
    private ListView<String> year;
    @FXML
    private Button back;
    @FXML
    private Button gradeButton;
    @FXML
    private ComboBox<String> subject;

    private ObservableList<StudentAccount> students = FXCollections.observableArrayList();

    public void initialize() {
        loadStudentData();
        setupSelectionListeners();

        subject.getItems().addAll("HTML/CSS Programming", "JavaScript Programming", "Python Programming", "Java Programming", "C++ Programming", "Cybersecurity", "Data Science", "Machine Learning", "Artificial Intelligence", "Augmented Reality", "Virtual Reality", "3D verse", "Unity", "Unreal Engine 5", "English", "French", "Soft Skills");

        gradeButton.setOnAction(this::handleAddGradeButtonAction);
    }

    private void loadStudentData() {
        List<StudentAccount> studentList = StudentAccountDAO.getAllStudents();
        students.setAll(studentList);

        updateListViewItems(students);
    }

    private void updateListViewItems(List<StudentAccount> studentList) {
        ObservableList<String> firstNames = FXCollections.observableArrayList();
        ObservableList<String> lastNames = FXCollections.observableArrayList();
        ObservableList<String> emails = FXCollections.observableArrayList();
        ObservableList<String> majors = FXCollections.observableArrayList();
        ObservableList<String> years = FXCollections.observableArrayList();

        for (StudentAccount student : studentList) {
            firstNames.add(student.getFirstName());
            lastNames.add(student.getLastName());
            emails.add(student.getEmail());
            majors.add(student.getMajor());
            years.add(student.getYear());
        }

        firstName.setItems(firstNames);
        lastName.setItems(lastNames);
        email.setItems(emails);
        major.setItems(majors);
        year.setItems(years);

        // Synchronize the selection after updating items
        synchronizeSelections();
    }

    private void setupSelectionListeners() {
        firstName.setOnMouseClicked(this::synchronizeSelection);
        lastName.setOnMouseClicked(this::synchronizeSelection);
        email.setOnMouseClicked(this::synchronizeSelection);
        major.setOnMouseClicked(this::synchronizeSelection);
        year.setOnMouseClicked(this::synchronizeSelection);
    }

    private void synchronizeSelection(MouseEvent event) {
        ListView<String> source = (ListView<String>) event.getSource();
        int index = source.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            firstName.getSelectionModel().select(index);
            lastName.getSelectionModel().select(index);
            email.getSelectionModel().select(index);
            major.getSelectionModel().select(index);
            year.getSelectionModel().select(index);
        }
    }

    private void synchronizeSelections() {
        // Get the selected index in any of the list views
        int selectedIndex = firstName.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            firstName.getSelectionModel().select(selectedIndex);
            lastName.getSelectionModel().select(selectedIndex);
            email.getSelectionModel().select(selectedIndex);
            major.getSelectionModel().select(selectedIndex);
            year.getSelectionModel().select(selectedIndex);
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

    public void handleAddGradeButtonAction(ActionEvent event) {
        int selectedIndex = firstName.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String studentFullName = firstName.getItems().get(selectedIndex) + " " + lastName.getItems().get(selectedIndex);
            String selectedSubject = subject.getValue();
            try {
                double gradeValue = Double.parseDouble(grade.getText());
                if (selectedSubject != null) {
                    // Show confirmation dialog
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirm Grade Addition");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to give " + studentFullName + " the grade " + gradeValue + " in the subject " + selectedSubject + "?");

                    // Wait for user confirmation
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            if (GradeDAO.addGrade(studentFullName, selectedSubject, gradeValue)) {
                                showAlert("Success", "Grade added successfully.");
                            } else {
                                showAlert("Error", "Failed to add grade. Please check the inputs.");
                            }
                        }
                    });
                } else {
                    showAlert("Error", "Please select a subject.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid grade value. Please enter a number.");
            }
        } else {
            showAlert("Error", "Please select a student.");
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
