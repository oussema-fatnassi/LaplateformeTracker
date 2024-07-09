package source;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

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