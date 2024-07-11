module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires java.xml.bind;
    requires com.opencsv;
    requires jbcrypt;


    opens com.example.demo to javafx.fxml, com.google.gson;
    exports com.example.demo;
    exports com.example.controllers;
    opens com.example.controllers to com.google.gson, javafx.fxml;
}