package com.example.demofx;

public class AccountCreation {

    public static boolean createAdminAccount(String firstName, String lastName, String email, String password) {
        return AdminDAO.createAdmin(firstName, lastName, email, password);
    }

    public static boolean authenticateAdmin(String email, String password) {
        return AdminDAO.authenticateAdmin(email, password);
    }
}
