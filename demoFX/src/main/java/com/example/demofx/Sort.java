package com.example.demofx;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.example.demofx.Menu.studentDAO;


public class Sort {

    // Sorts the list of students by first name
    public static void sortByFirstName(List<Student> students) {
        students.sort(Comparator.comparing(Student::getFirstName));
    }

    // Sorts the list of students by last name
    public static void sortByLastName(List<Student> students) {
        students.sort(Comparator.comparing(Student::getLastName));
    }

    // Sorts the list of students by email
    public static void sortByEmail(List<Student> students) {
        students.sort(Comparator.comparing(Student::getEmail));
    }

    // Sorts the list of students by age
    public static void sortByAge(List<Student> students) {
        students.sort(Comparator.comparing(Student::getAge));
    }

    // Sorts the list of students by grade
    public static void sortByGrade(List<Student> students) {
        students.sort(Comparator.comparing(Student::getGrade));
    }

    // Sorts the list of students by subject
    public static void sortBySubject(List<Student> students) {
        students.sort(Comparator.comparing(Student::getSubject));
    }

    // Displays the sort students menu
    public static void sortStudentsMenu(Scanner scanner) {
        try {
            System.out.println("********************************************");
            System.out.println("*          Sorted Students Information     *");
            System.out.println("********************************************");
            System.out.println("1. Sort by first name");
            System.out.println("2. Sort by last name");
            System.out.println("3. Sort by email");
            System.out.println("4. Sort by age");
            System.out.println("5. Sort by grade");
            System.out.println("6. Sort by subject");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            List<Student> students = studentDAO.getAllStudents();
            switch (choice) {
                case 1:
                    Sort.sortByFirstName(students);
                    break;
                case 2:
                    Sort.sortByLastName(students);
                    break;
                case 3:
                    Sort.sortByEmail(students);
                    break;
                case 4:
                    Sort.sortByAge(students);
                    break;
                case 5:
                    Sort.sortByGrade(students);
                    break;
                case 6:
                    Sort.sortBySubject(students);
                    break;
                default:
                    System.out.println("Invalid choice");
                    return;
            }
            students.forEach(System.out::println);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer choice.");
            scanner.nextLine();
        }
    }
}
