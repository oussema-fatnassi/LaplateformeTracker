package source;

import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

public class Menu {

    static final StudentDAO studentDAO = new StudentDAO();

    public void displayMenu() {
        System.out.println("********************************************");
        System.out.println("*         Student Management System         *");
        System.out.println("********************************************");
        System.out.println("1. Add student");
        System.out.println("2. Update student");
        System.out.println("3. Delete student");
        System.out.println("4. Search student by ID");
        System.out.println("5. Display all students");
        System.out.println("6. Sort students");
        System.out.println("7. Advanced Search");
        System.out.println("8. Display statistics");
        System.out.println("10. Exit");
        System.out.println("********************************************");
        System.out.print("Enter your choice: ");
    }

    public int getUserChoice(Scanner scanner) {
        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer choice.");
            scanner.nextLine();
        }
        return choice;
    }

    public void handleChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                addStudent(scanner);
                break;
            case 2:
                updateStudent(scanner);
                break;
            case 3:
                deleteStudent(scanner);
                break;
            case 4:
                searchStudent(scanner);
                break;
            case 5:
                displayAllStudents();
                break;
            case 6:
                Sort.sortStudentsMenu(scanner);
                break;
            case 7:
                AdvancedResearch.advancedSearchMenu(scanner);
                break;
            case 8:
                Statistics.statisticsMenu(scanner);
                break;
            case 10:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void addStudent(Scanner scanner) {
        try {
            System.out.println("\n********************************************");
            System.out.println("*             Add New Student               *");
            System.out.println("********************************************");
            System.out.print("Enter first name: ");
            String firstName = scanner.next();
            System.out.print("Enter last name: ");
            String lastName = scanner.next();
            System.out.print("Enter email: ");
            String email = scanner.next();
            System.out.print("Enter age: ");
            int age = scanner.nextInt();
            System.out.print("Enter grade: ");
            double grade = scanner.nextDouble();
            System.out.print("Enter subject: ");
            String subject = scanner.next();
            studentDAO.addStudent(firstName, lastName, email, age, grade, subject);
            System.out.println("Student added successfully");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer for age and a valid decimal for grade.");
            scanner.nextLine();
        }
    }

    private void updateStudent(Scanner scanner) {
        try {
            System.out.println("\n********************************************");
            System.out.println("*           Update Student Details          *");
            System.out.println("********************************************");
            System.out.print("Enter student id: ");
            int id = scanner.nextInt();
            System.out.print("Enter first name: ");
            String firstName = scanner.next();
            System.out.print("Enter last name: ");
            String lastName = scanner.next();
            System.out.print("Enter email: ");
            String email = scanner.next();
            System.out.print("Enter age: ");
            int age = scanner.nextInt();
            System.out.print("Enter grade: ");
            double grade = scanner.nextDouble();
            System.out.print("Enter subject: ");
            String subject = scanner.next();
            studentDAO.updateStudent(id, firstName, lastName, email, age, grade, subject);
            System.out.println("Student updated successfully");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer for id, age, and a valid decimal for grade.");
            scanner.nextLine();
        }
    }

    private void deleteStudent(Scanner scanner) {
        try {
            System.out.println("\n********************************************");
            System.out.println("*            Delete Student Record          *");
            System.out.println("********************************************");
            System.out.print("Enter student id: ");
            int id = scanner.nextInt();
            studentDAO.deleteStudent(id);
            System.out.println("Student deleted successfully");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer for id.");
            scanner.nextLine();
        }
    }

    private void displayAllStudents() {
        System.out.println("********************************************");
        System.out.println("*          All Students Information         *");
        System.out.println("********************************************");
        List<Student> students = studentDAO.getAllStudents();
        students.forEach(System.out::println);
    }

    private void searchStudent(Scanner scanner) {
        try {
            System.out.println("\n********************************************");
            System.out.println("*           Search Student Details          *");
            System.out.println("********************************************");
            System.out.print("Enter student id: ");
            int id = scanner.nextInt();
            Student student = studentDAO.getStudentById(id);
            System.out.println(student);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer for id.");
            scanner.nextLine();
        }
    }
}