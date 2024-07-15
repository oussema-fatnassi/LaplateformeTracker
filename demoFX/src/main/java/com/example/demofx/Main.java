package com.example.demofx;

import java.util.Scanner;

public class Main {

    static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        Menu menu = new Menu();

        try (Scanner scanner = new Scanner(System.in)) {
            int choice;

            do {
                menu.primalMenu();
                //menu.displayMenu();
                choice = menu.getUserChoice(scanner);
                //menu.handleChoice(choice, scanner);
                menu.handlePrimalChoice(choice, scanner);
            } while (choice != 10);
        }
    }
}
