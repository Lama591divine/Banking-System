package com.github.Lama591divine;

import com.github.Lama591divine.DTO.DtoAccount;
import com.github.Lama591divine.DTO.DtoUser;
import com.github.Lama591divine.repositoryes.InMemoryAccountRepository;
import com.github.Lama591divine.repositoryes.InMemoryUserRepository;
import com.github.Lama591divine.repositoryes.Repository;
import com.github.Lama591divine.systems.AccountSystem;
import com.github.Lama591divine.systems.UserSystem;

import java.util.Scanner;

public class ConsoleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Repository<DtoUser> userRepository = new InMemoryUserRepository();
        Repository<DtoAccount> accountRepository = new InMemoryAccountRepository();
        AccountSystem accountSystem = new AccountSystem(userRepository, accountRepository);
        UserSystem userSystem = new UserSystem(userRepository);

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
                case 1 -> createUser(scanner, userSystem);
                case 2 -> showUserInfo(scanner, userSystem);
                case 3 -> manageFriends(scanner, userSystem);
                case 4 -> createAccount(scanner, accountSystem);
                case 5 -> showBalance(scanner, accountSystem);
                case 6 -> depositMoney(scanner, accountSystem);
                case 7 -> withdrawMoney(scanner, accountSystem);
                case 8 -> transferMoney(scanner, accountSystem);
                case 9 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void createUser(Scanner scanner, UserSystem userSystem) {
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
        userSystem.createUser(login, name, age, gender, hairColor);
    }

    private static void showUserInfo(Scanner scanner, UserSystem userSystem) {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        userSystem.showUserInfo(login);
    }

    private static void manageFriends(Scanner scanner, UserSystem userSystem) {
        System.out.print("Enter your login: ");
        String login = scanner.nextLine();
        System.out.print("Enter friend's login: ");
        String friendLogin = scanner.nextLine();
        System.out.print("Add or remove friend? (add/remove): ");
        String action = scanner.nextLine();
        userSystem.manageFriends(login, friendLogin, action);
    }

    private static void createAccount(Scanner scanner, AccountSystem accountSystem) {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        accountSystem.createAccount(login);
    }

    private static void showBalance(Scanner scanner, AccountSystem accountSystem) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        accountSystem.showBalance(accountId);
    }

    private static void depositMoney(Scanner scanner, AccountSystem accountSystem) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        accountSystem.depositMoney(accountId, amount);
    }

    private static void withdrawMoney(Scanner scanner, AccountSystem accountSystem) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter withdrawal amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        accountSystem.withdrawMoney(accountId, amount);
    }

    private static void transferMoney(Scanner scanner, AccountSystem accountSystem) {
        System.out.print("Enter sender account ID: ");
        String senderId = scanner.nextLine();
        System.out.print("Enter receiver account ID: ");
        String receiverId = scanner.nextLine();
        System.out.print("Enter transfer amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        accountSystem.transferMoney(senderId, receiverId, amount);
    }
}
