package com.example.controllers;

import com.example.demo.StudentAccount;
import com.example.demo.StudentAccountDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminAdvancedSearchController {

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
    private Button searchButton;
    @FXML
    private ComboBox<String> search;
    @FXML
    private TextField parameter;

    public void initialize() {
        search.getItems().addAll("First Name", "Last Name", "Email", "Major", "Year");

        // Add listener to the parameter TextField
        parameter.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleSearch();
            }
        });

        loadStudentData();
    }

    private void loadStudentData() {
        List<StudentAccount> students = StudentAccountDAO.getAllStudents();

        firstName.getItems().clear();
        lastName.getItems().clear();
        email.getItems().clear();
        major.getItems().clear();
        year.getItems().clear();

        for (StudentAccount student : students) {
            firstName.getItems().add(student.getFirstName());
            lastName.getItems().add(student.getLastName());
            email.getItems().add(student.getEmail());
            major.getItems().add(student.getMajor());
            year.getItems().add(String.valueOf(student.getYear()));
        }

        // Optionally set fixed cell size for consistent display
        firstName.setFixedCellSize(25);
        lastName.setFixedCellSize(25);
        email.setFixedCellSize(25);
        major.setFixedCellSize(25);
        year.setFixedCellSize(25);

        // Disable selection
        firstName.setFocusTraversable(false);
        firstName.setSelectionModel(null);
        lastName.setFocusTraversable(false);
        lastName.setSelectionModel(null);
        email.setFocusTraversable(false);
        email.setSelectionModel(null);
        major.setFocusTraversable(false);
        major.setSelectionModel(null);
        year.setFocusTraversable(false);
        year.setSelectionModel(null);
    }

    private void handleSearch() {
        String selectedParameter = search.getValue();
        String parameterValue = parameter.getText().trim();

        if (selectedParameter != null && !parameterValue.isEmpty()) {
            List<StudentAccount> filteredStudents = StudentAccountDAO.searchStudents(selectedParameter, parameterValue);
            updateListViewItems(filteredStudents);
        } else {
            loadStudentData();
        }
    }

    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        String selectedParameter = search.getValue();
        String parameterValue = parameter.getText().trim();

        if (selectedParameter == null) {
            showAlert("Error", "Please select a search parameter.");
        } else if (parameterValue.isEmpty()) {
            showAlert("Error", "Please enter a search value.");
        } else {
            handleSearch();
        }
    }

    private void updateListViewItems(List<StudentAccount> students) {
        firstName.getItems().clear();
        lastName.getItems().clear();
        email.getItems().clear();
        major.getItems().clear();
        year.getItems().clear();

        for (StudentAccount student : students) {
            firstName.getItems().add(student.getFirstName());
            lastName.getItems().add(student.getLastName());
            email.getItems().add(student.getEmail());
            major.getItems().add(student.getMajor());
            year.getItems().add(String.valueOf(student.getYear()));
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
