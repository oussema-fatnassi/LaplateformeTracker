package com.example.demo;

import com.google.gson.annotations.Expose;

public class StudentGrade {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String subject;
    @Expose
    private double grade;

    public StudentGrade(String firstName, String lastName, String subject, double grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
        this.grade = grade;
    }

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