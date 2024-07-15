package com.example.controllers;

import com.example.demo.StudentAccountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class AdminStatisticController {

    @FXML
    private Button back;
    @FXML
    private ComboBox<String> graph;
    @FXML
    private PieChart pieChart;

    public void initialize() {
        graph.getItems().addAll("Major", "Year");
        graph.getSelectionModel().selectFirst();
        graph.setOnAction(this::updatePieChart);

        updatePieChart(null);
    }

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

    private void updatePieChart(ActionEvent event) {
        String selectedGraph = graph.getValue();
        pieChart.getData().clear();

        Map<String, Integer> data;
        if ("Major".equals(selectedGraph)) {
            data = StudentAccountDAO.getStudentCountByMajor();
        } else {
            data = StudentAccountDAO.getStudentCountByYear();
        }

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            pieChart.getData().add(slice);
        }
    }
}
