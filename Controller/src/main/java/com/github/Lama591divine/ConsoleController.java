package com.github.Lama591divine;

import java.util.Scanner;

public class ConsoleController {
    public static void createUser(Scanner scanner, UserService userSystem) {
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

    public static void showUserInfo(Scanner scanner, UserService userSystem) {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        userSystem.showUserInfo(login);
    }

    public static void manageFriends(Scanner scanner, UserService userSystem) {
        System.out.print("Enter your login: ");
        String login = scanner.nextLine();
        System.out.print("Enter friend's login: ");
        String friendLogin = scanner.nextLine();
        System.out.print("Add or remove friend? (add/remove): ");
        String action = scanner.nextLine();
        userSystem.manageFriends(login, friendLogin, action);
    }

    public static void createAccount(Scanner scanner, AccountService accountSystem) {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        accountSystem.createAccount(login);
    }

    public static void showBalance(Scanner scanner, AccountService accountSystem) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        accountSystem.showBalance(accountId);
    }

    public static void depositMoney(Scanner scanner, AccountService accountSystem) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        accountSystem.depositMoney(accountId, amount);
    }

    public static void withdrawMoney(Scanner scanner, AccountService accountSystem) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter withdrawal amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        accountSystem.withdrawMoney(accountId, amount);
    }

    public static void transferMoney(Scanner scanner, AccountService accountSystem) {
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
