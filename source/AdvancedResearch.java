package source;
import java.util.List;

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
}
