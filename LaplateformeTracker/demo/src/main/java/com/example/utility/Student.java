package com.example.utility;

public class Student {
    // Student class with private fields and a constructor
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private double grade;
    private String subject;
    // Constructor to initialize the fields
    public Student(int id, String firstName, String lastName, String email, int age, double grade, String subject) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.grade = grade;
        this.subject = subject;
    }
    // Getters to access the fields
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
    // toString method to print the student information
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", grade=" + grade +
                ", subject='" + subject + '\'' +
                '}';
    }
}