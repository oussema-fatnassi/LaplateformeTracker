package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportDataUtils {

    private static final String EXPORT_FOLDER = "export";

    public static String exportData(List<StudentAccount> students, String format, String type) {
        switch (format.toUpperCase()) {
            case "CSV":
                return exportDataCSV(students, type);
            case "JSON":
                return exportDataJSON(students, type);
            case "XML":
                return exportDataXML(students, type);
            default:
                System.out.println("Unsupported format: " + format);
                return null;
        }
    }

    public static String exportStudentGrades(List<StudentGrade> grades, String format) {
        switch (format.toUpperCase()) {
            case "CSV":
                return exportStudentGradesCSV(grades);
            case "JSON":
                return exportStudentGradesJSON(grades);
            case "XML":
                return exportStudentGradesXML(grades);
            default:
                System.out.println("Unsupported format: " + format);
                return null;
        }
    }

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
                        grade.getGrade()
                });
            }
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    private static String exportDataXML(List<StudentAccount> students, String type) {
        createExportFolder();
        String filePath = EXPORT_FOLDER + "/students_" + type.toLowerCase() + ".xml";
        try {
            JAXBContext context = JAXBContext.newInstance(StudentAccountListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StudentAccountListWrapper wrapper = new StudentAccountListWrapper();
            wrapper.setStudents(students);
            marshaller.marshal(wrapper, new File(filePath));
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String exportStudentGradesXML(List<StudentGrade> grades) {
        createExportFolder();
        String filePath = EXPORT_FOLDER + "/student_grades.xml";
        try {
            JAXBContext context = JAXBContext.newInstance(StudentGradeListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StudentGradeListWrapper wrapper = new StudentGradeListWrapper();
            wrapper.setGrades(grades);
            marshaller.marshal(wrapper, new File(filePath));
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void createExportFolder() {
        File exportDir = new File(EXPORT_FOLDER);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
    }
}
