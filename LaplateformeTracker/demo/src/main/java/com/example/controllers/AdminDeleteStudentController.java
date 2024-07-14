package com.example.controllers;

import com.example.demo.StudentAccount;
import com.example.demo.StudentAccountDAO;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminDeleteStudentController {


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

    private ObservableList<StudentAccount> students = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadStudentData();
        setupSelectionListeners();
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


    public void handleDeleteButtonAction(ActionEvent event) {
        int selectedIndex = firstName.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            StudentAccount selectedStudent = students.get(selectedIndex);
            StudentAccountDAO.deleteStudent(selectedStudent.getId());
            students.remove(selectedIndex);
            updateListViewItems(students);
        }

    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/main-menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Login");
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
