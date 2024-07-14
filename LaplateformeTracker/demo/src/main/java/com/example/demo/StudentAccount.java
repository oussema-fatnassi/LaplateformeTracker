package com.example.demo;

public class StudentAccount {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String major;
    private int year;

    public StudentAccount(int id, String firstName, String lastName, String email, String major, int year) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.major = major;
        this.year = year;
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

    public String getMajor() {
        return major;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "StudentAccount{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", major='" + major + '\'' +
                ", year=" + year +
                '}';
    }
}
