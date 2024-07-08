package source;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private double grade;
    private String subject;

    public Student(int id, String firstName, String lastName, String email, int age, double grade, String subject) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.grade = grade;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", grade=" + grade +
                ", subject='" + subject + '\n' +
                '}';
    }
}

