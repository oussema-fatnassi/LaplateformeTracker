package com.example.demo;

import java.util.Scanner;

public class Main {

    static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        Menu menu = new Menu();

        try (Scanner scanner = new Scanner(System.in)) {
            int choice;

            do {
                menu.displayMenu();
                choice = menu.getUserChoice(scanner);
                menu.handleChoice(choice, scanner);
            } while (choice != 10);
        }
    }
}