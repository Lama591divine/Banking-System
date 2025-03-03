package com.github.Lama591divine;

import com.github.Lama591divine.interfaces.AccountRepository;
import com.github.Lama591divine.interfaces.UserRepository;

/**
 * The {@code ConsoleApp} class serves as the entry point for the bank system application.
 * It provides a simple text-based menu for interacting with the banking system,
 * allowing users to create accounts, manage friends, deposit, withdraw, and transfer money.
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
        UserRepository userRepository = new InMemmoryUserRepository();
        AccountRepository accountRepository = new InMemmoryAccountRepository();
        BankSystem bankSystem = new BankSystem(userRepository, accountRepository);

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

            int choice = bankSystem.scanner.nextInt();
            bankSystem.scanner.nextLine();

            switch (choice) {
                case 1 -> bankSystem.createUser();
                case 2 -> bankSystem.showUserInfo();
                case 3 -> bankSystem.manageFriends();
                case 4 -> bankSystem.createAccount();
                case 5 -> bankSystem.showBalance();
                case 6 -> bankSystem.depositMoney();
                case 7 -> bankSystem.withdrawMoney();
                case 8 -> bankSystem.transferMoney();
                case 9 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
