package com.example.controllers;

import com.example.demo.ExportDataUtils;
import com.example.demo.GradeDAO;
import com.example.demo.StudentGrade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button export;
    @FXML
    private ListView<String> gradeList;
    @FXML
    private ListView<String> subjectList;
    @FXML
    private ComboBox<String> format;

    private int studentId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
        loadGrades();
    }

    private void loadGrades() {
        System.out.println("Loading grades for studentId: " + studentId);

        // Clear existing items for debugging purposes
        gradeList.getItems().clear();
        subjectList.getItems().clear();

        List<String> grades = GradeDAO.getGradesByStudentId(studentId);
        System.out.println("Retrieved grades: " + grades);

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
        format.getItems().addAll("JSON", "CSV");

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/student-main-menu.fxml"));
            Parent root = loader.load();

            // Pass the student ID back to the main menu
            StudentMainMenuController controller = loader.getController();
            controller.setStudentId(studentId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleExportButtonAction(ActionEvent event) {
        String selectedFormat = format.getValue();
        if (selectedFormat == null) {
            showAlert(Alert.AlertType.WARNING, "No Format Selected", "Please select a format to export.");
            return;
        }
        List<StudentGrade> grades = GradeDAO.getGradesByStudentIdList(studentId);
        String filePath = ExportDataUtils.exportStudentGrades(grades, selectedFormat);
        if (filePath != null) {
            showAlert(Alert.AlertType.INFORMATION, "Export Successful", "Grades exported to: " + filePath);
        } else {
            showAlert(Alert.AlertType.ERROR, "Export Failed", "An error occurred during the export.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
