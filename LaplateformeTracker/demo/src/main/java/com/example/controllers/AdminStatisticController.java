package com.example.controllers;

import com.example.demo.StudentAccountDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Map;

public class AdminStatisticController {
    // FXML fields
    @FXML
    private Button back;
    @FXML
    private ComboBox<String> graph;
    @FXML
    private PieChart pieChart;
    @FXML
    private CategoryAxis barXAxis;
    @FXML
    private NumberAxis barYAxis;
    // Initialize the ComboBox and the PieChart
    public void initialize() {
        graph.getItems().addAll("Major", "Year", "Age Distribution");
        graph.getSelectionModel().selectFirst();
        graph.setOnAction(this::updateChart);
        updateChart(null);
    }
    // Handle button actions for back button
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
    // Update the chart based on the selected graph
    private void updateChart(ActionEvent event) {
        String selectedGraph = graph.getValue();
        pieChart.setVisible(false);
        // Hide the bar chart if it is visible
        if ("Major".equals(selectedGraph)) {
            pieChart.setVisible(true);
            updatePieChart(StudentAccountDAO.getStudentCountByMajor());
        } else if ("Year".equals(selectedGraph)) {
            pieChart.setVisible(true);
            updatePieChart(StudentAccountDAO.getStudentCountByYear());
        } else if ("Age Distribution".equals(selectedGraph)) {
            pieChart.setVisible(true);
            updatePieChart(StudentAccountDAO.getStudentAgeDistribution());
        }
    }
    // Update the pie chart based on the data
    private void updatePieChart(Map<String, Integer> data) {
        pieChart.getData().clear();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey() + " (" + entry.getValue() + ")", entry.getValue());
            pieChart.getData().add(slice);
        }
    }
}