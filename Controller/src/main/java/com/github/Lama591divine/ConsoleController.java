package com.github.Lama591divine;

import com.github.Lama591divine.DbDao.DbAccountDao;
import com.github.Lama591divine.DbDao.DbUserDao;

import java.util.Scanner;

public class ConsoleController {
    private final AccountService accountService;
    private final UserService userService;

    public ConsoleController() {
        DbUserDao userDao = new DbUserDao();
        DbAccountDao accountDao = new DbAccountDao();
        accountService = new AccountService(userDao, accountDao);
        userService = new UserService(userDao);
    }

    public void createUser(Scanner scanner) {
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
        userService.createUser(login, name, age, gender, hairColor);
    }

    public void showUserInfo(Scanner scanner) {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        userService.showUserInfo(login);
    }

    public void manageFriends(Scanner scanner) {
        System.out.print("Enter your login: ");
        String login = scanner.nextLine();
        System.out.print("Enter friend's login: ");
        String friendLogin = scanner.nextLine();
        System.out.print("Add or remove friend? (add/remove): ");
        String action = scanner.nextLine();
        userService.manageFriends(login, friendLogin, action);
    }

    public void createAccount(Scanner scanner) {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        accountService.createAccount(login);
    }

    public void showBalance(Scanner scanner) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        accountService.showBalance(accountId);
    }

    public void depositMoney(Scanner scanner) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        accountService.depositMoney(accountId, amount);
    }

    public void withdrawMoney(Scanner scanner) {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter withdrawal amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        accountService.withdrawMoney(accountId, amount);
    }

    public void transferMoney(Scanner scanner) {
        System.out.print("Enter sender account ID: ");
        String senderId = scanner.nextLine();
        System.out.print("Enter receiver account ID: ");
        String receiverId = scanner.nextLine();
        System.out.print("Enter transfer amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        accountService.transferMoney(senderId, receiverId, amount);
    }
}
