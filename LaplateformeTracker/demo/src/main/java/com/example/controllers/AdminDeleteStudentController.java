package com.example.controllers;

import com.example.utility.StudentAccount;
import com.example.utility.StudentAccountDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminDeleteStudentController {
    // FXML fields
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
    private Button delete;
    @FXML
    private Button back;
    // Observable list to hold student accounts and synchronize selection
    private ObservableList<StudentAccount> students = FXCollections.observableArrayList();
    // Initialize method to set up data and event handlers
    @FXML
    public void initialize() {
        loadStudentData();
        setupSelectionListeners();
    }
    // Load all student data from the database
    private void loadStudentData() {
        List<StudentAccount> studentList = StudentAccountDAO.getAllStudents();
        students.setAll(studentList);
        updateListViewItems(students);
    }
    // Update list view items with student data
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
    // Set up selection listeners for each list view to synchronize selection
    private void setupSelectionListeners() {
        firstName.setOnMouseClicked(this::synchronizeSelection);
        lastName.setOnMouseClicked(this::synchronizeSelection);
        email.setOnMouseClicked(this::synchronizeSelection);
        major.setOnMouseClicked(this::synchronizeSelection);
        year.setOnMouseClicked(this::synchronizeSelection);
    }
    // Synchronize the selection across all list views
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
    // Synchronize the selection across all list views
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
    // Handle delete button action to delete student account
    @FXML
    public void handleDeleteButtonAction(ActionEvent event) {
        int selectedIndex = firstName.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            StudentAccount selectedStudent = students.get(selectedIndex);
            String studentFullName = selectedStudent.getFirstName() + " " + selectedStudent.getLastName();
            // Show confirmation dialog
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Delete");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete the account of " + studentFullName + "?");
            // Wait for user confirmation
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    StudentAccountDAO.deleteStudent(selectedStudent.getId());
                    students.remove(selectedIndex);
                    updateListViewItems(students);
                    showAlert("Success", "Student account deleted successfully.");
                }
            });
        } else {
            showAlert("Error", "Please select a student.");
        }
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
    // Show alert dialog with specified title and content
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}