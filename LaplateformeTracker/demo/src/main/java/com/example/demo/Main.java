package com.example.demo;

import com.example.terminal.Menu;
import com.example.utility.StudentDAO;

import java.util.Scanner;

public class Main {

    static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        Menu menu = new Menu();

        try (Scanner scanner = new Scanner(System.in)) {
            int choice;

            do {
                menu.primalMenu();
                choice = menu.getUserChoice(scanner);
                menu.handlePrimalChoice(choice, scanner);
            } while (choice != 10);
        }
    }
}