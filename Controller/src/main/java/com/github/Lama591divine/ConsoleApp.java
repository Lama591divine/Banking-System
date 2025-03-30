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
            Dao<User> userDao = new InMemoryUserDao();
            Dao<Account> accountDao = new InMemoryAccountDao();
            AccountService accountService = new AccountService(userDao, accountDao);
            UserService userService = new UserService(userDao);

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
                        case 1 -> ConsoleController.createUser(scanner, userService);
                        case 2 -> ConsoleController.showUserInfo(scanner, userService);
                        case 3 -> ConsoleController.manageFriends(scanner, userService);
                        case 4 -> ConsoleController.createAccount(scanner, accountService);
                        case 5 -> ConsoleController.showBalance(scanner, accountService);
                        case 6 -> ConsoleController.depositMoney(scanner, accountService);
                        case 7 -> ConsoleController.withdrawMoney(scanner, accountService);
                        case 8 -> ConsoleController.transferMoney(scanner, accountService);
                        case 9 -> {
                            System.out.println("Exiting...");
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