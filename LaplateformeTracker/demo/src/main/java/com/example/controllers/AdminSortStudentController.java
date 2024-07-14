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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AdminSortStudentController {

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
    private Button sort;
    @FXML
    private ComboBox<String> filter;
    @FXML
    private ComboBox<String> order;

    private ObservableList<StudentAccount> students = FXCollections.observableArrayList();

    public void initialize() {
        ObservableList<String> filterOptions = FXCollections.observableArrayList(
                "First Name", "Last Name", "Email", "Year", "Major"
        );
        filter.setItems(filterOptions);

        filter.setOnAction(event -> {
            String selectedFilter = filter.getValue();
            if (selectedFilter != null) {
                switch (selectedFilter) {
                    case "First Name":
                    case "Last Name":
                    case "Email":
                    case "Year":
                        order.setItems(FXCollections.observableArrayList(
                                "Ascending", "Descending"
                        ));
                        break;
                    case "Major":
                        ObservableList<String> majors = FXCollections.observableArrayList(
                                StudentAccountDAO.getAllMajors()
                        );
                        order.setItems(majors.sorted());
                        break;
                }
            }
        });

        loadStudentData();

        // Set up listeners for selection synchronization
        setupSelectionListeners();
    }

    private void loadStudentData() {
        List<StudentAccount> studentList = StudentAccountDAO.getAllStudents();
        students.setAll(studentList);

        updateListViewItems(students);
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

    @FXML
    private void handleSortButtonAction(ActionEvent event) {
        String selectedFilter = filter.getValue();
        String selectedOrder = order.getValue();
        if (selectedFilter != null && selectedOrder != null) {
            switch (selectedFilter) {
                case "First Name":
                    sortNamesByFirstName(selectedOrder);
                    break;
                case "Last Name":
                    sortNamesByLastName(selectedOrder);
                    break;
                case "Email":
                    sortNamesByEmail(selectedOrder);
                    break;
                case "Year":
                    sortNamesByYear(selectedOrder);
                    break;
                case "Major":
                    filterNamesByMajor(selectedOrder);
                    break;
            }
        }
    }

    private void sortNamesByFirstName(String order) {
        students.sort((s1, s2) -> order.equals("Ascending") ? s1.getFirstName().compareTo(s2.getFirstName()) : s2.getFirstName().compareTo(s1.getFirstName()));
        updateListViewItems(students);
    }

    private void sortNamesByLastName(String order) {
        students.sort((s1, s2) -> order.equals("Ascending") ? s1.getLastName().compareTo(s2.getLastName()) : s2.getLastName().compareTo(s1.getLastName()));
        updateListViewItems(students);
    }

    private void sortNamesByEmail(String order) {
        students.sort((s1, s2) -> order.equals("Ascending") ? s1.getEmail().compareTo(s2.getEmail()) : s2.getEmail().compareTo(s1.getEmail()));
        updateListViewItems(students);
    }

    private void sortNamesByYear(String order) {
        students.sort((s1, s2) -> order.equals("Ascending") ? s1.getYear().compareTo(s2.getYear()) : s2.getYear().compareTo(s1.getYear()));
        updateListViewItems(students);
    }

    private void filterNamesByMajor(String selectedMajor) {
        List<StudentAccount> filteredStudents = students.stream()
                .filter(student -> student.getMajor().equals(selectedMajor))
                .collect(Collectors.toList());
        updateListViewItems(filteredStudents);
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
}
