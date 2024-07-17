package com.example.utility;

import com.google.gson.annotations.Expose;

public class StudentGrade {
    // StudentGrade class with private fields and a constructor
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String subject;
    @Expose
    private double grade;
    // Constructor to initialize the fields
    public StudentGrade(String firstName, String lastName, String subject, double grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
        this.grade = grade;
    }
    // Getters to access the fields
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSubject() {
        return subject;
    }

    public double getGrade() {
        return grade;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}