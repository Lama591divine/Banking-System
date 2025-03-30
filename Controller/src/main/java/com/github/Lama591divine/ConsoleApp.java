package com.github.Lama591divine;

import java.util.Scanner;

/**
 * The {@code ConsoleApp} class represents the main entry point for the banking system.
 * It provides an interactive console menu for user and account management.
 * All methods just delegate work to another objects
 */
public class ConsoleApp {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ConsoleController controller = new ConsoleController();

            while (true) {
                System.out.println("\n=== Bank System Menu ===");
                System.out.println("1. Create a user");
                System.out.println("2. Show user info");
                System.out.println("3. Manage friends");
                System.out.println("4. Create an account");
                System.out.println("5. Show account balance");
                System.out.println("6. Deposit money");
                System.out.println("7. Withdraw money");
                System.out.println("8. Transfer money");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");

                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1 -> controller.createUser(scanner);
                        case 2 -> controller.showUserInfo(scanner);
                        case 3 -> controller.manageFriends(scanner);
                        case 4 -> controller.createAccount(scanner);
                        case 5 -> controller.showBalance(scanner);
                        case 6 -> controller.depositMoney(scanner);
                        case 7 -> controller.withdrawMoney(scanner);
                        case 8 -> controller.transferMoney(scanner);
                        case 9 -> {
                            controller.close();
                            return;
                        }
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println("Incorrect type of number. Input number for choose");
                }
            }
        }
    }
}