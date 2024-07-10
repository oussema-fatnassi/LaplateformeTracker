package source;

import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

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
        System.out.println("9. Export data (CSV, XML, JSON)");
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
            scanner.nextLine(); // clear the invalid input
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
            case 9:
                exportData(scanner);
                break;
            case 10:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 10.");
        }
    }

    private void addStudent(Scanner scanner) {
        String firstName, lastName, email, subject;
        int age = 0;
        double grade = 0.0;

        System.out.println("\n********************************************");
        System.out.println("*             Add New Student               *");
        System.out.println("********************************************");

        firstName = getValidatedInput(scanner, "^[a-zA-Z]+$", "Invalid input. First name should contain only letters.", "Enter first name: ");
        lastName = getValidatedInput(scanner, "^[a-zA-Z]+$", "Invalid input. Last name should contain only letters.", "Enter last name: ");
        email = getValidatedInput(scanner, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "Invalid input. Please enter a valid email address.", "Enter email: ");

        while (true) {
            try {
                System.out.print("Enter age: ");
                age = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for age.");
                scanner.nextLine(); // clear the invalid input
            }
        }

        while (true) {
            try {
                System.out.print("Enter grade: ");
                grade = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid decimal for grade.");
                scanner.nextLine(); // clear the invalid input
            }
        }

        System.out.print("Enter subject: ");
        subject = scanner.next();

        studentDAO.addStudent(firstName, lastName, email, age, grade, subject);
        System.out.println("Student added successfully.");
    }

    private void updateStudent(Scanner scanner) {
        int id = 0, age = 0;
        double grade = 0.0;
        String firstName, lastName, email, subject;

        System.out.println("\n********************************************");
        System.out.println("*           Update Student Details          *");
        System.out.println("********************************************");

        while (true) {
            try {
                System.out.print("Enter student id: ");
                id = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for id.");
                scanner.nextLine(); // clear the invalid input
            }
        }

        firstName = getValidatedInput(scanner, "^[a-zA-Z]+$", "Invalid input. First name should contain only letters.", "Enter first name: ");
        lastName = getValidatedInput(scanner, "^[a-zA-Z]+$", "Invalid input. Last name should contain only letters.", "Enter last name: ");
        email = getValidatedInput(scanner, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "Invalid input. Please enter a valid email address.", "Enter email: ");

        while (true) {
            try {
                System.out.print("Enter age: ");
                age = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for age.");
                scanner.nextLine(); // clear the invalid input
            }
        }

        while (true) {
            try {
                System.out.print("Enter grade: ");
                grade = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid decimal for grade.");
                scanner.nextLine(); // clear the invalid input
            }
        }

        System.out.print("Enter subject: ");
        subject = scanner.next();

        studentDAO.updateStudent(id, firstName, lastName, email, age, grade, subject);
        System.out.println("Student updated successfully.");
    }

    private void deleteStudent(Scanner scanner) {
        int id = 0;

        System.out.println("\n********************************************");
        System.out.println("*            Delete Student Record          *");
        System.out.println("********************************************");

        while (true) {
            try {
                System.out.print("Enter student id: ");
                id = scanner.nextInt();
                studentDAO.deleteStudent(id);
                System.out.println("Student deleted successfully.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for id.");
                scanner.nextLine(); // clear the invalid input
            }
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
        int id = 0;

        System.out.println("\n********************************************");
        System.out.println("*           Search Student Details          *");
        System.out.println("********************************************");

        while (true) {
            try {
                System.out.print("Enter student id: ");
                id = scanner.nextInt();
                Student student = studentDAO.getStudentById(id);
                System.out.println(student);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for id.");
                scanner.nextLine(); // clear the invalid input
            }
        }
    }

    private void exportData(Scanner scanner) {
        List<Student> students = studentDAO.getAllStudents();
        ExportData.exportDataMenu(students, scanner);
    }

    private String getValidatedInput(Scanner scanner, String pattern, String errorMessage, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.next();
            if (Pattern.matches(pattern, input)) {
                break;
            } else {
                System.out.println(errorMessage);
            }
        }
        return input;
    }
}
