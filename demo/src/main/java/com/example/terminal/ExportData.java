package com.example.terminal;

import com.example.utility.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ExportData {

    private static final String EXPORT_FOLDER = "export";
    // Export data as JSON format
    public static void exportDataJSON(List<Student> students) {
        createExportFolder();
        String filePath = EXPORT_FOLDER + "/students.json";
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(students, writer);
            System.out.println("Data exported to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Export data as CSV format
    public static void exportDataCSV(List<Student> students) {
        createExportFolder();
        String filePath = EXPORT_FOLDER + "/students.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"ID", "First Name", "Last Name", "Email", "Age", "Grade", "Subject"});
            for (Student student : students) {
                writer.writeNext(new String[]{
                        String.valueOf(student.getId()),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        String.valueOf(student.getAge()),
                        String.valueOf(student.getGrade()),
                        student.getSubject()
                });
            }
            System.out.println("Data exported to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Create export folder
    private static void createExportFolder() {
        File exportDir = new File(EXPORT_FOLDER);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
    }
    // Export data menu
    public static void exportDataMenu(List<Student> students, Scanner scanner) {
        System.out.println("********************************************");
        System.out.println("*            Export Data Format             *");
        System.out.println("********************************************");
        System.out.println("1. Export as JSON");
        System.out.println("2. Export as CSV");
        System.out.print("Enter your choice: ");
        int exportChoice = scanner.nextInt();

        switch (exportChoice) {
            case 1:
                exportDataJSON(students);
                break;
            case 2:
                exportDataCSV(students);
                break;
            default:
                System.out.println("Invalid export choice");
        }
    }
}