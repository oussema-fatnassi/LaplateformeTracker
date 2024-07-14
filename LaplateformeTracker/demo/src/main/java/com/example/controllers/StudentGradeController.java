package com.example.controllers;

import com.example.demo.GradeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

public class StudentGradeController {

    @FXML
    private Button back;
    @FXML
    private ListView<String> gradeList;
    @FXML
    private ListView<String> subjectList;

    private int studentId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
        loadGrades();
    }

    private void loadGrades() {
        List<String> grades = GradeDAO.getGradesByStudentId(studentId);

        for (String grade : grades) {
            // Split the grade into subject and grade parts
            String[] parts = grade.split(":");
            String subject = parts[0];
            String gradeValue = parts[1];

            // Add subject to subjectList
            subjectList.getItems().add(subject);

            // Add grade to gradeList
            gradeList.getItems().add(gradeValue);
        }
    }

    @FXML
    private void initialize() {
        // Configure gradeList cell factory to display grades with alignment
        gradeList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            Text grade = new Text(item);
                            grade.setStyle("-fx-alignment: CENTER-RIGHT;");
                            setGraphic(grade);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        // Configure subjectList cell factory to display subjects
        subjectList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            Text subject = new Text(item);
                            HBox.setHgrow(subject, Priority.ALWAYS);
                            setGraphic(subject);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        gradeList.setFocusTraversable(false);
        gradeList.setSelectionModel(null);
        subjectList.setFocusTraversable(false);
        subjectList.setSelectionModel(null);

        // Optional: Set fixed cell size to ensure consistent height
        gradeList.setFixedCellSize(25); // Adjust height as needed
        subjectList.setFixedCellSize(25); // Adjust height as needed
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/student-main-menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
