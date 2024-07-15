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

    public static void exportData(List<StudentAccount> students, String format, String type) {
        switch (format.toUpperCase()) {
            case "CSV":
                exportDataCSV(students, type);
                break;
            case "JSON":
                exportDataJSON(students, type);
                break;
            case "XML":
                exportDataXML(students, type);
                break;
            default:
                System.out.println("Unsupported format: " + format);
        }
    }

    private static void exportDataCSV(List<StudentAccount> students, String type) {
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
                    break;
                // Add cases for other types if needed (grades, statistics)
                default:
                    System.out.println("Unsupported type: " + type);
                    return;
            }
            System.out.println("Data exported to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exportDataJSON(List<StudentAccount> students, String type) {
        createExportFolder();
        String filePath = EXPORT_FOLDER + "/students_" + type.toLowerCase() + ".json";
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(students, writer);
            System.out.println("Data exported to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exportDataXML(List<StudentAccount> students, String type) {
        createExportFolder();
        String filePath = EXPORT_FOLDER + "/students_" + type.toLowerCase() + ".xml";
        try {
            JAXBContext context = JAXBContext.newInstance(StudentAccountListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StudentAccountListWrapper wrapper = new StudentAccountListWrapper();
            wrapper.setStudents(students);
            marshaller.marshal(wrapper, new File(filePath));
            System.out.println("Data exported to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createExportFolder() {
        File exportDir = new File(EXPORT_FOLDER);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
    }
}
