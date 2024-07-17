package com.example.utility;

public class StudentAccount {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String major;
    private String year;
    private int age;
    private String password;

    public StudentAccount(int id, String firstName, String lastName, String email, int age,String major, String year, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.major = major;
        this.year = year;
        this.password = password;
    }

    public StudentAccount(int id, String firstName, String lastName, String email, int age, String major, String year) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
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

    public String getYear() {
        return year;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }
}