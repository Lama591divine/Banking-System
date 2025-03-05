package com.github.Lama591divine;

import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.interfaces.Repository;
import java.util.Scanner;

/**
 * The {@code ConsoleApp} class is the entry point for the bank system application.
 * It provides a command-line interface for users to interact with the banking system.
 * Users can create accounts, manage friends, deposit, withdraw, and transfer money.
 */
public class ConsoleApp {
    /**
     * The main method that starts the console-based banking application.
     * It initializes the necessary repositories and the banking system, then
     * continuously presents a menu for user interaction until the user chooses to exit.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Repository<User> userRepository = new InMemmoryUserRepository();
        Repository<Account> accountRepository = new InMemmoryAccountRepository();
        BankSystem bankSystem = new BankSystem(userRepository, accountRepository);
        Scanner scanner = new Scanner(System.in);

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

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createUser(scanner, bankSystem);
                case 2 -> showUserInfo(scanner, bankSystem);
                case 3 -> manageFriends(scanner, bankSystem);
                case 4 -> createAccount(scanner, bankSystem);
                case 5 -> showBalance(scanner, bankSystem);
                case 6 -> depositMoney(scanner, bankSystem);
                case 7 -> withdrawMoney(scanner, bankSystem);
                case 8 -> transferMoney(scanner, bankSystem);
                case 9 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Handles user creation.
     *
     * @param scanner   the scanner object for user input
     * @param bankSystem the banking system instance
     */
    private static void createUser(Scanner scanner, BankSystem bankSystem) {
        System.out.print("Enter login: ");
        String login = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter gender (MALE/FEMALE): ");
        String gender = scanner.nextLine();
        System.out.print("Enter hair color: ");
        String hairColor = scanner.nextLine();
        bankSystem.createUser(login, name, age, gender, hairColor);
    }

    /**
     * Handles displaying user information.
     *
     * @param scanner   the scanner object for user input
     * @param bankSystem the banking system instance
     */
    private static void showUserInfo(Scanner scanner, BankSystem bankSystem) {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        bankSystem.showUserInfo(login);
    }

    /**
     * Handles adding or removing friends.
     *
     * @param scanner   the scanner object for user input
     * @param bankSystem the banking system instance
     */
    private static void manageFriends(Scanner scanner, BankSystem bankSystem) {
        System.out.print("Enter your login: ");
        String login = scanner.nextLine();
        System.out.print("Enter friend's login: ");
        String friendLogin = scanner.nextLine();
        System.out.print("Add or remove friend? (add/remove): ");
        String action = scanner.nextLine();
        bankSystem.manageFriends(login, friendLogin, action);
    }

    /**
     * Handles account creation for a user.
     *
     * @param scanner   the scanner object for user input
     * @param bankSystem the banking system instance
     */
    private static void createAccount(Scanner scanner, BankSystem bankSystem) {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        bankSystem.createAccount(login);
    }

    /**
     * Handles displaying the balance of an account.
     *
     * @param scanner   the scanner object for user input
     * @param bankSystem the banking system instance
     */
    private static void showBalance(Scanner scanner, BankSystem bankSystem) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        bankSystem.showBalance(accountId);
    }

    /**
     * Handles depositing money into an account.
     *
     * @param scanner   the scanner object for user input
     * @param bankSystem the banking system instance
     */
    private static void depositMoney(Scanner scanner, BankSystem bankSystem) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        bankSystem.depositMoney(accountId, amount);
    }

    /**
     * Handles withdrawing money from an account.
     *
     * @param scanner   the scanner object for user input
     * @param bankSystem the banking system instance
     */
    private static void withdrawMoney(Scanner scanner, BankSystem bankSystem) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter withdrawal amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        bankSystem.withdrawMoney(accountId, amount);
    }

    /**
     * Handles transferring money between two accounts.
     *
     * @param scanner   the scanner object for user input
     * @param bankSystem the banking system instance
     */
    private static void transferMoney(Scanner scanner, BankSystem bankSystem) {
        System.out.print("Enter sender account ID: ");
        String senderId = scanner.nextLine();
        System.out.print("Enter receiver account ID: ");
        String receiverId = scanner.nextLine();
        System.out.print("Enter transfer amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        bankSystem.transferMoney(senderId, receiverId, amount);
    }
}
