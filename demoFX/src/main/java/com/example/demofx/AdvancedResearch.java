package com.example.demofx;
import java.util.List;
import java.util.Scanner;

import static com.example.demofx.Menu.studentDAO;

public class AdvancedResearch {

    public static void searchByFirstName(String firstName, List<Student> students) {
        List<Student> studentsFound = students.stream()
                .filter(student -> student.getFirstName().equals(firstName))
                .toList();
        studentsFound.forEach(System.out::println);

        if(studentsFound.isEmpty()) {
            System.out.println("No students found with the first name: " + firstName);
        }
    }

    public static void searchByLastName(String lastName, List<Student> students) {
        List<Student> studentsFound = students.stream()
                .filter(student -> student.getLastName().equals(lastName))
                .toList();
        studentsFound.forEach(System.out::println);

        if(studentsFound.isEmpty()) {
            System.out.println("No students found with the last name: " + lastName);
        }
    }

    public static void searchByEmail(String email, List<Student> students) {
        List<Student> studentsFound = students.stream()
                .filter(student -> student.getEmail().equals(email))
                .toList();
        studentsFound.forEach(System.out::println);

        if(studentsFound.isEmpty()) {
            System.out.println("No students found with the email: " + email);
        }
    }

    public static void searchByAge(int age, List<Student> students) {
        List<Student> studentsFound = students.stream()
                .filter(student -> student.getAge() == age)
                .toList();
        studentsFound.forEach(System.out::println);

        if(studentsFound.isEmpty()) {
            System.out.println("No students found with the age: " + age);
        }
    }

    public static void searchByGrade(double grade, List<Student> students) {
        List<Student> studentsFound = students.stream()
                .filter(student -> student.getGrade() == grade)
                .toList();
        studentsFound.forEach(System.out::println);

        if(studentsFound.isEmpty()) {
            System.out.println("No students found with the grade: " + grade);
        }
    }

    public static void searchBySubject(String subject, List<Student> students) {
        List<Student> studentsFound = students.stream()
                .filter(student -> student.getSubject().equals(subject))
                .toList();
        studentsFound.forEach(System.out::println);

        if(studentsFound.isEmpty()) {
            System.out.println("No students found with the subject: " + subject);
        }
    }

    public static void advancedSearchMenu(Scanner scanner){
        System.out.println("********************************************");
        System.out.println("*          Advanced Search                  *");
        System.out.println("********************************************");
        System.out.println("1. Search by first name");
        System.out.println("2. Search by last name");
        System.out.println("3. Search by email");
        System.out.println("4. Search by age");
        System.out.println("5. Search by grade");
        System.out.println("6. Search by subject");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter first name: ");
                String firstName = scanner.next();
                AdvancedResearch.searchByFirstName(firstName, studentDAO.getAllStudents());
                break;
            case 2:
                System.out.print("Enter last name: ");
                String lastName = scanner.next();
                AdvancedResearch.searchByLastName(lastName, studentDAO.getAllStudents());
                break;
            case 3:
                System.out.print("Enter email: ");
                String email = scanner.next();
                AdvancedResearch.searchByEmail(email, studentDAO.getAllStudents());
                break;
            case 4:
                System.out.print("Enter age: ");
                int age = scanner.nextInt();
                AdvancedResearch.searchByAge(age, studentDAO.getAllStudents());
                break;
            case 5:
                System.out.print("Enter grade: ");
                double grade = scanner.nextDouble();
                AdvancedResearch.searchByGrade(grade, studentDAO.getAllStudents());
                break;
            case 6:
                System.out.print("Enter subject: ");
                String subject = scanner.next();
                AdvancedResearch.searchBySubject(subject, studentDAO.getAllStudents());
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
}
