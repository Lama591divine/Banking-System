package com.github.Lama591divine;

import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.interfaces.Repository;
import java.util.Scanner;

public class ConsoleApp {
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
                case 1 -> {
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
                case 2 -> {
                    System.out.print("Enter user login: ");
                    String login = scanner.nextLine();
                    bankSystem.showUserInfo(login);
                }
                case 3 -> {
                    System.out.print("Enter your login: ");
                    String login = scanner.nextLine();
                    System.out.print("Enter friend's login: ");
                    String friendLogin = scanner.nextLine();
                    System.out.print("Add or remove friend? (add/remove): ");
                    String action = scanner.nextLine();
                    bankSystem.manageFriends(login, friendLogin, action);
                }
                case 4 -> {
                    System.out.print("Enter user login: ");
                    String login = scanner.nextLine();
                    bankSystem.createAccount(login);
                }
                case 5 -> {
                    System.out.print("Enter account ID: ");
                    String accountId = scanner.nextLine();
                    bankSystem.showBalance(accountId);
                }
                case 6 -> {
                    System.out.print("Enter account ID: ");
                    String accountId = scanner.nextLine();
                    System.out.print("Enter deposit amount: ");
                    int amount = scanner.nextInt();
                    scanner.nextLine();
                    bankSystem.depositMoney(accountId, amount);
                }
                case 7 -> {
                    System.out.print("Enter account ID: ");
                    String accountId = scanner.nextLine();
                    System.out.print("Enter withdrawal amount: ");
                    int amount = scanner.nextInt();
                    scanner.nextLine();
                    bankSystem.withdrawMoney(accountId, amount);
                }
                case 8 -> {
                    System.out.print("Enter sender account ID: ");
                    String senderId = scanner.nextLine();
                    System.out.print("Enter receiver account ID: ");
                    String receiverId = scanner.nextLine();
                    System.out.print("Enter transfer amount: ");
                    int amount = scanner.nextInt();
                    scanner.nextLine();
                    bankSystem.transferMoney(senderId, receiverId, amount);
                }
                case 9 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
