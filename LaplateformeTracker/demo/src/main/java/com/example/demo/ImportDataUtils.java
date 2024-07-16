package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImportDataUtils {

    public static List<String> importStudentAccountsFromJSON(File file) {
        List<String> errorMessages = new ArrayList<>();

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type studentListType = new TypeToken<List<StudentAccount>>() {}.getType();
            List<StudentAccount> students = gson.fromJson(reader, studentListType);

            for (StudentAccount student : students) {
                String validationMessage = getValidationMessage(student);
                if (validationMessage == null) {
                    if (!StudentAccountDAO.emailExists(student.getEmail())) {
                        boolean success = StudentAccountDAO.createStudent(
                                student.getFirstName(),
                                student.getLastName(),
                                student.getEmail(),
                                student.getAge(),
                                student.getPassword(),
                                student.getYear(),
                                student.getMajor()
                        );

                        if (!success) {
                            errorMessages.add("Failed to create student account for email: " + student.getEmail());
                        }
                    } else {
                        errorMessages.add("Email already exists: " + student.getEmail());
                    }
                } else {
                    errorMessages.add("Invalid student data for email: " + student.getEmail() + ". " + validationMessage);
                }
            }
        } catch (IOException e) {
            errorMessages.add("Failed to read the file: " + e.getMessage());
        }
        return errorMessages;
    }

    private static String getValidationMessage(StudentAccount student) {
        if (student.getFirstName() == null || student.getFirstName().isEmpty()) {
            return "Missing or empty first name.";
        }
        if (student.getLastName() == null || student.getLastName().isEmpty()) {
            return "Missing or empty last name.";
        }
        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            return "Missing or empty email.";
        }
        if (student.getAge() <= 0) {
            return "Invalid or missing age.";
        }
        if (student.getPassword() == null || student.getPassword().isEmpty()) {
            return "Missing or empty password.";
        }
        if (student.getYear() == null || student.getYear().isEmpty()) {
            return "Missing or empty year.";
        }
        if (student.getMajor() == null || student.getMajor().isEmpty()) {
            return "Missing or empty major.";
        }
        return null;
    }
}