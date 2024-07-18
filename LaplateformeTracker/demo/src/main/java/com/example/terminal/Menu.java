package com.example.terminal;

import com.example.utility.AccountCreation;
import com.example.utility.Student;
import com.example.utility.StudentDAO;

import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

import java.util.regex.Pattern;

public class Menu {

    static final StudentDAO studentDAO = new StudentDAO();

    // Display the main menu options
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

    // Get user's choice from the menu
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

    // Handle the user's choice from the menu
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
                Statistic.statisticsMenu(scanner);
                break;
            case 9:
                exportData(scanner);
                break;
            case 10:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    // Add a new student
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
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Enter grade: ");
                grade = scanner.nextDouble();
                if (grade < 0.0 || grade > 10.0) {
                    System.out.println("Invalid input. Grade must be between 0.0 and 10.0.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid decimal for grade.");
                scanner.nextLine();
            }
        }

        System.out.print("Enter subject: ");
        subject = scanner.next();

        studentDAO.addStudent(firstName, lastName, email, age, grade, subject);
        System.out.println("Student added successfully.");
    }

    // Update student details
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
                scanner.nextLine();
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
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Enter grade: ");
                grade = scanner.nextDouble();
                if (grade < 0.0 || grade > 10.0) {
                    System.out.println("Invalid input. Grade must be between 0.0 and 10.0.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid decimal for grade.");
                scanner.nextLine();
            }
        }

        System.out.print("Enter subject: ");
        subject = scanner.next();

        studentDAO.updateStudent(id, firstName, lastName, email, age, grade, subject);
        System.out.println("Student updated successfully.");
    }

    // Delete a student
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
                scanner.nextLine();
            }
        }
    }

    // Display all students
    private void displayAllStudents() {
        System.out.println("********************************************");
        System.out.println("*          All Students Information         *");
        System.out.println("********************************************");
        List<Student> students = studentDAO.getAllStudents();

        System.out.printf("%-4s | %-12s | %-12s | %-4s | %-12s | %-6s\n", "ID", "First Name", "Last Name", "Age", "Subject", "Grade");
        System.out.println("-------------------------------------------------------------");

        for (Student student : students) {
            System.out.printf("%-4d | %-12s | %-12s | %-4d | %-12s | %-6.2f\n",
                    student.getId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getAge(),
                    student.getSubject(),
                    student.getGrade());
        }
    }

    // Search for a student by ID
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
                scanner.nextLine();
            }
        }
    }

    // Export student data
    private void exportData(Scanner scanner) {
        List<Student> students = studentDAO.getAllStudents();
        ExportData.exportDataMenu(students, scanner);
    }

    // Get validated input based on a regex pattern
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

    // Display the primal menu options
    public void primalMenu() {
        System.out.println("\n********************************************");
        System.out.println("*      Student Management System Menu      *");
        System.out.println("********************************************");
        System.out.println("1. Create admin account");
        System.out.println("2. Admin Login");
        System.out.println("3. Student Menu");
        System.out.println("10. Exit");
        System.out.println("********************************************");
        System.out.print("Enter your choice: ");

    }

    // Handle the user's choice from the primal menu
    public void handlePrimalChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                createAdminAccount(scanner);
                break;
            case 2:
                adminLogin(scanner);
                break;
            case 3:
                studentMenu(scanner);
                break;
            case 10:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    // Create an admin account
    private void createAdminAccount(Scanner scanner) {
        System.out.println("\n********************************************");
        System.out.println("*           Create Admin Account            *");
        System.out.println("********************************************");
        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter last name: ");
        String lastName = scanner.next();
        System.out.print("Enter email: ");
        String email = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.next();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            System.out.println("All fields must be filled.");
            return;
        }

        if (!validateName(firstName)) {
            System.out.println("First name must not contain numbers or symbols.");
            return;
        }

        if (!validateName(lastName)) {
            System.out.println("Last name must not contain numbers or symbols.");
            return;
        }

        if (!validateEmail(email)) {
            System.out.println("Email must be valid and contain '@'.");
            return;
        }

        if (!validatePassword(password)) {
            return;
        }

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return;
        }

        boolean created = AccountCreation.createAdminAccount(firstName, lastName, email, password);
        if (created) {
            System.out.println("Admin account created successfully.");
        } else {
            System.out.println("Failed to create admin account.");
        }
    }

    // Validate a name (should contain only letters)
    private boolean validateName(String name) {
        return name.matches("^[a-zA-Z]+$");
    }

    // Validate an email address
    private boolean validateEmail(String email) {
        return email.contains("@");
    }

    // Validate a password (should meet certain criteria)
    private boolean validatePassword(String password) {
        boolean isValidLength = password.length() >= 10;
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[@#$%^&+=!?.;].*");

        System.out.println("Password: " + password);
        System.out.println("Valid Length: " + isValidLength);
        System.out.println("Has Upper Case: " + hasUpperCase);
        System.out.println("Has Lower Case: " + hasLowerCase);
        System.out.println("Has Digit: " + hasDigit);
        System.out.println("Has Special Char: " + hasSpecialChar);

        String alertMessage = "The password must contain at least";
        int count = 0;
        if (!isValidLength) {
            count++;
            alertMessage += " 10 characters";
        } if (!hasUpperCase) {
            if (count > 0) {
                alertMessage += ",";
            }
            count++;
            alertMessage += " one uppercase letter";
        } if (!hasLowerCase) {
            if (count > 0) {
                alertMessage += ",";
            }
            count++;
            alertMessage += " one lowercase letter";
        } if (!hasDigit) {
            if (count > 0) {
                alertMessage += ",";
            }
            count++;
            alertMessage += " one number";
        } if (!hasSpecialChar) {
            if (count > 0) {
                alertMessage += ",";
            }
            alertMessage += " one special character";
        }
        alertMessage += ".";

        if (!isValidLength || !hasUpperCase || !hasLowerCase || !hasDigit || !hasSpecialChar) {
            System.out.println("Invalid Input: " + alertMessage);
        }

        return isValidLength && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    // Perform admin login
    private void adminLogin(Scanner scanner) {
        System.out.println("\n********************************************");
        System.out.println("*               Admin Login                *");
        System.out.println("********************************************");
        System.out.print("Enter email: ");
        String email = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("All fields must be filled.");
            return;
        }

        boolean loggedIn = AccountCreation.authenticateAdmin(email, password);
        if (loggedIn) {
            System.out.println("Admin logged in successfully.");
            int choice;
            do {
                displayMenu();
                choice = getUserChoice(scanner);
                handleChoice(choice, scanner);
            } while (choice != 10);
        } else {
            System.out.println("Failed to log in.");
        }
    }

    // Display the student menu options
    private void studentDisplayMenu(Scanner scanner) {
        System.out.println("********************************************");
        System.out.println("*               Student Menu               *");
        System.out.println("********************************************");
        System.out.println("1. Search student by ID");
        System.out.println("2. Sort students");
        System.out.println("3. Advanced Search");
        System.out.println("4. Display statistics");
        System.out.println("10. Exit");
        System.out.println("********************************************");
        System.out.print("Enter your choice: ");
    }

    // Handle the user's choice from the student menu
    private void handleStudentChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                searchStudent(scanner);
                break;
            case 2:
                Sort.sortStudentsMenu(scanner);
                break;
            case 3:
                AdvancedResearch.advancedSearchMenu(scanner);
                break;
            case 4:
                Statistic.statisticsMenu(scanner);
                break;
            case 10:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    // Display the student menu
    private void studentMenu(Scanner scanner) {
        int choice;
        do {
            studentDisplayMenu(scanner);
            choice = getUserChoice(scanner);
            handleStudentChoice(choice, scanner);
        } while (choice != 10);
    }
}