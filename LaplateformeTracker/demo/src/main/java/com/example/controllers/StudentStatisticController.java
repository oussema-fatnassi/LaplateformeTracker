package com.example.controllers;

import com.example.demo.GradeDAO;
import com.example.demo.StudentGrade;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentStatisticController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private ComboBox<String> graph;

    private int studentId;

    public void initialize() {
        barChart.setVisible(false);
        pieChart.setVisible(false);
        lineChart.setVisible(false);
        populateGraphComboBox();
        graph.setOnAction(this::handleGraphSelection);
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
        loadStudentStatistics();
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

    private void populateGraphComboBox() {
        graph.setItems(FXCollections.observableArrayList("Bar Chart", "Pie Chart", "Line Chart"));
    }

    @FXML
    private void handleGraphSelection(ActionEvent event) {
        String selectedGraph = graph.getValue();
        barChart.setVisible(false);
        pieChart.setVisible(false);
        lineChart.setVisible(false);
        switch (selectedGraph) {
            case "Bar Chart":
                barChart.setVisible(true);
                break;
            case "Pie Chart":
                pieChart.setVisible(true);
                break;
            case "Line Chart":
                lineChart.setVisible(true);
                break;
        }
    }

    private void loadStudentStatistics() {
        List<StudentGrade> allGrades = GradeDAO.getAllStudentGrades();
        List<String> studentGrades = GradeDAO.getGradesByStudentId(studentId);

        populateBarChart(studentGrades);
        populatePieChart(allGrades);
        populateLineChart(allGrades);
    }

    private void populateBarChart(List<String> studentGrades) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String grade : studentGrades) {
            String[] parts = grade.split(": ");
            series.getData().add(new XYChart.Data<>(parts[0], Double.parseDouble(parts[1])));
        }
        barChart.getData().add(series);
    }

    private void populatePieChart(List<StudentGrade> allGrades) {
        int totalSubjects = 0;
        int passedSubjects = 0;
        for (StudentGrade sg : allGrades) {
            totalSubjects++;
            if (sg.getGrade() >= 50) {
                passedSubjects++;
            }
        }
        PieChart.Data passData = new PieChart.Data("Passed", passedSubjects);
        PieChart.Data failData = new PieChart.Data("Failed", totalSubjects - passedSubjects);
        pieChart.setData(FXCollections.observableArrayList(passData, failData));
    }

    private void populateLineChart(List<StudentGrade> allGrades) {
        Map<String, Double> subjectAverages = new HashMap<>();
        Map<String, Integer> subjectCounts = new HashMap<>();

        for (StudentGrade sg : allGrades) {
            String subject = sg.getSubject();
            subjectAverages.put(subject, subjectAverages.getOrDefault(subject, 0.0) + sg.getGrade());
            subjectCounts.put(subject, subjectCounts.getOrDefault(subject, 0) + 1);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String subject : subjectAverages.keySet()) {
            double average = subjectAverages.get(subject) / subjectCounts.get(subject);
            series.getData().add(new XYChart.Data<>(subject, average));
        }
        lineChart.getData().add(series);
    }
}
