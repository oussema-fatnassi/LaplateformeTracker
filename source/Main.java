package source;

import java.util.Scanner;
import java.util.List;

public class Main {

    static final StudentDAO studentDAO = new StudentDAO();
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Add student");
            System.out.println("2. Update student");
            System.out.println("3. Delete student");
            System.out.println("4. Display all students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

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
                    displayAllStudents();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 5);
    }

    private static void addStudent(Scanner scanner){
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
    }

    private static void updateStudent(Scanner scanner){
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
    }

    private static void deleteStudent(Scanner scanner){
        System.out.print("Enter student id: ");
        int id = scanner.nextInt();
        studentDAO.deleteStudent(id);
        System.out.println("Student deleted successfully");
    }

    private static void displayAllStudents(){
        List<Student> students = studentDAO.getAllStudents();
        students.forEach(System.out::println);
    }
}