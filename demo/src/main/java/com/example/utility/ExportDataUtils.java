package com.example.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportDataUtils {

    private static final String EXPORT_FOLDER = "export";
    // Export data as JSON format
    public static String exportData(List<StudentAccount> students, String format, String type) {
        switch (format.toUpperCase()) {
            case "CSV":
                return exportDataCSV(students, type);
            case "JSON":
                return exportDataJSON(students, type);
            default:
                System.out.println("Unsupported format: " + format);
                return null;
        }
    }
    // Export student grades as JSON or CSV format
    public static String exportStudentGrades(List<StudentGrade> grades, String format) {
        switch (format.toUpperCase()) {
            case "CSV":
                return exportStudentGradesCSV(grades);
            case "JSON":
                return exportStudentGradesJSON(grades);
            default:
                System.out.println("Unsupported format: " + format);
                return null;
        }
    }
    // Export data as CSV format
    private static String exportDataCSV(List<StudentAccount> students, String type) {
        createExportFolder();
        String filePath = EXPORT_FOLDER + "/students_" + type.toLowerCase() + ".csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            switch (type.toUpperCase()) {
                case "STUDENT LIST":
                    writer.writeNext(new String[]{"First Name", "Last Name", "Email", "Major", "Year"});
                    for (StudentAccount student : students) {
                        writer.writeNext(new String[]{
                                student.getFirstName(),
                                student.getLastName(),
                                student.getEmail(),
                                student.getMajor(),
                                String.valueOf(student.getYear())
                        });
                    }
                    return filePath;
                default:
                    System.out.println("Unsupported type: " + type);
                    return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Export student grades as CSV format
    private static String exportStudentGradesCSV(List<StudentGrade> grades) {
        createExportFolder();
        String filePath = EXPORT_FOLDER + "/student_grades.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"First Name", "Last Name", "Subject", "Grade"});
            for (StudentGrade grade : grades) {
                writer.writeNext(new String[]{
                        grade.getFirstName(),
                        grade.getLastName(),
                        grade.getSubject(),
                        String.valueOf(grade.getGrade())
                });
            }
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Export data as JSON format
    private static String exportDataJSON(List<StudentAccount> students, String type) {
        createExportFolder();
        String filePath = EXPORT_FOLDER + "/students_" + type.toLowerCase() + ".json";
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(students, writer);
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Export student grades as JSON format
    private static String exportStudentGradesJSON(List<StudentGrade> grades) {
        createExportFolder();
        String filePath = EXPORT_FOLDER + "/student_grades.json";
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            gson.toJson(grades, writer);
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Create export folder
    private static void createExportFolder() {
        File exportDir = new File(EXPORT_FOLDER);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
    }
}