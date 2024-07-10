module com.example.demofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires com.opencsv;
    requires java.xml.bind;
    requires jbcrypt;


    opens com.example.demofx to javafx.fxml, com.google.gson;
    exports com.example.demofx;
    exports com.example.controllers;
    opens com.example.controllers to com.google.gson, javafx.fxml;
}