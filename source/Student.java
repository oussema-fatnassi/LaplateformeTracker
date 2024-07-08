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

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public double getGrade() {
        return grade;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "Students: " + '\n' +
                "id: " + id + '\n' +
                "firstName: " + firstName + '\n' +
                "lastName: " + lastName + '\n' +
                "email: " + email + '\n' +
                "age: " + age + '\n' +
                "grade: " + grade + '\n' +
                "subject: " + subject + '\n';
    }
}