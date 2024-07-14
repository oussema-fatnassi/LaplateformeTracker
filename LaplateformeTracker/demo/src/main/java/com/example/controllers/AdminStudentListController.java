package com.example.controllers;

import com.example.demo.StudentAccountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminStudentListController {

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

    public void initialize() {
        System.out.println("Initializing AdminStudentListController...");

        // Load student data
        loadStudentData();
    }

    private void loadStudentData() {
        System.out.println("Loading student data...");

        List<String> firstNames = StudentAccountDAO.getStudentFirstName();
        List<String> lastNames = StudentAccountDAO.getStudentLastName();
        List<String> emails = StudentAccountDAO.getStudentEmail();
        List<String> majors = StudentAccountDAO.getStudentMajor();
        List<String> years = StudentAccountDAO.getStudentYear();

        // Add data to list views
        firstName.getItems().addAll(firstNames);
        lastName.getItems().addAll(lastNames);
        email.getItems().addAll(emails);
        major.getItems().addAll(majors);
        year.getItems().addAll(years);

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

    @FXML
    private void handleBackButtonAction(ActionEvent event){
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

}
