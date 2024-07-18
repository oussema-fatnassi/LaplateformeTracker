package com.example.terminal;

import com.example.utility.Student;

import java.util.Scanner;
// Statistic class with methods to calculate average age, average grade, count students, and count students by subject
public class Statistic {
    // Calculate the average age of students
    public static double calculateAverageAge() {
        return Menu.studentDAO.getAllStudents().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }
    // Calculate the average grade of students
    public static double calculateAverageGrade() {
        return Menu.studentDAO.getAllStudents().stream()
                .mapToDouble(Student::getGrade)
                .average()
                .orElse(0);
    }
    // Count the total number of students
    public static int countStudents() {
        return Menu.studentDAO.getAllStudents().size();
    }
    // Count the number of students by subject
    public static int countStudentsBySubject(String subject) {
        subject = subject.toLowerCase();
        String finalSubject = subject;
        return (int) Menu.studentDAO.getAllStudents().stream()
                .filter(student -> student.getSubject().equals(finalSubject))
                .count();
    }
    // Statistic menu to display the statistic information
    public static void statisticsMenu(Scanner scanner){

        System.out.println("********************************************");
        System.out.println("*          Statistic Information          *");
        System.out.println("********************************************");
        System.out.println("1. Average age of students");
        System.out.println("2. Average grade of students");
        System.out.println("3. Total number of students");
        System.out.println("4. Number of students by subject");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();

        switch(choice){
            case 1:
                System.out.println("Average age of students: " + calculateAverageAge());
                break;
            case 2:
                System.out.println("Average grade of students: " + calculateAverageGrade());
                break;
            case 3:
                System.out.println("Total number of students: " + countStudents());
                break;
            case 4:
                System.out.print("Enter the subject: ");
                String subject = scanner.next();
                System.out.println("Number of students by subject: " + countStudentsBySubject(subject));
                break;
        }
    }
}